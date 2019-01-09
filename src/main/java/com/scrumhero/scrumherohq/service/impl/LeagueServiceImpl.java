package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.LeagueDto;
import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.repository.LeagueRepository;
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
public class LeagueServiceImpl implements LeagueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueServiceImpl.class);

    private LeagueRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public LeagueServiceImpl(LeagueRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeagueDto save(LeagueDto league) throws BadRequestException {
        checkDuplicatedResource(league);

        League persistedLeague = repository
                .saveAndFlush(modelMapper.map(league, League.class));

        LOGGER.debug("League created: {}", league.getName());

        return modelMapper.map(persistedLeague, LeagueDto.class);
    }

    @Override
    public LeagueDto update(LeagueDto league) throws ResourceNotFoundException, BadRequestException {
        checkIfExists(league.getId());
        checkDuplicatedResource(league);

        League persistedLeague = repository
                .saveAndFlush(modelMapper.map(league, League.class));

        LOGGER.debug("League updated: {}", league.getName());

        return modelMapper.map(persistedLeague, LeagueDto.class);
    }

    @Override
    public List<LeagueDto> getAll() {
        Type type = new TypeToken<List<LeagueDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public LeagueDto getById(Long id) throws ResourceNotFoundException {
        Optional<League> league = repository.findById(id);

        if(!league.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(league.get(), LeagueDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("League deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(LeagueDto league) throws BadRequestException {
        Optional<League> duplicatedResource;

        if (league.getId() == null) {
            duplicatedResource = repository.findByName(league.getName());
        } else {
            duplicatedResource = repository.findByNameWhereIdIsNotEquals(league.getName(), league.getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid superpower, a superpower with this name already exists.");
        }
    }
}
