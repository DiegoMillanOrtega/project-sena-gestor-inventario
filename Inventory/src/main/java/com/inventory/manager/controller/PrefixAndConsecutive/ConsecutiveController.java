package com.inventory.manager.controller.PrefixAndConsecutive;

import com.inventory.manager.model.Consecutive;
import com.inventory.manager.service.PrefixAndConsecutive.ConsecutiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/consecutive")
@CrossOrigin(value = "http://localhost:4200")
public class ConsecutiveController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConsecutiveController.class);

    @Autowired
    private ConsecutiveService service;

    @GetMapping("getConsecutive/{prefix}")
    public ResponseEntity<Integer> getConsecutive(@PathVariable String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Controller-getConsecutive: prefix = null");
        }
        try {
            int consecutive = service.getConsecutive(prefix);
            return ResponseEntity.ok(consecutive);
        } catch (Exception e) {
            LOGGER.info("Error al obtener el consecutivo: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createdConsecutive(@RequestBody Consecutive consecutive) {
        if (consecutive == null || consecutive.getPrefix() == null) {
            throw new IllegalArgumentException("CONTROLLER: La entidad consecutive ó el Prefix no pueden ser null");
        }
        try {
            service.savedConsecutive(consecutive);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOGGER.info("CONTROLER: BAD_REQUEST: "+ e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
