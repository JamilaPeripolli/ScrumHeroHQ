package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.model.entity.SuperPower;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.repository.SuperPowerRepository;
import com.scrumhero.scrumherohq.service.SuperPowerService;
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
public class SuperPowerServiceImpl implements SuperPowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperPowerServiceImpl.class);

    private SuperPowerRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public SuperPowerServiceImpl(SuperPowerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SuperPowerDto save(SuperPowerDto superPower) throws BadRequestException {
        checkDuplicatedResource(superPower);

        SuperPower persistedSuperPower = repository
                .saveAndFlush(modelMapper.map(superPower, SuperPower.class));

        LOGGER.debug("SuperPower created: {}", superPower.getName());

        return modelMapper.map(persistedSuperPower, SuperPowerDto.class);
    }

    @Override
    public SuperPowerDto update(SuperPowerDto superPower) throws ResourceNotFoundException {
        checkIfExists(superPower.getId());

        SuperPower persistedSuperPower = repository
                .saveAndFlush(modelMapper.map(superPower, SuperPower.class));

        LOGGER.debug("SuperPower updated: {}", superPower.getName());

        return modelMapper.map(persistedSuperPower, SuperPowerDto.class);
    }

    @Override
    public List<SuperPowerDto> getAll() {
        Type type = new TypeToken<List<SuperPowerDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public SuperPowerDto getById(Long id) throws ResourceNotFoundException {
        Optional<SuperPower> superPower = repository.findById(id);

        if(!superPower.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(superPower.get(), SuperPowerDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("SuperPower deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(SuperPowerDto superPower) throws BadRequestException {
        Optional<SuperPower> duplicatedResource = repository.findByName(superPower.getName());

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid superpower, a superpower with this name already exists.");
        }
    }
}
