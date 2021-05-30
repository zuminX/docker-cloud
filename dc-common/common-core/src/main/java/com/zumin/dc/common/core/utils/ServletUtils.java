package com.zumin.dc.common.core.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端工具类
 */
@UtilityClass
public class ServletUtils {

  /**
   * 将数据转换为JSON数据响应给客户端
   *
   * @param response HttpServlet响应对象
   * @param data     数据
   * @throws IOException IO异常
   */
  public void returnJsonData(HttpServletResponse response, Object data) throws IOException {
    response.setStatus(HttpStatus.HTTP_OK);
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter writer = response.getWriter();
    writer.write(JSONUtil.toJsonStr(data));
    writer.flush();
    writer.close();
  }

  /**
   * 获取请求对象
   *
   * @return 请求对象
   */
  public HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  /**
   * 获取响应对象
   *
   * @return 响应对象
   */
  public HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  /**
   * 获取String参数
   *
   * @param name 参数名称
   * @return 参数
   */
  public String getParameter(String name) {
    return getRequest().getParameter(name);
  }

  /**
   * 获取String参数
   *
   * @param name         参数名称
   * @param defaultValue 默认值
   * @return 参数
   */
  public String getParameter(String name, String defaultValue) {
    return Convert.toStr(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取Integer参数
   *
   * @param name 参数名称
   * @return 参数
   */
  public Integer getParameterToInt(String name) {
    return Convert.toInt(getRequest().getParameter(name));
  }

  /**
   * 获取Integer参数
   *
   * @param name         参数名称
   * @param defaultValue 默认值
   * @return 参数
   */
  public Integer getParameterToInt(String name, Integer defaultValue) {
    return Convert.toInt(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取请求属性
   *
   * @return Servlet请求属性
   */
  private ServletRequestAttributes getRequestAttributes() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    return (ServletRequestAttributes) attributes;
  }
}
