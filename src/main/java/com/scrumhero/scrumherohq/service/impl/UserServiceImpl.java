package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto save(UserDto userDto) throws BadRequestException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        checkDuplicatedResource(userDto);

        User persistedUser = repository.saveAndFlush(modelMapper.map(userDto, User.class));

        LOGGER.info("User created: {}", persistedUser.getEmail());

        return modelMapper.map(persistedUser, UserDto.class);
    }

    @Override
    public UserDto update(UserDto user) throws ResourceNotFoundException, BadRequestException {
        checkIfExists(user.getId());
        checkDuplicatedResource(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User persistedUser = repository
                .saveAndFlush(modelMapper.map(user, User.class));

        LOGGER.debug("User updated: {}", user.getName());

        return modelMapper.map(persistedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAll() {
        Type type = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public UserDto getById(Long id) throws ResourceNotFoundException {
        Optional<User> user = repository.findById(id);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(user.get(), UserDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("User deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(UserDto user) throws BadRequestException {
        Optional<User> duplicatedResource;

        if (user.getId() == null) {
            duplicatedResource = repository.findByEmail(user.getEmail());
        } else {
            duplicatedResource = repository.findByEmailWhereIdIsNotEquals(user.getEmail(), user.getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid user, a user with this name already exists.");
        }
    }
}