package com.vaibhavbansode.userService.service;

import com.vaibhavbansode.userService.dto.LoginRequestDto;
import com.vaibhavbansode.userService.dto.SignupRequest;
import com.vaibhavbansode.userService.dto.UserDto;
import com.vaibhavbansode.userService.entity.User;
import com.vaibhavbansode.userService.exception.BadRequestException;
import com.vaibhavbansode.userService.feignClient.ConnectionClient;
import com.vaibhavbansode.userService.mapper.UserMapper;
import com.vaibhavbansode.userService.repository.UserRepository;
import com.vaibhavbansode.userService.util.BCrypt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class userService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final ConnectionClient connectionClient;


    @Transactional
    public UserDto signup(SignupRequest signupRequest) {

    log.info("signup request received with email: {}",signupRequest.email());

        Boolean isExist = userRepository.existsByEmail(signupRequest.email());
    if (isExist) {
        throw new BadRequestException("Email already exists");
    }

        User user = userMapper.toEntity(signupRequest);
    user.setPassword(BCrypt.hashPassword(signupRequest.password()));
    User savedUser = userRepository.save(user);

//saving the user to connections neo4J DB

        log.info("Before calling Connection Service. userId={}", user.getId());

        connectionClient.createPerson(user.getId());

        log.info("After calling Connection Service. userId={}", user.getId());
    return userMapper.toDto(savedUser);

    }

    public String login(LoginRequestDto loginRequestDto) {
    log.info("login request for email: {}",loginRequestDto.email());
    User user = userRepository.getUserByEmail(loginRequestDto.email()).orElseThrow(()->new BadRequestException("Invalid email"));
    return jwtService.generateToken(user);
    }
}
