package com.zumin.dc.common.web.convert;

/**
 * 字符串对象转换器
 *
 * @param <T> 目标类型
 */
public interface StringConvert<T> {

  /**
   * 将字符串转化为类型T
   *
   * @param text 字符串对象
   * @return 字符串对应的类型T对象
   */
  T convert(String text);
}
