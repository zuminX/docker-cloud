package com.zumin.dc.gateway.security;

import cn.hutool.core.collection.CollUtil;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.redis.utils.RedisUtils;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

/**
 * 鉴权管理器
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

  private final RedisUtils redisUtils;

  /**
   * 自定义鉴权逻辑
   *
   * @param mono                 要检查的身份验证
   * @param authorizationContext 要检查的对象
   * @return 校验结果
   */
  @Override
  public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
    ServerHttpRequest request = authorizationContext.getExchange().getRequest();
    String restPath = request.getMethodValue() + "_" + request.getURI().getPath();
    log.info("请求路径：{}", restPath);
    // 对于跨域的预检请求直接放行
    if (request.getMethod() == HttpMethod.OPTIONS) {
      return Mono.just(new AuthorizationDecision(true));
    }
    Map<String, Set<String>> resourceRolesMap = redisUtils.getMap(AuthConstants.RESOURCE_ROLES_KEY);
    Set<String> authorities = getLongestMatch(restPath, resourceRolesMap);
    // 资源没有设置访问权限，直接放行
    if (CollUtil.isEmpty(authorities)) {
      return Mono.just(new AuthorizationDecision(true));
    }
    return mono
        .filter(Authentication::isAuthenticated)
        .flatMapIterable(Authentication::getAuthorities)
        .map(GrantedAuthority::getAuthority)
        .any(authorities::contains)
        .map(AuthorizationDecision::new)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }

  /**
   * 获取最长匹配的路径所需要的角色
   *
   * @param restPath         访问路径
   * @param resourceRolesMap 资源对应的角色
   * @return 所需的角色
   */
  private Set<String> getLongestMatch(String restPath, Map<String, Set<String>> resourceRolesMap) {
    PathMatcher pathMatcher = new AntPathMatcher();
    return resourceRolesMap.entrySet()
        .stream()
        .filter(entry -> pathMatcher.match(entry.getKey(), restPath))
        .max(Comparator.comparingInt(e -> e.getKey().length()))
        .map(Entry::getValue)
        .orElse(null);
  }
}
