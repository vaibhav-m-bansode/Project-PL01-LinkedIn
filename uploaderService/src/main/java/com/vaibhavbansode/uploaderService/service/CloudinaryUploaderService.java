package com.vaibhavbansode.uploaderService.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

//@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryUploaderService implements UploaderService {


    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            log.info("upload result is {}", uploadResult);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
