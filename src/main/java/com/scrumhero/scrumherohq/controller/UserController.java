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
import java.util.List;
import java.util.stream.Collectors;

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

        UserDto userDto = service.save(user);
        userDto.setPassword(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid UserDto user) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/user/{}'", id);

        user.setId(id);

        UserDto userDto = service.update(user);
        userDto.setPassword(null);

        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/users'");

        List<UserDto> users =  service.getAll();
        users.stream().forEach(u -> u.setPassword(null));

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/users/{}'", id);

        UserDto userDto = service.getById(id);
        userDto.setPassword(null);

        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/users/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}