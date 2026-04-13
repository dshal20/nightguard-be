package com.nightguard.api.upload;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class R2Service {

  private final S3Client s3Client;

  @Value("${r2.bucket}")
  private String bucket;

  @Value("${r2.public-url}")
  private String publicUrl;

  public R2Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public String upload(MultipartFile file) throws IOException {
    String ext = getExtension(file.getOriginalFilename());
    String key = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(file.getContentType())
            .contentLength(file.getSize())
            .build(),
        RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    return publicUrl.replaceAll("/+$", "") + "/" + key;
  }

  private String getExtension(String filename) {
    if (filename == null || !filename.contains(".")) return "";
    return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
  }
}
