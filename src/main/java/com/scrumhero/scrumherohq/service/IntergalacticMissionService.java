package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;

import java.util.List;

public interface IntergalacticMissionService {

    IntergalacticMissionDto save(IntergalacticMissionDto intergalacticMission) throws BadRequestException;

    IntergalacticMissionDto update(IntergalacticMissionDto intergalacticMission) throws ResourceNotFoundException, BadRequestException;

    List<IntergalacticMissionDto> getAll();

    IntergalacticMissionDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
