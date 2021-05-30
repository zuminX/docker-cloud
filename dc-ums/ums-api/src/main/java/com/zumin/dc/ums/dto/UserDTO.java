package com.zumin.dc.ums.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户传输层对象
 */
@Data
@NoArgsConstructor
public class UserDTO {

  private Long id;
  private String username;
  private String password;
  private Integer enabled;
  private List<Long> roleIds;
  private String clientId;
}
