package com.zumin.dc.common.web.utils;

import static cn.hutool.core.annotation.AnnotationUtil.hasAnnotation;

import cn.hutool.core.annotation.AnnotationUtil;
import com.zumin.dc.common.web.annotation.ComRestController;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@UtilityClass
public class WebUtils {

  public boolean isController(Class<?> clazz) {
    return hasAnnotation(clazz, Controller.class) || hasAnnotation(clazz, RestController.class) || hasAnnotation(clazz, ComRestController.class);
  }
}
