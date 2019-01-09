package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.MissionDto;
import com.scrumhero.scrumherohq.model.entity.Mission;
import com.scrumhero.scrumherohq.model.type.MissionStatus;
import com.scrumhero.scrumherohq.repository.MissionRepository;
import com.scrumhero.scrumherohq.service.IntergalacticMissionService;
import com.scrumhero.scrumherohq.service.MissionService;
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
public class MissionServiceImpl implements MissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MissionServiceImpl.class);

    private MissionRepository repository;

    private IntergalacticMissionService imService;

    private ModelMapper modelMapper;

    @Autowired
    public MissionServiceImpl(MissionRepository repository,
                              IntergalacticMissionService imService, ModelMapper modelMapper) {
        this.repository = repository;
        this.imService = imService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MissionDto save(MissionDto mission) throws BadRequestException, ResourceNotFoundException {
        validateIntergalacticMission(mission);
        checkDuplicatedResource(mission);

        mission.setStartDate(null);
        mission.setEndDate(null);
        mission.setStatus(MissionStatus.CREATED);

        Mission persistedMission = repository
                .saveAndFlush(modelMapper.map(mission, Mission.class));

        LOGGER.debug("Mission created: {}", mission.getName());

        return modelMapper.map(persistedMission, MissionDto.class);
    }

    @Override
    public MissionDto update(MissionDto mission) throws ResourceNotFoundException, BadRequestException {
        checkDuplicatedResource(mission);

        MissionDto resource = getById(mission.getId());
        mission.setStartDate(resource.getStartDate());
        mission.setEndDate(resource.getEndDate());
        mission.setStatus(resource.getStatus());
        mission.setIntergalacticMission(resource.getIntergalacticMission());

        Mission persistedMission = repository
                .saveAndFlush(modelMapper.map(mission, Mission.class));

        LOGGER.debug("Mission updated: {}", mission.getName());

        return modelMapper.map(persistedMission, MissionDto.class);
    }

    @Override
    public List<MissionDto> getAll() {
        Type type = new TypeToken<List<MissionDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public MissionDto getById(Long id) throws ResourceNotFoundException {
        Optional<Mission> mission = repository.findById(id);

        if(!mission.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(mission.get(), MissionDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("Mission deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(MissionDto mission) throws BadRequestException {
        Optional<Mission> duplicatedResource;

        if (mission.getId() == null) {
            duplicatedResource = repository.findByName(mission.getName());
        } else {
            duplicatedResource = repository.findDuplicatedResource(mission.getName(), mission.getId(), mission.getIntergalacticMission().getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid mission, a resource with this name already exists.");
        }
    }

    private void validateIntergalacticMission(MissionDto mission) throws BadRequestException, ResourceNotFoundException {
        if(mission.getIntergalacticMission() == null
                || mission.getIntergalacticMission().getId() == null) {
            throw new BadRequestException("To create a mission it must be associated to an intergalactic mission. Please provide the required information.");
        }

        imService.getById(mission.getIntergalacticMission().getId());
    }
}
