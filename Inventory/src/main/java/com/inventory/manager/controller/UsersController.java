package com.inventory.manager.controller;

import com.inventory.manager.User.User;
import com.inventory.manager.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:4200")
public class UsersController {

    @Autowired
    private UsersService usersService;

    public static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = this.usersService.usersList();
        logger.info("Users obtained");
        users.forEach(user -> logger.info(user.toString()));
        return users;
    }

    @PostMapping("/postUsers")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (usersService.authenticateUser(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Autenticacion exitosa");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}
