package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.InvalidUserException;
import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        LOGGER.debug("/signup request, user: {}", user.getEmail());

        try {
            service.save(user);

            LOGGER.info("/signup response, status: {}", HttpStatus.CREATED);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (InvalidUserException e) {

            LOGGER.warn("Error to create user: {}", user.getEmail());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello Heros!");
    }

}
