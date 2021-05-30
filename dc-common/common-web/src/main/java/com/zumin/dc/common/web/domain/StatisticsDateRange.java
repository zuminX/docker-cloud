package com.zumin.dc.common.web.domain;

import com.zumin.dc.common.web.enums.StatisticsDate;
import com.zumin.dc.common.web.validator.IsLocalDateRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统计日期范围类")
public class StatisticsDateRange {

  @ApiModelProperty(value = "日期范围", required = true)
  @IsLocalDateRange(startNotNull = true, endNotNull = true)
  private LocalDateRange dateRange;

  @ApiModelProperty(value = "统计日期", required = true)
  @NotNull(message = "统计日期不能为空")
  private StatisticsDate statisticsDate;
}
