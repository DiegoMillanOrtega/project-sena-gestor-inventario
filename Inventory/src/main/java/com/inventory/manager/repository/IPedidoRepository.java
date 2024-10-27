package com.inventory.manager.repository;

import com.inventory.manager.model.Inventory;
import com.inventory.manager.model.Pedido;
import com.inventory.manager.model.PedidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT new com.inventory.manager.model.PedidoDTO(p.id, p.price, p.address, c.name, c.lastName, fp.descrip) " +
            "FROM Pedido p " +
            "JOIN p.client c " +
            "JOIN p.paymentType fp")
    List<PedidoDTO> findAllPedidoDetails();
}
