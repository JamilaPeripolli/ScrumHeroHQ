package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.MissionDto;

import java.util.List;

public interface MissionService {

    MissionDto save(MissionDto mission) throws BadRequestException;

    MissionDto update(MissionDto mission) throws ResourceNotFoundException, BadRequestException;

    List<MissionDto> getAll();

    MissionDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
