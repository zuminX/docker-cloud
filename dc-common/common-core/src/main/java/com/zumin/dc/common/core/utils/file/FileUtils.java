package com.zumin.dc.common.core.utils.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.FileException;
import com.zumin.dc.common.core.utils.DateUtils;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 */
@UtilityClass
public class FileUtils {

  /**
   * 连接文件路径
   *
   * @param path 路径
   * @return 连接后的路径
   */
  public String connect(Object... path) {
    return Arrays.stream(path).map(Object::toString).collect(Collectors.joining(FileUtil.FILE_SEPARATOR));
  }

  public String getAbsolutePath(String dir, String fileName) {
    return StrUtil.isBlank(fileName) ? dir : connect(dir, fileName);
  }

  /**
   * 编码文件名
   */
  public String extractFilename(MultipartFile file) {
    String extension = getExtension(file);
    return connect(DateUtils.plainDateStr(), UUID.fastUUID())  + "." + extension;
  }

  /**
   * 文件格式校验
   *
   * @param file 上传的文件
   */
  public void assertAllowed(MultipartFile file, String[] allowedExtension) {
    String extension = getExtension(file);
    if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
      if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
        throw new FileException(CommonStatusCode.FILE_EXPECT_IMAGE);
      } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
        throw new FileException(CommonStatusCode.FILE_EXPECT_MEDIA);
      } else {
        throw new FileException(CommonStatusCode.FILE_TYPE_ILLEGAL);
      }
    }
  }

  /**
   * 判断MIME类型是否是允许的MIME类型
   *
   * @param extension
   * @param allowedExtension
   * @return
   */
  public boolean isAllowedExtension(String extension, String[] allowedExtension) {
    return Arrays.stream(allowedExtension).anyMatch(str -> str.equalsIgnoreCase(extension));
  }

  /**
   * 获取文件名的后缀
   *
   * @param file 表单文件
   * @return 后缀名
   */
  public String getExtension(MultipartFile file) {
    String extension = FileNameUtil.getSuffix(file.getOriginalFilename());
    return StrUtil.isBlank(extension) ? MimeTypeUtils.getExtension(file.getContentType()) : extension;
  }

}
