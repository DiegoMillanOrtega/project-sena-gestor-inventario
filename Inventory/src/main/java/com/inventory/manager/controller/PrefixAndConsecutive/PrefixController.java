package com.inventory.manager.controller.PrefixAndConsecutive;

import com.inventory.manager.controller.Inventory.InventoryController;
import com.inventory.manager.model.Prefix;
import com.inventory.manager.service.PrefixAndConsecutive.PrefixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefix")
@CrossOrigin(value = "http://localhost:4200")
public class PrefixController {
    private final static Logger LOGGER = LoggerFactory.getLogger(PrefixController.class);

    @Autowired
    private PrefixService prefixService;

    @GetMapping("/getAllPrefixes")
    public List<Prefix> getAllPrefixes() {
        return prefixService.getAllPrefix();
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<Prefix> getPrefixById(@PathVariable Long id) {
        try {
            Prefix prefix = prefixService.findById(id);
            return ResponseEntity.ok(prefix);
        } catch (Exception e) {
            LOGGER.info("NOT_FOUND PREFIX: "+ e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createPrefix(@RequestBody Prefix prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("CONTROLLER: El prefix no puede ser null");
        }
        try {
            prefixService.savePrefix(prefix);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.info("BAD_REQUEST: "+e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/{id}")
    public void deletePrefix(@PathVariable Long id) {
        prefixService.deletePrefix(id);
    }
}
