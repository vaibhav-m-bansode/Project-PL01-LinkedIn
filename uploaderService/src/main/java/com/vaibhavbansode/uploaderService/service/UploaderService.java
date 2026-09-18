package com.vaibhavbansode.uploaderService.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {

    String upload(MultipartFile file);
}
