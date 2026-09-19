package com.vaibhavbansode.postsService.client;

import com.vaibhavbansode.postsService.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "connection-service",
        path = "/connections",
        url = "${CONNECTION_SERVICE_URI:}"
)
public interface ConnectionClient {

    @GetMapping("/core/{userId}")
    List<Long> getFirstDegreeConnection(@PathVariable Long userId);

    @GetMapping("/{userId}/second-degree")
    ResponseEntity<List<PersonDto>> findSecondDegreeConnections(@PathVariable Long userId);
}