package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.model.User;


public interface UserService {

    public void save(User user);

    public void delete(User user);

    public User findForLogin(String username, String password);
}
