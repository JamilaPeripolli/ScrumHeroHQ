package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.EvolutionItemDto;
import com.scrumhero.scrumherohq.service.EvolutionItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/evolution-items")
public class EvolutionItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvolutionItemController.class);

    private EvolutionItemService service;

    @Autowired
    public EvolutionItemController(EvolutionItemService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity create(@RequestBody @Valid EvolutionItemDto evolutionItem) throws BadRequestException, ResourceNotFoundException {
        LOGGER.debug("Endpoint called: POST '/api/evolution-items'");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(evolutionItem));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid EvolutionItemDto evolutionItem) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/evolution-items/{}'", id);

        evolutionItem.setId(id);

        return ResponseEntity.ok().body(service.update(evolutionItem));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/evolution-items'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/evolution-items/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/evolution-items/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
