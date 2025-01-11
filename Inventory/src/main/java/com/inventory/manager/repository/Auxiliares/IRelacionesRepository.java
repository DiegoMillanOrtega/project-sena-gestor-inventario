package com.inventory.manager.repository.Auxiliares;

import com.inventory.manager.model.Auxiliares.Relaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRelacionesRepository extends JpaRepository<Relaciones, Long> {
    Optional<Relaciones> findByCodigo(String codigo);
}
