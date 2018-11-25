package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void save(UserDto user) throws BadRequestException;

}
