package com.inventory.manager.CommonCrud;

import java.io.Serializable;
import java.util.List;

public interface GenericService <T,ID extends Serializable> {
    List<T> findAll() throws Exception;
    T findById(ID id) throws Exception;
    T save(T entity) throws Exception;
    T update(ID id, T entity) throws Exception;
    void delete(ID id) throws Exception;
}
