package com.vaibhavbansode.uploaderService.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.vaibhavbansode.uploaderService.config.ImageValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleCloudUploaderService implements UploaderService {
    private final Storage storage;
    private final String gcsBucketName;
    private final ImageValidation imageValidation;

    @Override
    public String upload(MultipartFile file) {

        imageValidation.validateImage(file);

        String objectName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo =
                BlobInfo.newBuilder(gcsBucketName, objectName)
                        .setContentType(file.getContentType())
                        .build();
        try {
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.format("https://storage.googleapis.com/%s/%s", gcsBucketName, objectName);
    }
}
