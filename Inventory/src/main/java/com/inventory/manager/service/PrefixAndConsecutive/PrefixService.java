package com.inventory.manager.service.PrefixAndConsecutive;

import com.inventory.manager.model.Prefix;
import com.inventory.manager.repository.IPrefixRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrefixService {
    @Autowired
    private IPrefixRepository repository;

    public Prefix findById(Long prefixId) {
        if (prefixId == null) {
            throw new IllegalArgumentException("El prefixId no puede ser null");
        }
        return repository.findById(prefixId).orElseThrow(() -> new EntityNotFoundException("No se encontró el prefijo con el id: "+prefixId));
    }

    public void savePrefix(Prefix prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("La clase prefijo no puede ser null");
        }
        repository.save(prefix);
    }
    public List<Prefix> getAllPrefix() {
        return repository.findAll();
    }

    public void deletePrefix(Long prefixId) {
        if (prefixId == null) {
            throw new IllegalArgumentException("El prefixId no puede ser null");
        }
        Prefix prefix = findById(prefixId);
        repository.delete(prefix);
    }
}
