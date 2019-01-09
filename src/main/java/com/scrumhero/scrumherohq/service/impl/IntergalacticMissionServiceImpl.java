package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.type.MissionStatus;
import com.scrumhero.scrumherohq.repository.IntergalacticMissionRepository;
import com.scrumhero.scrumherohq.service.IntergalacticMissionService;
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
public class IntergalacticMissionServiceImpl implements IntergalacticMissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntergalacticMissionServiceImpl.class);

    private IntergalacticMissionRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public IntergalacticMissionServiceImpl(IntergalacticMissionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public IntergalacticMissionDto save(IntergalacticMissionDto intergalacticMission) throws BadRequestException {
        checkDuplicatedResource(intergalacticMission);

        intergalacticMission.setStartDate(null);
        intergalacticMission.setEndDate(null);
        intergalacticMission.setStatus(MissionStatus.CREATED);

        IntergalacticMission persistedIntergalacticMission = repository
                .saveAndFlush(modelMapper.map(intergalacticMission, IntergalacticMission.class));

        LOGGER.debug("IntergalacticMission created: {}", intergalacticMission.getName());

        return modelMapper.map(persistedIntergalacticMission, IntergalacticMissionDto.class);
    }

    @Override
    public IntergalacticMissionDto update(IntergalacticMissionDto intergalacticMission) throws ResourceNotFoundException, BadRequestException {
        checkDuplicatedResource(intergalacticMission);

        IntergalacticMissionDto resource = getById(intergalacticMission.getId());
        intergalacticMission.setStartDate(resource.getStartDate());
        intergalacticMission.setEndDate(resource.getEndDate());
        intergalacticMission.setStatus(resource.getStatus());

        IntergalacticMission persistedIntergalacticMission = repository
                .saveAndFlush(modelMapper.map(intergalacticMission, IntergalacticMission.class));

        LOGGER.debug("IntergalacticMission updated: {}", intergalacticMission.getName());

        return modelMapper.map(persistedIntergalacticMission, IntergalacticMissionDto.class);
    }

    @Override
    public List<IntergalacticMissionDto> getAll() {
        Type type = new TypeToken<List<IntergalacticMissionDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public IntergalacticMissionDto getById(Long id) throws ResourceNotFoundException {
        Optional<IntergalacticMission> intergalacticMission = repository.findById(id);

        if(!intergalacticMission.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(intergalacticMission.get(), IntergalacticMissionDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("IntergalacticMission deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(IntergalacticMissionDto intergalacticMission) throws BadRequestException {
        Optional<IntergalacticMission> duplicatedResource;

        if (intergalacticMission.getId() == null) {
            duplicatedResource = repository.findByName(intergalacticMission.getName());
        } else {
            duplicatedResource = repository.findByNameWhereIdIsNotEquals(intergalacticMission.getName(), intergalacticMission.getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid intergalactic mission, a resource with this name already exists.");
        }
    }


}
