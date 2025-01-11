package com.inventory.manager.CommonCrud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository <T,ID extends Serializable> extends JpaRepository<T,ID> {
}
