package com.vaibhavbansode.uploaderService.controller;

import com.vaibhavbansode.uploaderService.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class UploaderController {

    private final UploaderService uploaderService;

    @PostMapping
    public ResponseEntity<String> uploadfile(@RequestParam MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}
