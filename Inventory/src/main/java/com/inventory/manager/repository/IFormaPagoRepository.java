package com.inventory.manager.repository;

import com.inventory.manager.model.FormaPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFormaPagoRepository extends JpaRepository<FormaPago, Long> {
}
