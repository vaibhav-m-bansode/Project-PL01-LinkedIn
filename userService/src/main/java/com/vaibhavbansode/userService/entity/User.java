package com.vaibhavbansode.userService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;




    @Column(nullable = false)
    private String password;

}
