package com.inventory.manager.service.FormaPago;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.FormaPago;
import com.inventory.manager.model.FormaPagoDTO;
import com.inventory.manager.repository.IFormaPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagoService implements IFormaPagoService{

    @Autowired
    private IFormaPagoRepository repository;
    @Override
    public List<FormaPagoDTO> getAllFormasDePago() {
        List<FormaPago> formasDePago = repository.findAll();
        //Si no encuentra formas de pagos tenga una excepcion:
        if (formasDePago.isEmpty()) {
            throw new RuntimeException("No se encontró formas de pago");
        }
        return formasDePago.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public FormaPagoDTO convertToDTO(FormaPago formaPago) {
        return new FormaPagoDTO(
                formaPago.getFormaPagoID(),
                formaPago.getNombre(),
                formaPago.getDescrip()
        );
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

    @Override
    public void deleteFormaPago(Long formaPagoID) {
        if (!repository.existsById(formaPagoID)){
            throw new EntityNotFoundException("Forma de pago con id"+ formaPagoID + " no encontrado");
        }
        repository.deleteById(formaPagoID);
    }
}
