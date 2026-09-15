package com.vaibhavbansode.userService.repository;

import com.vaibhavbansode.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> getUserByEmail(String email);
}