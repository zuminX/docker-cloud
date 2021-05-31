package com.zumin.dc.common.core.pojo;

import com.zumin.dc.common.core.utils.file.FileUtils;
import java.io.InputStream;
import java.util.List;
import lombok.Getter;

/**
 * 文件保存信息
 */
@Getter
public class FileSaveInfo {

  private final InputStream fileStream;
  private final String name;
  private final String type;
  private final String directory;
  private final long maxSize;
  private final List<String> allowExtensions;

  FileSaveInfo(InputStream fileStream, String name, String type, String directory, long maxSize, List<String> allowExtensions) {
    this.fileStream = fileStream;
    this.name = name;
    this.type = type;
    this.directory = directory;
    this.maxSize = maxSize;
    this.allowExtensions = allowExtensions;
  }

  public static FileSaveInfoBuilder builder() {
    return new FileSaveInfoBuilder();
  }

  public String getPath() {
    return FileUtils.connect(directory, name + '.' + type);
  }

  public String getType() {
    return type;
  }

  public static class FileSaveInfoBuilder {

    private InputStream fileStream;
    private String name;
    private String type;
    private String directory;
    private long maxSize = Long.MAX_VALUE;
    private List<String> allowExtensions;

    FileSaveInfoBuilder() {
    }

    public FileSaveInfoBuilder fileStream(InputStream fileStream) {
      this.fileStream = fileStream;
      return this;
    }

    public FileSaveInfoBuilder name(String name) {
      this.name = name;
      return this;
    }

    public FileSaveInfoBuilder type(String type) {
      this.type = type;
      return this;
    }

    public FileSaveInfoBuilder directory(String directory) {
      this.directory = directory;
      return this;
    }

    public FileSaveInfoBuilder maxSize(long maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public FileSaveInfoBuilder allowExtensions(List<String> allowExtensions) {
      this.allowExtensions = allowExtensions;
      return this;
    }

    public FileSaveInfo build() {
      return new FileSaveInfo(fileStream, name, type, directory, maxSize, allowExtensions);
    }
  }
}