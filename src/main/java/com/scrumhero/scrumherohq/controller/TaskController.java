package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.TaskDto;
import com.scrumhero.scrumherohq.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity create(@RequestBody @Valid TaskDto task) throws BadRequestException, ResourceNotFoundException {
        LOGGER.debug("Endpoint called: POST '/api/tasks'");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(task));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid TaskDto task) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/tasks/{}'", id);

        task.setId(id);

        return ResponseEntity.ok().body(service.update(task));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/tasks'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/tasks/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/tasks/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
