package com.vaibhavbansode.userService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "connection-service",
        path = "/connections"
)
public interface ConnectionClient {

    @PostMapping("/core/internal")
    ResponseEntity<Void> createPerson(
            @RequestParam Long userId);
}
