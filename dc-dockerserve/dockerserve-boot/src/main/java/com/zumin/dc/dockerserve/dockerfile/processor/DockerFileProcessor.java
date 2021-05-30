package com.zumin.dc.dockerserve.dockerfile.processor;

import com.zumin.dc.common.core.pojo.FileSaveInfo;

public interface DockerFileProcessor {

  DockerFileProcessor DEFAULT = (info, dockerFileContent) -> dockerFileContent;

  String process(FileSaveInfo info, String dockerFileContent);
}
