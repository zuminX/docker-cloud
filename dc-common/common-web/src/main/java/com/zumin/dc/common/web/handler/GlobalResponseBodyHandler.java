package com.zumin.dc.common.web.handler;

import com.zumin.dc.common.core.result.CommonResult;
import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应体处理器
 */
@ControllerAdvice(basePackages = {"com.zumin.dc"})
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.response'])}")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

  /**
   * 是否支持给定的控制器方法返回类型和所选的HttpMessageConverter类型
   *
   * @param methodParameter 方法参数
   * @param aClass          该方法所在的类
   * @return 返回true代表支持该方法，返回false代表不支持该方法
   */
  @Override
  public boolean supports(@NotNull MethodParameter methodParameter, @NotNull Class aClass) {
    return true;
  }

  /**
   * 修改响应对象
   *
   * @param body               原响应对象
   * @param methodParameter    方法参数
   * @param mediaType          媒体类型
   * @param aClass             所在的类
   * @param serverHttpRequest  请求对象
   * @param serverHttpResponse 响应对象
   * @return 经过包装的响应对象
   */
  @Override
  public Object beforeBodyWrite(Object body, @NotNull MethodParameter methodParameter, @NotNull MediaType mediaType, @NotNull Class aClass,
      @NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse) {
    Method method = methodParameter.getMethod();
    if (method != null && method.getReturnType().equals(ResponseEntity.class)) {
      return body;
    }
    return body instanceof CommonResult ? body : CommonResult.success(body);
  }
}
