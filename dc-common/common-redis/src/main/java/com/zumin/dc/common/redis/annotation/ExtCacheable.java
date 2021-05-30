package com.zumin.dc.common.redis.annotation;

import cn.hutool.core.util.ArrayUtil;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

/**
 * 扩展Cacheable注解，使其具有过期时间属性
 */
@Cacheable
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ExtCacheable {

  /**
   * 设置Redis缓存的过期时间，默认为永不过期
   *
   * @return 以秒为单位的过期时间
   */
  int ttlOfSeconds() default 0;

  /**
   * 设置Redis缓存的过期时间，默认为永不过期
   *
   * @return 以分钟为单位的过期时间
   */
  int ttlOfMinutes() default 0;

  /**
   * 设置Redis缓存的过期时间，默认为永不过期
   *
   * @return 以小时为单位的过期时间
   */
  int ttlOfHours() default 0;

  /**
   * 设置Redis缓存的过期时间，默认为永不过期
   *
   * @return 以天为单位的过期时间
   */
  int ttlOfDays() default 0;

  @AliasFor(annotation = Cacheable.class)
  String[] value() default {};

  @AliasFor(annotation = Cacheable.class)
  String[] cacheNames() default {};

  @AliasFor(annotation = Cacheable.class)
  String keyGenerator() default "simpleKG";

  /**
   * 扩展Cacheable注解的工具类
   */
  class ExtCacheableUtils {

    /**
     * 获取extCacheable的TTL
     *
     * @param extCacheable 扩展Cacheable注解
     * @return 以Duration形式的TTL，若未设置则返回null
     */
    @Nullable
    public static Duration getTtl(@NotNull ExtCacheable extCacheable) {
      Duration duration = Duration.ZERO.plusSeconds(extCacheable.ttlOfSeconds());
      duration = duration.plusMillis(extCacheable.ttlOfMinutes());
      duration = duration.plusHours(extCacheable.ttlOfHours());
      duration = duration.plusDays(extCacheable.ttlOfDays());
      return duration.compareTo(Duration.ZERO) > 0 ? duration : null;
    }

    /**
     * 获取extCacheable的名称，若有多个则返回第一个
     *
     * @param extCacheable 扩展Cacheable注解
     * @return extCacheable的名称，若未设置则返回null
     */
    @Nullable
    public static String getName(@NotNull ExtCacheable extCacheable) {
      String[] value = extCacheable.value();
      return ArrayUtil.isNotEmpty(value) ? value[0] : null;
    }
  }
}
