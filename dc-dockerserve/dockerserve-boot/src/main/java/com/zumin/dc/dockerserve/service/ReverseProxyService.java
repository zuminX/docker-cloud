package com.zumin.dc.dockerserve.service;

import com.zumin.dc.common.core.utils.ServletUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 反向代理服务类
 */
@Service
public class ReverseProxyService {

  /**
   * 代理请求
   *
   * @param serverUrl 服务器URL
   * @param prefix    请求前缀
   * @return 请求结果
   */
  public ResponseEntity<String> proxy(String serverUrl, String prefix) {
    try {
      HttpServletRequest request = ServletUtils.getRequest();
      String redirectUrl = createProxyUrl(request, serverUrl, prefix);
      RequestEntity<?> requestEntity = createRequestEntity(request, redirectUrl);
      return request(requestEntity);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * 创建实际请求的URL
   *
   * @param request   HTTP请求
   * @param serverUrl 服务器URL
   * @param prefix    请求前缀
   * @return 反向代理的实际URL
   */
  private String createProxyUrl(HttpServletRequest request, String serverUrl, String prefix) {
    String queryString = request.getQueryString();
    return serverUrl + request.getRequestURI().replace(prefix, "") + (queryString != null ? "?" + queryString : "");
  }

  /**
   * 创建请求信息
   *
   * @param request HTTP请求
   * @param url     请求URL
   * @return 请求实体
   * @throws URISyntaxException URI语法异常
   * @throws IOException        IO异常
   */
  private RequestEntity<?> createRequestEntity(HttpServletRequest request, String url) throws URISyntaxException, IOException {
    String method = request.getMethod();
    HttpMethod httpMethod = HttpMethod.resolve(method);
    MultiValueMap<String, String> headers = parseRequestHeader(request);
    byte[] body = parseRequestBody(request);
    return new RequestEntity<>(body, headers, httpMethod, new URI(url));
  }

  /**
   * 进行请求
   *
   * @param requestEntity 请求实体
   * @return 响应实体
   */
  private ResponseEntity<String> request(RequestEntity<?> requestEntity) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.exchange(requestEntity, String.class);
  }

  /**
   * 解析请求体
   *
   * @param request HTTP请求
   * @return 请求字节数组
   * @throws IOException IO异常
   */
  private byte[] parseRequestBody(HttpServletRequest request) throws IOException {
    InputStream inputStream = request.getInputStream();
    return StreamUtils.copyToByteArray(inputStream);
  }

  /**
   * 解析请求头
   *
   * @param request HTTP请求
   * @return 请求头值映射
   */
  private MultiValueMap<String, String> parseRequestHeader(HttpServletRequest request) {
    HttpHeaders headers = new HttpHeaders();
    List<String> headerNames = Collections.list(request.getHeaderNames());
    headerNames.forEach(headerName -> {
      List<String> headerValues = Collections.list(request.getHeaders(headerName));
      headerValues.forEach(headerValue -> headers.add(headerName, headerValue));
    });
    return headers;
  }
}