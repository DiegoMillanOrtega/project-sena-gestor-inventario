package com.inventory.manager.repository;

import com.inventory.manager.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IUserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
