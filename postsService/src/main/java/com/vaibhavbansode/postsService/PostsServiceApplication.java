package com.vaibhavbansode.postsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PostsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsServiceApplication.class, args);
	}

}
