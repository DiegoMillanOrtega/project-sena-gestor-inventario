package com.inventory.manager.service;

import com.inventory.manager.User.User;

import java.util.List;

public interface IUsersService {
    public List<User> usersList();
    public void addUser(User user);

    boolean authenticateUser(String username, String password);
}
