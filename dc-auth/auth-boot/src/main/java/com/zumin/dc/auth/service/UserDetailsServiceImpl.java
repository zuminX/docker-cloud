package com.zumin.dc.auth.service;

import com.zumin.dc.auth.domain.User;
import com.zumin.dc.common.core.auth.AuthConstants;
import com.zumin.dc.common.core.auth.AuthStatusCode;
import com.zumin.dc.common.core.result.CommonResult;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.dto.UserDTO;
import com.zumin.dc.ums.feign.UserFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户认证和授权
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserFeign userFeign;

  /**
   * 实现根据用户名查找对应的用户详情
   *
   * @param username 用户名
   * @return 用户详情信息
   * @throws UsernameNotFoundException 用户名不存在异常
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String clientId = SecurityUtils.getAuthClientId();
    User user = null;
    switch (clientId) {
      case AuthConstants.USER_CLIENT_ID: // 后台用户
        CommonResult<UserDTO> userRes = userFeign.getUserByUsername(username);
        if (AuthStatusCode.USER_NOT_EXIST.getCode().equals(userRes.getCode())) {
          throw new UsernameNotFoundException(AuthStatusCode.USER_NOT_EXIST.getMessage());
        }
        UserDTO userDTO = userRes.getData();
        userDTO.setClientId(clientId);
        user = new User(userDTO);
        break;
      default:
        //TODO 多平台
        throw new UnsupportedOperationException("不存在该平台");
    }
    checkUserStatus(user);
    return user;
  }

  /**
   * 检验用户当前状态
   *
   * @param user 用户
   */
  private void checkUserStatus(User user) {
    if (!user.isEnabled()) {
      throw new DisabledException("该账户已被禁用!");
    } else if (!user.isAccountNonLocked()) {
      throw new LockedException("该账号已被锁定!");
    } else if (!user.isAccountNonExpired()) {
      throw new AccountExpiredException("该账号已过期!");
    }
  }


}
