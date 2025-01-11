package com.inventory.manager.CommonCrud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericServiceImp<T, ID extends Serializable> implements GenericService<T, ID> {

    protected GenericRepository<T, ID> baseRepository;

    @Override
    public List<T> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public T findById(ID id) throws Exception {
        try {
            return baseRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T save(T entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T update(ID id, T entity) throws Exception {
        try {
            Optional<T> optional = baseRepository.findById(id);

            T entityUpdate = optional.get();
            entityUpdate = baseRepository.save(entity);
            return entityUpdate;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(ID id) throws Exception  {
        try {
            if (!baseRepository.existsById(id)) {
                throw new Exception("No se encontro la entidad");
            }

            baseRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
