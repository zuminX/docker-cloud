package com.zumin.dc.ums.controller;


import com.zumin.dc.common.alicloud.pojo.OSSCallbackParameter;
import com.zumin.dc.common.alicloud.pojo.OSSCallbackResult;
import com.zumin.dc.common.alicloud.pojo.OSSPolicy;
import com.zumin.dc.common.alicloud.service.OSSService;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.BaseException;
import com.zumin.dc.common.web.annotation.ComRestController;
import com.zumin.dc.common.web.utils.SecurityUtils;
import com.zumin.dc.ums.config.ProfileConfig;
import com.zumin.dc.ums.config.ProfileConfig.AvatarProperties;
import com.zumin.dc.ums.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@ComRestController(path = "/profile", tags = "用户档案API接口")
public class ProfileController {

  private final UserService userService;

  private final OSSService ossService;

  private final ProfileConfig profileConfig;

  @PostMapping("/uploadAvatarPolicy")
  @ApiOperation("获取上传头像的凭证")
  public OSSPolicy getUploadAvatarPolicy() {
    AvatarProperties avatar = profileConfig.getAvatar();
    return ossService.policy(avatar.getMaxSize(), avatar.getDir(), new OSSCallbackParameter<>("profile/updateAvatarCallback", SecurityUtils.getUserId()));
  }

  @PostMapping("/updateAvatarCallback")
  @ApiOperation("更新头像的回调接口")
  public void updateAvatar() {
    OSSCallbackResult<Long> oosCallback = ossService.resolveCallbackData(Long.class);
    Long userId = oosCallback.getCallbackData();
    if (userId == null) {
      throw new BaseException(CommonStatusCode.UNAUTHORIZED_ACCESS);
    }
    userService.updateAvatar(oosCallback.getFilePath(), userId);
  }
}
