package com.inventory.manager.service.PrefixAndConsecutive;

import com.inventory.manager.model.Consecutive;
import com.inventory.manager.model.Prefix;
import com.inventory.manager.repository.IConsecutiveRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsecutiveService {

    @Autowired
    private IConsecutiveRepository repository;

    @Autowired
    private PrefixService prefixService;


    @Transactional
    public void generateNextConsecutive(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Service | generateNextConsecutive: prefix = null");
        }
        int maxConsecutive = repository.findMaxConsecutive(prefix);

        Consecutive consecutive = new Consecutive();
        consecutive.setConsecutive(maxConsecutive + 1);
        repository.save(consecutive);
    }

    @Transactional
    public int getConsecutive(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Service | generateNextConsecutive: prefix = null");
        }
        return repository.findMaxConsecutive(prefix);
    }

    public void savedConsecutive(Consecutive consecutive) {
        if (consecutive == null || consecutive.getPrefix() == null) {
            throw new IllegalArgumentException("El objeto no consecutivo o el prefijo no puede ser null");
        }
        Prefix prefix = prefixService.findById(consecutive.getPrefix().getPrefixId());
        consecutive.setPrefix(prefix);
        repository.save(consecutive);
    }

    public List<Consecutive> getAllConsecutive() {
        return repository.findAll();
    }
}
