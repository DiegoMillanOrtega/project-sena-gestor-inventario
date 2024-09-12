package com.inventory.manager.service.FormaPago;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.FormaPago;
import com.inventory.manager.repository.IFormaPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagoService implements IFormaPagoService{

    @Autowired
    private IFormaPagoRepository repository;
    @Override
    public List<FormaPago> getAllFormasDePago() {
        List<FormaPago> formasDePago = repository.findAll();
        //Si no encuentra formas de pagos tenga una excepcion:
        if (formasDePago.isEmpty()) {
            throw new RuntimeException("No se encontró formas de pago");
        }
        return formasDePago;
    }

    @Override
    public FormaPago addFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new IllegalArgumentException("La entidad FormaPago no puede ser nula.");
        }

        try {
            return repository.save(formaPago);
        } catch (Exception e) {
            throw new DataBaseException("Error al guarda la forma de pago", e);
        }
    }

    @Override
    public FormaPago findFormaPagoById(Long formaPagoID) {
        if (formaPagoID == null || formaPagoID <= 0) {
            throw new IllegalArgumentException("El ID de la forma de pago debe ser un valor positivo.");
        }
        return repository.findById(formaPagoID)
                .orElseThrow(() -> new EntityNotFoundException("Forma de pago no encontrada con el ID: " + formaPagoID));
    }
}
