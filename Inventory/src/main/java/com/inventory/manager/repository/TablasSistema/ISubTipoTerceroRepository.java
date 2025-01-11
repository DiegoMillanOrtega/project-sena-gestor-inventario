package com.inventory.manager.repository.TablasSistema;

import com.inventory.manager.model.TablasSistemas.SubTipoTercero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubTipoTerceroRepository extends JpaRepository<SubTipoTercero, Long> {
    Optional<SubTipoTercero> findByCodigo(String codigo);
}
