package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.MissionDto;
import com.scrumhero.scrumherohq.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MissionController.class);

    private MissionService service;

    @Autowired
    public MissionController(MissionService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity create(@RequestBody @Valid MissionDto mission) throws BadRequestException, ResourceNotFoundException {
        LOGGER.debug("Endpoint called: POST '/api/missions'");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(mission));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid MissionDto mission) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/missions/{}'", id);

        mission.setId(id);

        return ResponseEntity.ok().body(service.update(mission));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/missions'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/missions/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/missions/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
