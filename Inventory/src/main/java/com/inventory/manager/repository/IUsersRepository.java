package com.inventory.manager.repository;

import com.inventory.manager.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<Users, Long> {
}
