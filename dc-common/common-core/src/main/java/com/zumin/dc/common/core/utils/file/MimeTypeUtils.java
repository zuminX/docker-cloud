package com.zumin.dc.common.core.utils.file;

import lombok.experimental.UtilityClass;

/**
 * 媒体类型工具类
 */
@UtilityClass
public class MimeTypeUtils {

  public final String IMAGE_PNG = "image/png";

  public final String IMAGE_JPG = "image/jpg";

  public final String IMAGE_JPEG = "image/jpeg";

  public final String IMAGE_BMP = "image/bmp";

  public final String IMAGE_GIF = "image/gif";

  public final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};

  public final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"};

  public final String[] DEFAULT_ALLOWED_EXTENSION = {
      // 图片
      "bmp", "gif", "jpg", "jpeg", "png",
      // word excel powerpoint
      "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
      // 压缩文件
      "rar", "zip", "gz", "bz2",
      // pdf
      "pdf"};

  public String getExtension(String prefix) {
    switch (prefix) {
      case IMAGE_PNG:
        return "png";
      case IMAGE_JPG:
        return "jpg";
      case IMAGE_JPEG:
        return "jpeg";
      case IMAGE_BMP:
        return "bmp";
      case IMAGE_GIF:
        return "gif";
      default:
        return "";
    }
  }
}
