package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void save(User user);

}
