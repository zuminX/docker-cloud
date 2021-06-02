package com.zumin.dc.dockerserve.pojo.vo;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationServeInfo {

  private Long id;
  private String name;
  private String state;
  private String description;
  private Long userId;
  private Set<Integer> portList;
}