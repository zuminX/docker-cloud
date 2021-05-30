package com.zumin.dc.common.web.handler;

import com.zumin.dc.common.web.domain.CustomEditorInfo;
import com.zumin.dc.common.web.enums.StatisticsDate;
import java.util.Collections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局数据绑定处理器类
 */
@ControllerAdvice(basePackages = {"com.zumin.dc"})
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.data-binding'])}")
public class GlobalDataBindingHandler implements DataBindingHandler {

  /**
   * 初始化空的绑定信息
   *
   * @param binder 绑定器对象
   */
  @Override
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    initBinder(binder, Collections.singletonList(getStatisticsDateEditor()));
  }

  /**
   * 获取统计日期的自定义编辑信息对象
   *
   * @return 统计日期的自定义编辑信息对象
   */
  private CustomEditorInfo<StatisticsDate> getStatisticsDateEditor() {
    return new CustomEditorInfo<>(StatisticsDate.class, StatisticsDate::findByName);
  }

}