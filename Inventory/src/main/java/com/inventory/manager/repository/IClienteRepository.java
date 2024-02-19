package com.inventory.manager.repository;

import com.inventory.manager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Client, Long> {
}
