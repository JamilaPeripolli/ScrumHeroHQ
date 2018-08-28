package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
