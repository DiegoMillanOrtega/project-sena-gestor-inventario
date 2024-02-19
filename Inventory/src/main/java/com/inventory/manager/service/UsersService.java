package com.inventory.manager.service;

import com.inventory.manager.model.Users;
import com.inventory.manager.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private IUsersRepository usersRepository;

    @Override
    public List<Users> usersList() {
        return this.usersRepository.findAll();
    }

    @Override
    public void addUser(Users user) {
        this.usersRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        List<Users> usersList = this.usersList();
        for (Users user : usersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Autenticación exitosa
            }
        }
        return false; // Credenciales incorrectas
    }
}
