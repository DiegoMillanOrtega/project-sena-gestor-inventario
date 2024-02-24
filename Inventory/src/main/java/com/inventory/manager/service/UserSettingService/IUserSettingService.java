package com.inventory.manager.service.UserSettingService;

import com.inventory.manager.model.UserSetting;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;

public interface IUserSettingService {

    public UserSetting getUserSetting(String username);
    public List<UserSetting> userSettingList();
    public void saveUserSetting(UserSetting userSetting);
    public UserSetting findByUsername(String username);

}
