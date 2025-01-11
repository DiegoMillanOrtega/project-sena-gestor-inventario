package com.inventory.manager.repository.TablasSistema;

import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
}
