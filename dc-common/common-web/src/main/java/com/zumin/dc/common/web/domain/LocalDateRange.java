package com.zumin.dc.common.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("日期范围类")
public class LocalDateRange implements Serializable {

  private static final long serialVersionUID = -347642114082810320L;

  @ApiModelProperty("开始日期")
  private LocalDate start;

  @ApiModelProperty("结束日期")
  private LocalDate end;
}
