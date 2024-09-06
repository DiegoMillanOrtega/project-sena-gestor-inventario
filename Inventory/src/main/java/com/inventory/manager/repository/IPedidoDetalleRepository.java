package com.inventory.manager.repository;

import com.inventory.manager.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
}
