package com.zumin.dc.dockeradmin.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.zumin.dc.common.core.utils.ConvertUtils;
import com.zumin.dc.dockeradmin.pojo.vo.image.ImageStatsVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final DockerClient dockerClient;

  public List<InspectImageResponse> listOfAllImages() {
    return ConvertUtils.convert(dockerClient.listImagesCmd().exec(), this::inspect);
  }

  public InspectImageResponse listImage(String imageId) {
    return dockerClient.inspectImageCmd(imageId).exec();
  }

  public void removeImage(String imageId) {
    dockerClient.removeImageCmd(imageId).exec();
  }

  private InspectImageResponse inspect(Image image) {
    return dockerClient.inspectImageCmd(image.getId()).exec();
  }

  public ImageStatsVO getStatistics() {
    List<Image> images = dockerClient.listImagesCmd().exec();
    List<Image> danglingImages = dockerClient.listImagesCmd().withDanglingFilter(true).exec();
    return new ImageStatsVO(images.size(), danglingImages.size());
  }
}

