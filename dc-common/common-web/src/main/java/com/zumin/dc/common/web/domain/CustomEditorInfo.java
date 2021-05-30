package com.zumin.dc.common.web.domain;

import com.zumin.dc.common.web.convert.StringConvert;
import com.zumin.dc.common.web.exception.FormParameterConversionException;
import java.beans.PropertyEditor;
import lombok.Getter;
import org.springframework.beans.PropertyValuesEditor;

/**
 * 自定义编辑信息类
 */
@Getter
public class CustomEditorInfo<T> {

  private final Class<T> requiredType;

  private final PropertyEditor propertyEditor;

  /**
   * 自定义编辑信息类的构造方法
   *
   * @param requiredType 需要的类型
   * @param callback     将字符串转换为需要类型对象的转换器
   */
  public CustomEditorInfo(Class<T> requiredType, StringConvert<T> callback) {
    this.requiredType = requiredType;
    this.propertyEditor = new PropertyValuesEditor() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        T data = callback.convert(text);
        if (data == null) {
          throw new FormParameterConversionException(text, requiredType);
        }
        setValue(data);
      }
    };
  }
}