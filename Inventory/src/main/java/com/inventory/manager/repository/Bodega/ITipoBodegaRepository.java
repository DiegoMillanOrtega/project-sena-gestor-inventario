package com.inventory.manager.repository.Bodega;

import com.inventory.manager.model.Inventario.Bodega.TipoBodega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoBodegaRepository extends JpaRepository<TipoBodega, Long> {
}
