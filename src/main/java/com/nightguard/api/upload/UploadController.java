package com.nightguard.api.upload;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

  private final R2Service r2Service;

  public UploadController(R2Service r2Service) {
    this.r2Service = r2Service;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
    String url = r2Service.upload(file);
    return ResponseEntity.ok(new UploadResponse(url));
  }
}
