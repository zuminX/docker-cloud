package com.zumin.dc.common.core.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import com.zumin.dc.common.core.enums.CommonStatusCode;
import com.zumin.dc.common.core.exception.FileException;
import com.zumin.dc.common.core.pojo.FileSaveInfo;
import com.zumin.dc.common.core.utils.PublicUtils;
import com.zumin.dc.common.core.utils.file.FileUtils;
import java.io.File;
import java.io.InputStream;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  private static final String TEMP_PATH = "/tmp";

  public File save(FileSaveInfo info) {
    InputStream stream = info.getFileStream();
    File file = FileUtil.writeFromStream(stream, info.getPath());
    if (file.length() > info.getMaxSize()) {
      throw new FileException(CommonStatusCode.FILE_TOO_LARGE);
    }
    if (CollUtil.isNotEmpty(info.getAllowExtensions())) {
      if (!info.getAllowExtensions().contains(info.getType())) {
        throw new FileException(CommonStatusCode.FILE_TYPE_ILLEGAL);
      }
    }
    return file;
  }

  public void openTempFile(String fileName, Consumer<File> consumer) {
    File file = new File(FileUtils.connect(getTempDirectory(), fileName));
    FileUtil.touch(file);
    consumer.accept(file);
    FileUtil.del(file);
  }

  public String getTempDirectory() {
    return FileUtils.connect(TEMP_PATH, PublicUtils.getRandomIdentity());
  }

}
