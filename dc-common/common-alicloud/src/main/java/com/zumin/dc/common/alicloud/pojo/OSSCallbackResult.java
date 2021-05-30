package com.zumin.dc.common.alicloud.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * oss上传文件的回调结果
 */
@Data
public class OSSCallbackResult<T> {

  @ApiModelProperty("文件地址")
  private String filePath;
  @ApiModelProperty("文件大小")
  private String size;
  @ApiModelProperty("文件的mimeType")
  private String mimeType;
  @ApiModelProperty("回调数据")
  private T callbackData;
}
