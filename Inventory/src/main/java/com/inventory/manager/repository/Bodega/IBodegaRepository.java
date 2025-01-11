package com.inventory.manager.repository.Bodega;

import com.inventory.manager.model.Inventario.Bodega.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBodegaRepository extends JpaRepository<Bodega, Long> {
}
