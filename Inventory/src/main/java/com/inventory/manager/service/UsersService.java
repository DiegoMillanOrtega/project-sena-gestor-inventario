package com.inventory.manager.service;

import com.inventory.manager.User.User;
import com.inventory.manager.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private IUserRepository usersRepository;

    @Override
    public List<User> usersList() {
        return this.usersRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        this.usersRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        List<User> usersList = this.usersList();
        for (User user : usersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Autenticación exitosa
            }
        }
        return false; // Credenciales incorrectas
    }
}
