package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity signup(@RequestBody @Valid UserDto user) throws Exception {

        LOGGER.debug("Endpoint called: POST '/api/user/signup, new user: {}", user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid UserDto user) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/user/{}'", id);

        user.setId(id);

        return ResponseEntity.ok().body(service.update(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/users'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/users/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/users/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}