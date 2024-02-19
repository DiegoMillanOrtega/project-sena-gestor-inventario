package com.inventory.manager.service;

import com.inventory.manager.model.Users;

import java.util.List;

public interface IUsersService {
    public List<Users> usersList();
    public void addUser(Users user);

    boolean authenticateUser(String username, String password);
}
