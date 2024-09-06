package com.inventory.manager.controller;

import com.inventory.manager.model.UserSetting;
import com.inventory.manager.service.UserSettingService.UserSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:4200")
public class UserSettingController {

    @Autowired
    private UserSettingService userSettingService;

    public static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("/getUserSetting/{username}")
    public UserSetting getUserSetting(@PathVariable String username) {
        return this.userSettingService.getUserSetting(username);
    }

    @PostMapping("/postUpdateUserSetting")
    public ResponseEntity<UserSetting> saveUserSetting(@RequestParam("nameSystem") String nameSystem,
                                                       @RequestParam("username") String username,
                                                       @RequestParam("logo") MultipartFile logo) {
        try {
            UserSetting userSetting = new UserSetting();
            System.out.println("nameSystem: "+ nameSystem);
            if (!nameSystem.isEmpty()) {
                userSetting.setNameSystem(nameSystem);
            }
            userSetting.setUsername(username);
            if (!logo.isEmpty()) {
                userSetting.setLogo(logo.getBytes());
            }
            this.userSettingService.saveUserSetting(userSetting);
            UserSetting savedUserSetting = this.userSettingService.findByUsername(username);
            return ResponseEntity.ok(savedUserSetting); // Devuelve los datos actualizados del usuario
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}