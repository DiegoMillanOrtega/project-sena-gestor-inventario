package com.inventory.manager.repository;

import com.inventory.manager.model.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserSettingRepository extends JpaRepository<UserSetting, Long> {
    UserSetting findByUsername(String username);
}
