package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.zumin.dc.dockeradmin.pojo.vo.image.ImageStatsVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 镜像的服务层类
 */
@Service
@RequiredArgsConstructor
public class ImageService {

  private final DockerClient dockerClient;

  /**
   * 列出所有镜像
   *
   * @return 镜像列表
   */
  public List<Image> list() {
    return dockerClient.listImagesCmd().exec();
  }

  /**
   * 根据镜像ID获取镜像详情
   *
   * @param id 镜像ID
   * @return 镜像详情
   */
  public InspectImageResponse getById(String id) {
    return dockerClient.inspectImageCmd(id).exec();
  }

  /**
   * 删除指定的镜像
   *
   * @param id 镜像ID
   */
  public void remove(String id) {
    dockerClient.removeImageCmd(id).exec();
  }

  /**
   * 获取镜像统计信息
   *
   * @return 镜像统计信息
   */
  public ImageStatsVO getStatistics() {
    List<Image> images = dockerClient.listImagesCmd().exec();
    List<Image> danglingImages = dockerClient.listImagesCmd().withDanglingFilter(true).exec();
    return new ImageStatsVO(images.size(), danglingImages.size());
  }
}

