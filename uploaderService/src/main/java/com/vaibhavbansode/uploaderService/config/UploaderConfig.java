package com.vaibhavbansode.uploaderService.config;

import com.cloudinary.Cloudinary;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class UploaderConfig {

    @Value("${my_cloud_name}")
    private String cloudName;

    @Value("${my_api_key}")
    private String apiKey;

    @Value("${my_api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> map = Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );
        return new Cloudinary(map);
    }


    @Value("${gcp.bucket-name}")
    private String bucketName;

    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance()
                .getService();
    }

    @Bean
    public String gcsBucketName() {
        return bucketName;
    }
}
