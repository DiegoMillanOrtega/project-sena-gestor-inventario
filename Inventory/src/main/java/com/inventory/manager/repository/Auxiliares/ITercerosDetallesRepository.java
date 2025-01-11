package com.inventory.manager.repository.Auxiliares;

import com.inventory.manager.model.Auxiliares.TerceroDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITercerosDetallesRepository extends JpaRepository<TerceroDetalle, Long> {
    @Query("SELECT td FROM TerceroDetalle td " +
            "JOIN FETCH td.tercero t " +
            "JOIN FETCH td.relacion r " +
            "WHERE t.id = :terceroId")
    List<TerceroDetalle> findRelacionesByTerceroId(@Param("terceroId") Long terceroId);

    @Query("SELECT td FROM TerceroDetalle td " +
            "JOIN FETCH td.relacion r " +
            "WHERE td.valor = :valor")
    List<TerceroDetalle> findRelacionesByValor(@Param("valor") String valor);

}
