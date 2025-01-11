package com.inventory.manager.CommonCrud;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor

public abstract class GenericController<T,ID extends Serializable> {
    private final GenericServiceImp<T, ID> service;


    @GetMapping
    public List<T> getAll() throws Exception {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable ID id) throws Exception {
        return service.findById(id);
    }

    @PostMapping
    public T create(@RequestBody T entity) throws Exception {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) throws Exception {
        // Se asume que la entidad tiene un método `setId`, asegúrate de ajustarlo según tu implementación
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) throws Exception {
        service.delete(id);
    }
}
