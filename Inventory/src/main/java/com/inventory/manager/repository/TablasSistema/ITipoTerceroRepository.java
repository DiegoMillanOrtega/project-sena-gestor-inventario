package com.inventory.manager.repository.TablasSistema;

import com.inventory.manager.model.TablasSistemas.TipoTercero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoTerceroRepository extends JpaRepository<TipoTercero, Long> {
}
