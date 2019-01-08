package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.service.SuperPowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/super-powers")
public class SuperPowerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperPowerController.class);

    private SuperPowerService service;

    @Autowired
    public SuperPowerController(SuperPowerService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity create(@RequestBody @Valid SuperPowerDto superPower) throws BadRequestException {
        LOGGER.debug("Endpoint called: POST '/api/super-powers'");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(superPower));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid SuperPowerDto superPower) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: PUT '/api/super-powers/{}'", id);

        superPower.setId(id);

        return ResponseEntity.ok().body(service.update(superPower));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/super-powers'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/super-powers/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/super-powers/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
