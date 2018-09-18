package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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
    @PreAuthorize("permitAll()")
    public ResponseEntity add(@RequestBody @Valid User user) throws Exception {

        LOGGER.debug("/signup request, user: {}", user.getEmail());

        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
