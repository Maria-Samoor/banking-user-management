package com.exalt.training.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 * <p>
 * This class contains the {@code main} method which is used to launch the application.
 * The {@code @SpringBootApplication} annotation denotes that this is a Spring Boot application.
 * </p>
 */
@SpringBootApplication
public class UserService {

    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
    }
}