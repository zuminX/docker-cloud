package com.zumin.dc.common.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertUtils {

  public <T, V> List<V> convert(Collection<T> list, Function<T, V> convert) {
    return convert(list, convert, Collectors.toList());
  }

  public <T> String convertAndJoin(Collection<T> list, Function<T, CharSequence> convert, CharSequence delimiter) {
    return convert(list, convert, Collectors.joining(delimiter));
  }

  public <T, V, R> R convert(Collection<T> list, Function<T, V> convert, Collector<V, ?, R> collector) {
    return list.stream().map(convert).collect(collector);
  }
}
