package com.inventory.manager.controller.FormaPago;

import com.inventory.manager.model.FormaPago;
import com.inventory.manager.service.FormaPago.FormaPagoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formaPago")
@CrossOrigin(value = "http://localhost:4200")
public class FormaPagoController {

    @Autowired
    private FormaPagoService service;

    @GetMapping("/getAllFormasDePago")
    public ResponseEntity<List<FormaPago>> getAllFormasDePago() {
        List<FormaPago> formasPago = service.getAllFormasDePago();
        return ResponseEntity.ok(formasPago);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPago> findById(@PathVariable("id") Long formaDePagoID) {
        FormaPago formaPago = service.findFormaPagoById(formaDePagoID);
        return ResponseEntity.ok(formaPago);
    }

    @PostMapping("/addFormaPago")
    public ResponseEntity<FormaPago> addFormaPago(@RequestBody FormaPago formaPago) {
        FormaPago nuevaFormaPago = service.addFormaPago(formaPago);
        return new ResponseEntity<>(formaPago, HttpStatus.CREATED);
    }

    // Manejo global de excepciones (opcional)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
