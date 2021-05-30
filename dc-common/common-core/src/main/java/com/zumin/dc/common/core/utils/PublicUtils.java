package com.zumin.dc.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * 公共工具类
 */
@UtilityClass
public class PublicUtils {

  /**
   * 生成随机数的对象
   */
  private final Random RANDOM = new Random();

  /**
   * 获取随机唯一标识
   *
   * @return 唯一标识
   */
  public String getRandomIdentity() {
    return UUID.fastUUID().toString(true) + System.currentTimeMillis();
  }

  /**
   * 将二维Integer型List转为二维int数组
   *
   * @param list 列表
   * @return 转换后的二维数组
   */
  public int[][] unwrapIntArray(List<List<Integer>> list) {
    if (CollUtil.isEmpty(list)) {
      return new int[0][0];
    }

    final int row = list.size();
    final int[][] result = new int[row][];
    for (int i = 0; i < row; i++) {
      List<Integer> rowList = list.get(i);
      final int column = CollUtil.isEmpty(rowList) ? 0 : rowList.size();
      result[i] = new int[column];
      for (int j = 0; j < column; j++) {
        Integer element = rowList.get(j);
        result[i][j] = element == null ? 0 : element;
      }
    }
    return result;
  }

  public Integer[][] wrap(int[][] list) {
    return Arrays.stream(list).map(ArrayUtil::wrap).toArray(Integer[][]::new);
  }

  public Boolean[][] wrap(boolean[][] list) {
    return Arrays.stream(list).map(ArrayUtil::wrap).toArray(Boolean[][]::new);
  }

  /**
   * 将二维Boolean型List转为二维boolean数组
   *
   * @param list 列表
   * @return 转换后的二维数组
   */
  public boolean[][] unwrapBoolArray(List<List<Boolean>> list) {
    if (CollUtil.isEmpty(list)) {
      return new boolean[0][0];
    }

    final int row = list.size();
    final boolean[][] result = new boolean[row][];
    for (int i = 0; i < row; i++) {
      List<Boolean> rowList = list.get(i);
      final int column = CollUtil.isEmpty(rowList) ? 0 : rowList.size();
      result[i] = new boolean[column];
      for (int j = 0; j < column; j++) {
        Boolean element = rowList.get(j);
        result[i][j] = element != null && element;
      }
    }
    return result;
  }

  /**
   * 获取[min,max]的随机整数
   *
   * @param min 最小值
   * @param max 最大值
   * @return [min, max]之间的一个整数
   */
  public int getRandomInt(int min, int max) {
    if (min > max) {
      throw new IllegalArgumentException("最小值必须小于或等于最大值");
    }
    return RANDOM.nextInt(max - min + 1) + min;
  }

  /**
   * 深拷贝二维int型数组
   *
   * @param source 源数据
   * @return 深拷贝的数组
   */
  public int[][] deepClone(int[][] source) {
    if (source == null) {
      return null;
    }
    int[][] clone = new int[source.length][];
    for (int i = 0; i < source.length; i++) {
      clone[i] = new int[source[i].length];
      System.arraycopy(source[i], 0, clone[i], 0, source[i].length);
    }
    return clone;
  }

  /**
   * 深拷贝二维boolean型数组
   *
   * @param source 源数据
   * @return 深拷贝的数组
   */
  public boolean[][] deepClone(boolean[][] source) {
    if (source == null) {
      return null;
    }
    boolean[][] clone = new boolean[source.length][];
    for (int i = 0; i < source.length; i++) {
      clone[i] = new boolean[source[i].length];
      System.arraycopy(source[i], 0, clone[i], 0, source[i].length);
    }
    return clone;
  }

  /**
   * 利用Knuth洗牌算法打乱一维数组
   *
   * @param array 待打乱的数组
   */
  public void randomizedArray(int[] array) {
    for (int i = array.length - 1; i > 0; i--) {
      int random = getRandomInt(0, i);
      int temp = array[i];
      array[i] = array[random];
      array[random] = temp;
    }
  }

  /**
   * 利用Knuth洗牌算法打乱二维boolean型数组
   *
   * @param array 待打乱的数组
   */
  public void randomizedArray(boolean[][] array) {
    for (int row = array.length, column = array[0].length, i = row * column - 1; i > 0; i--) {
      int random = getRandomInt(0, i);
      boolean temp = array[i / row][i % column];
      array[i / row][i % column] = array[random / row][random % column];
      array[random / row][random % column] = temp;
    }
  }

  /**
   * 压缩二维int型数组为字符串
   *
   * @param array 二维int型数组
   * @return 对应的字符串
   */
  public String compressionIntArray(int[][] array) {
    if (ArrayUtil.isEmpty(array)) {
      return "";
    }
    return Arrays.stream(array).flatMapToInt(Arrays::stream).mapToObj(String::valueOf).collect(Collectors.joining());
  }

  /**
   * 压缩二维int型列表为字符串
   *
   * @param list 二维int型列表
   * @return 对应的字符串
   */
  public String compressionIntList(List<List<Integer>> list) {
    return PublicUtils.compressionIntArray(PublicUtils.unwrapIntArray(list));
  }

  /**
   * 压缩二维boolean型数组为字符串
   *
   * @param array 二维boolean型数组
   * @return 对应的字符串
   */
  public String compressionBoolArray(boolean[][] array) {
    if (ArrayUtil.isEmpty(array)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    Arrays.stream(array).forEach(booleans -> {
      for (boolean aBoolean : booleans) {
        sb.append(aBoolean ? 1 : 0);
      }
    });
    return sb.toString();
  }

  /**
   * 压缩二维boolean型列表为字符串
   *
   * @param list 二维boolean型列表
   * @return 对应的字符串
   */
  public String compressionBoolList(List<List<Boolean>> list) {
    return PublicUtils.compressionBoolArray(PublicUtils.unwrapBoolArray(list));
  }

  /**
   * 对于源对象与目标对象相同名称的字段，将源对象的字段赋值给目标对象
   *
   * @param source 源对象
   * @param target 目标对象
   * @return 赋值后的目标对象
   */
  @SneakyThrows
  public <T, V> V selectiveAssign(T source, V target) {
    Map<String, Object> sourceFieldValueMap = Arrays.stream(ClassUtil.getDeclaredFields(source.getClass()))
        .collect(Collectors.toMap(Field::getName, f -> getFieldValue(source, f)));
    Field[] targetFields = ClassUtil.getDeclaredFields(target.getClass());
    for (Field field : targetFields) {
      field.setAccessible(true);
      Object value = sourceFieldValueMap.get(field.getName());
      if (value != null && value.getClass().equals(field.getType())) {
        field.set(target, value);
      }
    }
    return target;
  }

  @SneakyThrows
  private Object getFieldValue(Object o, Field field) {
    field.setAccessible(true);
    return field.get(o);
  }
}
