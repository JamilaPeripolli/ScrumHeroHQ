package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.EvolutionItemDto;
import com.scrumhero.scrumherohq.model.dto.LeagueDto;
import com.scrumhero.scrumherohq.model.entity.EvolutionItem;
import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;
import com.scrumhero.scrumherohq.repository.EvolutionItemRepository;
import com.scrumhero.scrumherohq.service.EvolutionItemService;
import com.scrumhero.scrumherohq.service.LeagueService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class EvolutionItemServiceImpl implements EvolutionItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvolutionItemServiceImpl.class);

    private EvolutionItemRepository repository;

    private LeagueService leagueService;

    private ModelMapper modelMapper;

    @Autowired
    public EvolutionItemServiceImpl(EvolutionItemRepository repository,
                                    LeagueService leagueService, ModelMapper modelMapper) {
        this.repository = repository;
        this.leagueService = leagueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public EvolutionItemDto save(EvolutionItemDto evolutionItem) throws BadRequestException, ResourceNotFoundException {
        validateLeague(evolutionItem);
        checkDuplicatedResource(evolutionItem);

        evolutionItem.setStatus(EvolutionItemStatus.PENDING);

        EvolutionItem persistedEvolutionItem = repository
                .saveAndFlush(modelMapper.map(evolutionItem, EvolutionItem.class));

        LOGGER.debug("EvolutionItem created: {}", evolutionItem.getTitle());

        return modelMapper.map(persistedEvolutionItem, EvolutionItemDto.class);
    }

    @Override
    public EvolutionItemDto update(EvolutionItemDto evolutionItem) throws ResourceNotFoundException, BadRequestException {
        checkDuplicatedResource(evolutionItem);

        EvolutionItemDto resource = getById(evolutionItem.getId());
        evolutionItem.setStatus(resource.getStatus());

        EvolutionItem persistedEvolutionItem = repository
                .saveAndFlush(modelMapper.map(evolutionItem, EvolutionItem.class));

        LOGGER.debug("EvolutionItem updated: {}", evolutionItem.getTitle());

        return modelMapper.map(persistedEvolutionItem, EvolutionItemDto.class);
    }

    @Override
    public List<EvolutionItemDto> getAll() {
        Type type = new TypeToken<List<EvolutionItemDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public EvolutionItemDto getById(Long id) throws ResourceNotFoundException {
        Optional<EvolutionItem> evolutionItem = repository.findById(id);

        if(!evolutionItem.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(evolutionItem.get(), EvolutionItemDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("EvolutionItem deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(EvolutionItemDto evolutionItem) throws BadRequestException {
        Optional<EvolutionItem> duplicatedResource;

        if (evolutionItem.getId() == null) {
            duplicatedResource = repository.findByTitleAndLeagueId(evolutionItem.getTitle(), evolutionItem.getLeague().getId());
        } else {
            duplicatedResource = repository.findDuplicatedResource(evolutionItem.getTitle(), evolutionItem.getId(), evolutionItem.getLeague().getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid evolutionItem, a resource with this name already exists.");
        }
    }

    private void validateLeague(EvolutionItemDto evolutionItem) throws BadRequestException, ResourceNotFoundException {
        if(evolutionItem.getLeague() == null
                || evolutionItem.getLeague().getId() == null) {
            throw new BadRequestException("To create an Evolution Item it must be associated to a league. Please provide the required information.");
        }

        leagueService.getById(evolutionItem.getLeague().getId());
    }
}
