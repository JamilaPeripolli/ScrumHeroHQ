package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto save(UserDto user) throws BadRequestException;

    UserDto update(UserDto user) throws ResourceNotFoundException, BadRequestException;

    List<UserDto> getAll();

    UserDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}