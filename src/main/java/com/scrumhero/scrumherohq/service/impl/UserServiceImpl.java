package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public User findForLogin(String username, String password) {
        return repository.findByEmailAndPassword(username, password);
    }
}
