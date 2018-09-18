package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.InvalidUserException;
import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getAuthority() == null) {
            user.setAuthority(AuthorityType.USER);
        }

        validateUser(user);

        repository.save(user);

        LOGGER.info("User created: {}", user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Email not found: " + email);
        }

        LOGGER.debug("User found: {}", user.getEmail());

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthority().getValue());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Arrays.asList(authority));
    }

    private void validateUser(User user) {
        if (user == null
                || StringUtils.isEmpty(user.getEmail())
                || StringUtils.isEmpty(user.getName())
                || StringUtils.isEmpty(user.getPassword())
                || user.getAuthority() == null) {

            throw new InvalidUserException("Invalid user, missing mandatory information.");
        }

        User duplicatedUser = repository.findByEmail(user.getEmail());

        if(duplicatedUser != null) {
            throw new InvalidUserException("Invalid user, an user with this email already exists.");
        }
    }
}
