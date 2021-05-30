package com.zumin.dc.common.web.template;

import com.zumin.dc.common.web.domain.LocalDateRange;
import com.zumin.dc.common.web.domain.StatisticsDateRange;
import com.zumin.dc.common.web.enums.StatisticsDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * 获取统计数据的模板方法
 *
 * @param <T> 统计数据的类型
 */
@AllArgsConstructor
public class GetStatisticsDataTemplate<T> {

  private final StatisticsDateRange dateRange;

  /**
   * 获取统计数据
   *
   * @param callback 获取统计数据的回调方法
   * @return 统计数据列表
   */
  public List<T> getData(StatisticsDataCallback<T> callback) {
    StatisticsDate statisticsDate = dateRange.getStatisticsDate();
    LocalDateRange range = dateRange.getDateRange();
    LocalDate nowDate = statisticsDate.getFirst(range.getStart()), lastDate = statisticsDate.getFirst(range.getEnd());
    List<T> list = new ArrayList<>();
    while (nowDate.compareTo(lastDate) < 0) {
      LocalDate nextDate = statisticsDate.next(nowDate);
      list.add(callback.getData(nowDate, nextDate));
      nowDate = nextDate;
    }
    return list;
  }

  /**
   * 获取统计数据的回调方法
   *
   * @param <T> 统计数据的类型
   */
  public interface StatisticsDataCallback<T> {

    /**
     * 获取[startDate,endDate)中的统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 统计数据
     */
    T getData(LocalDate startDate, LocalDate endDate);
  }

}