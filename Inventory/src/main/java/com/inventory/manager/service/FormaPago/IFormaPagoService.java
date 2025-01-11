package com.inventory.manager.service.FormaPago;

import com.inventory.manager.model.FormaPago;
import com.inventory.manager.model.FormaPagoDTO;

import java.util.List;

public interface IFormaPagoService {
    public List<FormaPagoDTO> getAllFormasDePago();
    public FormaPago addFormaPago(FormaPago formaPago);
    public FormaPago findFormaPagoById(Long formaPagoID);
    public void deleteFormaPago(Long formaPagoID);
}
