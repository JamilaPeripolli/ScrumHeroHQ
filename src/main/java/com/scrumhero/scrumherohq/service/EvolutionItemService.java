package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.EvolutionItemDto;

import java.util.List;

public interface EvolutionItemService {

    EvolutionItemDto save(EvolutionItemDto evolutionItem) throws BadRequestException, ResourceNotFoundException;

    EvolutionItemDto update(EvolutionItemDto evolutionItem) throws ResourceNotFoundException, BadRequestException;

    List<EvolutionItemDto> getAll();

    EvolutionItemDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
