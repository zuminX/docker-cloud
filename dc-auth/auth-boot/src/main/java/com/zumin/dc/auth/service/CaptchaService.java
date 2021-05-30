package com.zumin.dc.auth.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.zumin.dc.common.core.auth.AuthRedisKey;
import com.zumin.dc.auth.exception.CaptchaException;
import com.zumin.dc.auth.pojo.vo.CaptchaVO;
import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.common.redis.utils.RedisUtils;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 验证码业务层类
 */
@Setter
@Service
@RequiredArgsConstructor
@ConfigurationProperties("captcha")
public class CaptchaService {

  private final RedisUtils redisUtils;

  /**
   * 验证码的宽度
   */
  private int width = 112;

  /**
   * 验证码的高度
   */
  private int height = 36;

  /**
   * 验证码字符的个数
   */
  private int codeNumber = 4;

  /**
   * 验证码干扰线的个数
   */
  private int lineCount = 100;

  /**
   * 验证码有效期(分钟)
   */
  private int expireTime;

  /**
   * 生成验证码
   *
   * @return 验证码对象
   */
  public CaptchaVO generateCaptcha() {
    LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, codeNumber, lineCount);
    String uuid = UUID.fastUUID().toString();
    redisUtils.set(getCaptchaKey(uuid), captcha.getCode(), expireTime, TimeUnit.MINUTES);
    return new CaptchaVO(uuid, captcha.getImageBase64());
  }

  /**
   * 检查验证码是否正确
   *
   * @param uuid 唯一标识
   * @param code 待验证的码
   */
  public void checkCaptcha(String uuid, String code) {
    String key = getCaptchaKey(uuid);
    String captcha = redisUtils.get(key);
    if (captcha == null) {
      throw new CaptchaException(AuthStatusCode.CAPTCHA_EXPIRED);
    }
    redisUtils.delete(key);
    if (!captcha.equalsIgnoreCase(code)) {
      throw new CaptchaException(AuthStatusCode.CAPTCHA_NOT_EQUALS);
    }
  }

  /**
   * 获取验证码的key值
   *
   * @return 验证码在redis中的key值
   */
  protected String getCaptchaKey(String uuid) {
    return AuthRedisKey.CAPTCHA_PREFIX + uuid;
  }
}
