package com.vaibhavbansode.userService.controller;

import com.vaibhavbansode.userService.dto.LoginRequestDto;
import com.vaibhavbansode.userService.dto.SignupRequest;
import com.vaibhavbansode.userService.dto.UserDto;
import com.vaibhavbansode.userService.entity.User;
import com.vaibhavbansode.userService.service.userService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final userService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserDto userDto = userService.signup(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
       String token =  userService.login(loginRequestDto);
       return ResponseEntity.ok(token);
    }
}
