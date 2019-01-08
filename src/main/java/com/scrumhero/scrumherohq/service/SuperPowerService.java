package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;

import java.util.List;

public interface SuperPowerService {

    SuperPowerDto save(SuperPowerDto superPower) throws BadRequestException;

    SuperPowerDto update(SuperPowerDto superPower) throws ResourceNotFoundException, BadRequestException;

    List<SuperPowerDto> getAll();

    SuperPowerDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
