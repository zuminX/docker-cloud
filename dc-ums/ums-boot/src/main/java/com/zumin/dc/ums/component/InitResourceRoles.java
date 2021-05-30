package com.zumin.dc.ums.component;


import com.zumin.dc.ums.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化资源角色信息
 */
@Component
@RequiredArgsConstructor
public class InitResourceRoles implements CommandLineRunner {

  private final ResourceService resourceService;

  /**
   * 容器启动完成时加载角色权限规则至Redis缓存
   *
   * @param args 参数
   */
  @Override
  public void run(String... args) {
    resourceService.refreshResourceRolesCache();
  }
}
