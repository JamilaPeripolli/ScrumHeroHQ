package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity add(@RequestBody User user) {
        try {
            service.save(user);

            LOGGER.info("User created: {}", user.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.warn("Error to create user: {}", user.getEmail());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity delete() {
        return null;
    }

    @RequestMapping("/hello")
    @GetMapping
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/home")
    @GetMapping
    @PreAuthorize("permitAll()")
    public String home() {
        return "welcome";
    }
}
