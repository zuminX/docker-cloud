package com.zumin.dc.common.alicloud.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.zumin.dc.common.alicloud.constants.AliCloudRedisKey;
import com.zumin.dc.common.alicloud.pojo.OSSCallbackParameter;
import com.zumin.dc.common.alicloud.pojo.OSSCallbackResult;
import com.zumin.dc.common.alicloud.pojo.OSSPolicy;
import com.zumin.dc.common.core.utils.DateUtils;
import com.zumin.dc.common.core.utils.ServletUtils;
import com.zumin.dc.common.redis.utils.RedisUtils;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OSS服务类
 */
@Component
@RequiredArgsConstructor
public class OSSService {

  private static final String CALLBACK_DATA_PARAMETER_NAME = "callback_key";
  private final OSS oss;
  private final RedisUtils redisUtils;
  @Value("${alicloud.access-key-id}")
  private String accessKeyId;
  @Value("${alicloud.oss.endpoint}")
  private String endpoint;
  @Value("${alicloud.oss.bucket-name}")
  private String bucketName;
  @Value("${alicloud.oss.policy.expire}")
  private int expire;
  @Value("${system.addr}")
  private String addr;

  /**
   * 获取OSS上传凭证
   *
   * @param maxSize   文件的最大大小(以M为单位)
   * @param baseDir   存放的目录
   * @param parameter 回调参数
   * @return 上传凭证
   */
  public OSSPolicy policy(long maxSize, String baseDir, OSSCallbackParameter<?> parameter) {
    String dir = getDateDir(baseDir);
    String postPolicy = oss.generatePostPolicy(getExpiration(), getPolicyConditions(maxSize * 1048576, dir));

    OSSPolicy oosPolicy = new OSSPolicy();
    oosPolicy.setAccessKeyId(accessKeyId);
    oosPolicy.setPolicy(getPolicy(postPolicy));
    oosPolicy.setSignature(oss.calculatePostSignature(postPolicy));
    oosPolicy.setDir(dir);
    oosPolicy.setHost(getHost());
    oosPolicy.setCallback(getCallbackData(parameter));
    return oosPolicy;
  }

  /**
   * 从请求中解析回调结果
   *
   * @return 上传的回调结果
   */
  public <T> OSSCallbackResult<T> resolveCallbackData() {
    return (OSSCallbackResult<T>) resolveCallbackData(Object.class);
  }

  /**
   * 从请求中解析回调结果
   *
   * @param clazz 数据类型
   * @return 上传的回调结果
   */
  public <T> OSSCallbackResult<T> resolveCallbackData(Class<T> clazz) {
    HttpServletRequest request = ServletUtils.getRequest();
    OSSCallbackResult<T> oosCallback = new OSSCallbackResult<>();
    oosCallback.setFilePath(getHost() + "/" + request.getParameter("filename"));
    oosCallback.setSize(request.getParameter("size"));
    oosCallback.setMimeType(request.getParameter("mimeType"));
    String key = request.getParameter(CALLBACK_DATA_PARAMETER_NAME);
    if (StrUtil.isNotBlank(key)) {
      oosCallback.setCallbackData(redisUtils.getAndDelete(key, clazz));
    }
    return oosCallback;
  }

  /**
   * 获取上传策略
   *
   * @param postPolicy 策略
   * @return 编码后的上传策略
   */
  private String getPolicy(String postPolicy) {
    byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
    return BinaryUtil.toBase64String(binaryData);
  }

  /**
   * 获取日期目录
   *
   * @param baseDir 基本目录
   * @return 日期目录
   */
  private String getDateDir(String baseDir) {
    return baseDir + "/" + DateUtils.plainDateStr() + "/";
  }

  /**
   * 获取凭证的过期时间
   *
   * @return 过期时间
   */
  private Date getExpiration() {
    return new Date(System.currentTimeMillis() + expire * 1000L);
  }

  /**
   * 获取OSS上传主机
   *
   * @return 主机地址
   */
  private String getHost() {
    return "https://" + bucketName + "." + endpoint;
  }

  /**
   * 获取回调数据
   *
   * @param parameter 回调参数
   * @return 编码后的回调数据
   */
  private String getCallbackData(OSSCallbackParameter<?> parameter) {
    CallbackData callbackData = new CallbackData();
    callbackData.setCallbackUrl(addr + "/" + parameter.getCallbackPath());
    callbackData.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&" + CALLBACK_DATA_PARAMETER_NAME + "=" + saveCallbackData(parameter));
    callbackData.setCallbackBodyType("application/x-www-form-urlencoded");
    return BinaryUtil.toBase64String(JSONUtil.parse(callbackData).toString().getBytes());
  }

  /**
   * 获取策略条件
   *
   * @param maxSize 文件的最大大小
   * @param dir     文件的目录
   * @return 策略条件
   */
  private PolicyConditions getPolicyConditions(long maxSize, String dir) {
    PolicyConditions policyConditions = new PolicyConditions();
    policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
    policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
    return policyConditions;
  }

  private String saveCallbackData(OSSCallbackParameter<?> parameter) {
    if (parameter.getData() == null) {
      return "";
    }
    String key = AliCloudRedisKey.OSS_CALLBACK_DATA_PREFIX + UUID.fastUUID();
    redisUtils.set(key, parameter.getData(), parameter.getTimeout(), parameter.getTimeUnit());
    return key;
  }

  @Data
  private static class CallbackData {

    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
  }
}
