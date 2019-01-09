package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;
import com.scrumhero.scrumherohq.service.IntergalacticMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/intergalactic-missions")
public class IntergalacticMissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntergalacticMissionController.class);

    private IntergalacticMissionService service;

    @Autowired
    public IntergalacticMissionController(IntergalacticMissionService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity create(@RequestBody @Valid IntergalacticMissionDto intergalacticMission) throws BadRequestException {
        LOGGER.debug("Endpoint called: POST '/api/intergalactic-missions'");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(intergalacticMission));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid IntergalacticMissionDto intergalacticMission) throws ResourceNotFoundException, BadRequestException {
        LOGGER.debug("Endpoint called: PUT '/api/intergalactic-missions/{}'", id);

        intergalacticMission.setId(id);

        return ResponseEntity.ok().body(service.update(intergalacticMission));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getAll() {
        LOGGER.debug("Endpoint called: GET '/api/intergalactic-missions'");

        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity getOne(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: GET '/api/intergalactic-missions/{}'", id);

        return ResponseEntity.ok().body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("Endpoint called: DELETE '/api/intergalactic-missions/{}'", id);

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
