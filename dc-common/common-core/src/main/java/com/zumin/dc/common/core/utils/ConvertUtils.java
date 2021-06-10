package com.zumin.dc.common.core.utils;

import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

/**
 * 转换器工具类
 */
@UtilityClass
public class ConvertUtils {

  /**
   * 使用函数convert转换集合list中的元素
   *
   * @param list    待转换的集合
   * @param convert 转换函数
   * @param <T>     待转化的元素类型
   * @param <V>     转换后的元素类型
   * @return 转换后的集合
   */
  public <T, V> List<V> convert(Collection<T> list, Function<T, V> convert) {
    return convert(list, convert, Collectors.toList());
  }

  /**
   * 使用函数convert转换集合list中的元素，并将它们以delimiter进行连接
   *
   * @param list      待转换的集合
   * @param convert   转换函数
   * @param delimiter 连接字符串
   * @param <T>       待转化的元素类型
   * @return 以delimiter连接的字符串
   */
  public <T> String convertAndJoin(Collection<T> list, Function<T, CharSequence> convert, CharSequence delimiter) {
    return convert(list, convert, Collectors.joining(delimiter));
  }

  /**
   * 使用函数convert转换集合list中的元素，并使用collector收集集合中的元素
   *
   * @param list      待转换的集合
   * @param convert   转换函数
   * @param collector 收集器
   * @param <T>       待转化的元素类型
   * @return 收集后的对象
   */
  public <T, V, R> R convert(Collection<T> list, Function<T, V> convert, Collector<V, ?, R> collector) {
    return list.stream().map(convert).collect(collector);
  }

  /**
   * 使用分隔符separator分割字符串str，使用函数convert转换分割的结果，并使用collector收集集合中的元素
   *
   * @param str       待分割的字符串
   * @param separator 分割符
   * @param convert   转换函数
   * @param collector 收集器
   * @return 收集后的对象
   */
  public <V, R> R splitAndConvert(String str, CharSequence separator, Function<String, V> convert, Collector<V, ?, R> collector) {
    return convert(StrUtil.split(str, separator, -1, false, false), convert, collector);
  }

  /**
   * 使用收集器collector收集集合list中的元素
   *
   * @param list      待转换的集合
   * @param collector 收集器
   * @param <T>       待转化的元素类型
   * @param <R>       收集后的元素类型
   * @return 收集后的对象
   */
  public <T, R> R convert(Collection<T> list, Collector<T, ?, R> collector) {
    return list.stream().collect(collector);
  }
}
