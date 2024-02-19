package com.inventory.manager.service.UserSettingService;

import com.inventory.manager.model.UserSetting;
import com.inventory.manager.repository.IUserSettingRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserSettingService implements IUserSettingService{

    @Autowired
    private IUserSettingRepository userSettingRepository;

    public static final Logger logger = LoggerFactory.getLogger(UserSettingService.class);
    @Override
    public UserSetting getUserSetting(String username)  {

        UserSetting defaultUserSetting = new UserSetting();
        defaultUserSetting.setNameSystem("Gestión y Control");

        List<UserSetting> userSettingList = this.userSettingList();

        if (userSettingList != null) {
            for (UserSetting userSetting: userSettingList) {
                if (Objects.equals(userSetting.getUsername(), username)) {
                    return userSetting;
                }
            }
            return defaultUserSetting;
        } else {
            return defaultUserSetting;
        }
    }

    @Override
    public List<UserSetting> userSettingList() {
        return this.userSettingRepository.findAll();
    }

    @Override
    public void saveUserSetting(UserSetting userSetting) {
        List<UserSetting> userSettingList = this.userSettingList();

        if (userSettingList != null) {
            for (UserSetting userSettingIterador: userSettingList) {

                if (Objects.equals(userSettingIterador.getUsername(), userSetting.getUsername())) {
                    if (userSetting.getNameSystem() != null) {
                        userSettingIterador.setNameSystem(userSetting.getNameSystem());
                    }

                    if (userSetting.getLogo() != null) {
                        userSettingIterador.setLogo(userSetting.getLogo());
                    }

                    this.userSettingRepository.save(userSettingIterador);
                    return;
                }
            }
        } else {
            this.userSettingRepository.save(userSetting);
        }
    }

}
