package com.inventory.manager.repository;

import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import com.inventory.manager.model.Tercero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Tercero, Long> {
    @Query("SELECT t FROM TerceroDetalle td " +
            "JOIN td.tercero t " +
            "JOIN td.relacion r " +
            "WHERE r.codigo = :codigo")

    List<Tercero> findTercerosByRelacionCodigo(@Param("codigo") String codigo);

    Optional<Tercero> findByNumeroDocumentoAndTipoDocumento(String numeroDocumento, TipoDocumento tipoDocumentoId);
}
