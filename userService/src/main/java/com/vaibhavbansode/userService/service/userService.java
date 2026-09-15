package com.vaibhavbansode.userService.service;

import com.vaibhavbansode.userService.dto.LoginRequestDto;
import com.vaibhavbansode.userService.dto.SignupRequest;
import com.vaibhavbansode.userService.dto.UserDto;
import com.vaibhavbansode.userService.entity.User;
import com.vaibhavbansode.userService.exception.BadRequestException;
import com.vaibhavbansode.userService.mapper.UserMapper;
import com.vaibhavbansode.userService.repository.UserRepository;
import com.vaibhavbansode.userService.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserDto signup(SignupRequest signupRequest) {
    log.info("signup request received with email: {}",signupRequest.email());
    Boolean isExist = userRepository.existsByEmail(signupRequest.email());
    if (isExist) {
        throw new BadRequestException("Email already exists");
    }
    User user = userMapper.toEntity(signupRequest);
    user.setPassword(BCrypt.hashPassword(signupRequest.password()));
    User savedUser = userRepository.save(user);
    return userMapper.toDto(savedUser);
    }

    public String login(LoginRequestDto loginRequestDto) {
    log.info("login request for email: {}",loginRequestDto.email());
    User user = userRepository.getUserByEmail(loginRequestDto.email()).orElseThrow(()->new BadRequestException("Invalid email"));
    return jwtService.generateToken(user);
    }
}
