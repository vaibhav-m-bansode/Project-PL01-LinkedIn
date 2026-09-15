package com.vaibhavbansode.postsService.client;

import com.vaibhavbansode.postsService.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Connection;
import java.util.List;

@FeignClient(
        name = "CONNECTION-SERVICE",
        path = "/connections"
)
public interface ConnectionClient {

    @GetMapping("/core/{userId}")
    List<Connection> getConnections(
            @PathVariable Long userId
    );
    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<List<PersonDto>> findSecondDegreeConnections( @PathVariable Long userId);
}