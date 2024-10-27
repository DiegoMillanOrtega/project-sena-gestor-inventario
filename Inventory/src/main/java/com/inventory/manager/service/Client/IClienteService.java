package com.inventory.manager.service.Client;

import com.inventory.manager.model.Client;
import com.inventory.manager.model.ClientDTO;

import java.util.List;

public interface IClienteService {
    public List<ClientDTO> findAllClient();
    public Client saveClient(Client client);
    public Client findClientById(Long id);
    public void deleteClientById(Long id);
    public ClientDTO convertToDTO(Client client);
}
