package com.inventory.manager.repository;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
}
