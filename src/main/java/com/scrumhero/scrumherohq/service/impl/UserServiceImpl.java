package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
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

    public void save(UserDto userDto) throws BadRequestException {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = modelMapper.map(userDto, User.class);
        validateNewUser(user);
        repository.save(user);

        LOGGER.info("User created: {}", user.getEmail());
    }

    @Override
    public User loadUserByUsername(String email) {

        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Email not found: " + email);
        }

        LOGGER.debug("User found: {}", user.getEmail());

        return user;
    }

    private void validateNewUser(User user) throws BadRequestException {

        User duplicatedUser = repository.findByEmail(user.getEmail());

        if(duplicatedUser != null) {
            throw new BadRequestException("Invalid user, an user with this email already exists.");
        }
    }
}