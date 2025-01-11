package com.inventory.manager.service.Client;

import com.inventory.manager.model.Request.TerceroRequest;
import com.inventory.manager.model.Tercero;
import com.inventory.manager.model.ClientDTO;

import java.util.List;

public interface IClienteService {
    public List<ClientDTO> findAllClient();
    public Tercero saveClient(Tercero tercero);
    public Tercero findClientById(Long id);
    public void deleteClientById(Long id);
    public ClientDTO convertToDTO(Tercero tercero);
}
