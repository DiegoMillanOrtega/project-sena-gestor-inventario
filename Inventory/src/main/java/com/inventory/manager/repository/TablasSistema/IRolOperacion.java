package com.inventory.manager.repository.TablasSistema;

import com.inventory.manager.model.TablasSistemas.RolOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolOperacion extends JpaRepository<RolOperacion, Long> {
}
