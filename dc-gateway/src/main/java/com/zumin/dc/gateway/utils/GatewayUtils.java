package com.zumin.dc.gateway.utils;

import cn.hutool.json.JSONUtil;
import com.zumin.dc.common.core.enums.StatusCode;
import com.zumin.dc.common.core.result.CommonResult;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * 网关工具类
 */
public class GatewayUtils {

  /**
   * 响应错误信息
   *
   * @param response   响应对象
   * @param statusCode 状态码
   * @return 响应
   */
  public static Mono<Void> writeFailedToResponse(ServerHttpResponse response, StatusCode statusCode) {
    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.getHeaders().set("Access-Control-Allow-Origin", "*");
    response.getHeaders().set("Cache-Control", "no-cache");
    String body = JSONUtil.toJsonStr(CommonResult.error(statusCode));
    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
    return response.writeWith(Mono.just(buffer))
        .doOnError(error -> DataBufferUtils.release(buffer));
  }

}
