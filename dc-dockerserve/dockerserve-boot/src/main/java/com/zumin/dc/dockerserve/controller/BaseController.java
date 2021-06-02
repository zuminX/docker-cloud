package com.zumin.dc.dockerserve.controller;

import com.zumin.dc.common.web.domain.CustomEditorInfo;
import com.zumin.dc.common.web.handler.DataBindingHandler;
import com.zumin.dc.dockerserve.pojo.entity.ApplicationEntity;
import com.zumin.dc.dockerserve.pojo.entity.ImageEntity;
import com.zumin.dc.dockerserve.pojo.entity.ServeEntity;
import com.zumin.dc.dockerserve.service.ApplicationService;
import com.zumin.dc.dockerserve.service.ImageService;
import com.zumin.dc.dockerserve.service.ServeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 基础控制器类
 */
@Controller
public class BaseController implements DataBindingHandler {

  @Autowired
  private ImageService imageService;
  @Autowired
  private ApplicationService applicationService;
  @Autowired
  private ServeService serveService;

  /**
   * 初始化绑定信息
   *
   * @param binder 绑定器对象
   */
  @Override
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    initBinder(binder, getCustomEditorInfoList());
  }

  /**
   * 设置自定义编辑信息列表
   *
   * @return 自定义编辑信息对象列表
   */
  private List<CustomEditorInfo<?>> getCustomEditorInfoList() {
    List<CustomEditorInfo<?>> customEditorInfoList = new ArrayList<>();
    customEditorInfoList.add(getImageEntityEditor());
    customEditorInfoList.add(getApplicationEditor());
    customEditorInfoList.add(getServeEditor());
    return customEditorInfoList;
  }

  /**
   * 获取镜像实体类的自定义编辑信息对象
   *
   * @return 镜像实体类的自定义编辑信息对象
   */
  private CustomEditorInfo<ImageEntity> getImageEntityEditor() {
    return new CustomEditorInfo<>(ImageEntity.class, imageService::getById);
  }

  /**
   * 获取应用实体类的自定义编辑信息对象
   *
   * @return 应用实体类的自定义编辑信息对象
   */
  private CustomEditorInfo<ApplicationEntity> getApplicationEditor() {
    return new CustomEditorInfo<>(ApplicationEntity.class, applicationService::getById);
  }

  /**
   * 获取服务实体类的自定义编辑信息对象
   *
   * @return 服务实体类的自定义编辑信息对象
   */
  private CustomEditorInfo<ServeEntity> getServeEditor() {
    return new CustomEditorInfo<>(ServeEntity.class, serveService::getById);
  }

}
