package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.LeagueDto;

import java.util.List;

public interface LeagueService {

    LeagueDto save(LeagueDto league) throws BadRequestException;

    LeagueDto update(LeagueDto league) throws ResourceNotFoundException, BadRequestException;

    List<LeagueDto> getAll();

    LeagueDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
