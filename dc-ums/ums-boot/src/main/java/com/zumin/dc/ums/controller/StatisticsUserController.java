package com.zumin.dc.ums.controller;


import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.domain.LocalDateRange;
import com.zumin.dc.common.web.domain.StatisticsDateRange;
import com.zumin.dc.common.web.enums.StatisticsDate;
import com.zumin.dc.ums.pojo.bo.StatisticsUserDataBO;
import com.zumin.dc.ums.service.StatisticsUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/statistics", tags = "统计信息API接口")
public class StatisticsUserController {

  private final StatisticsUserService statisticsUserService;

  @PostMapping("/user/assignDate")
  @ApiOperation("获取指定日期的用户统计数据")
  @ApiImplicitParam(name = "dateRange", value = "统计日期范围", dataTypeClass = StatisticsDateRange.class, required = true)
  public List<StatisticsUserDataBO> getAssignDateUserData(@RequestBody @Valid StatisticsDateRange dateRange) {
    return statisticsUserService.getStatisticsUserData(dateRange);
  }

  @GetMapping("/user/recentDate")
  @ApiOperation("获取最近日期的用户统计数据")
  @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  public List<StatisticsUserDataBO> getRecentDateUserData(@RequestParam StatisticsDate date) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = date.minus(endDate, 7L);
    return getAssignDateUserData(new StatisticsDateRange(new LocalDateRange(startDate, endDate), date));
  }

  @GetMapping("/user/total")
  @ApiOperation("获取系统的用户总数")
  public Integer getUserTotal() {
    return statisticsUserService.getUserTotal();
  }
}
