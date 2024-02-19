package com.inventory.manager.service.Client;

import com.inventory.manager.model.Client;

import java.util.List;

public interface IClienteService {
    public List<Client> findAllClient();
    public void saveClient(Client client);
}
