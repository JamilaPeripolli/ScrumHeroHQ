package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    public void save(User user);

    public void delete(User user);

}
