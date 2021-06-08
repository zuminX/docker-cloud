package com.zumin.dc.ums.service;

import com.zumin.dc.common.redis.annotation.ExtCacheable;
import com.zumin.dc.common.web.domain.StatisticsDateRange;
import com.zumin.dc.common.web.template.GetStatisticsDataTemplate;
import com.zumin.dc.ums.mapper.UserMapper;
import com.zumin.dc.ums.pojo.bo.StatisticsUserDataBO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户统计的业务层类
 */
@Service
@RequiredArgsConstructor
public class StatisticsUserService {

  private final UserMapper userMapper;

  /**
   * 获取在[startDate,endDate)中的用户统计信息列表
   *
   * @param dateRange 统计日期范围
   * @return 用户统计信息列表
   */
  @ExtCacheable(value = "statisticsUserData", ttlOfDays = 1)
  public List<StatisticsUserDataBO> getStatisticsUserData(StatisticsDateRange dateRange) {
    return new GetStatisticsDataTemplate<StatisticsUserDataBO>(dateRange).getData((firstDate, lastDate) -> {
      Integer newUserTotal = userMapper.countNewUserByDateBetween(firstDate, lastDate);
      Integer recentLoginUserTotal = userMapper.countRecentLoginUserByDateBetween(firstDate, lastDate);
      return new StatisticsUserDataBO(newUserTotal, recentLoginUserTotal);
    });
  }

  /**
   * 获取用户总数
   *
   * @return 用户总数
   */
  @ExtCacheable(value = "userTotal", ttlOfDays = 1)
  public Integer getUserTotal() {
    return userMapper.selectCount(null);
  }
}
