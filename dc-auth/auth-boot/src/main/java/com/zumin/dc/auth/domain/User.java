package com.zumin.dc.auth.domain;

import cn.hutool.core.collection.CollUtil;
import com.zumin.dc.ums.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录用户信息
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {

  private static final long serialVersionUID = -3451116629449574759L;

  private Long id;

  private String username;

  private String password;

  private Boolean enabled;

  private String clientId;

  private Collection<SimpleGrantedAuthority> authorities;

  public User(UserDTO user) {
    this.setId(user.getId());
    this.setUsername(user.getUsername());
    this.setPassword(user.getPassword());
    this.setEnabled(Objects.equals(1, user.getEnabled()));
    this.setClientId(user.getClientId());
    if (CollUtil.isNotEmpty(user.getRoleIds())) {
      authorities = new ArrayList<>();
      user.getRoleIds().forEach(roleId -> authorities.add(new SimpleGrantedAuthority(String.valueOf(roleId))));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
