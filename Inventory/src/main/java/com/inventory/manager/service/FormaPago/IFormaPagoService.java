package com.inventory.manager.service.FormaPago;

import com.inventory.manager.model.FormaPago;

import java.util.List;

public interface IFormaPagoService {
    public List<FormaPago> getAllFormasDePago();
    public FormaPago addFormaPago(FormaPago formaPago);
    public FormaPago findFormaPagoById(Long formaPagoID);
}
