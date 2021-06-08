package com.zumin.dc.common.web.utils;

import static cn.hutool.core.annotation.AnnotationUtil.hasAnnotation;

import com.zumin.dc.common.web.annotation.ComRestController;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web工具类
 */
@UtilityClass
public class WebUtils {

  /**
   * 判断当前类是否为控制类
   *
   * @param clazz 类型的class对象
   * @return 若是控制类则返回true，否则返回false
   */
  public boolean isController(Class<?> clazz) {
    return hasAnnotation(clazz, Controller.class) || hasAnnotation(clazz, RestController.class) || hasAnnotation(clazz, ComRestController.class);
  }
}
