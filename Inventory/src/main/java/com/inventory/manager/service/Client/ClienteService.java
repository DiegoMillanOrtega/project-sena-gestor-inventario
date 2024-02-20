package com.inventory.manager.service.Client;

import com.inventory.manager.model.Client;
import com.inventory.manager.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<Client> findAllClient() {
        return this.clienteRepository.findAll();
    }

    @Override
    public void saveClient(Client client) {
        this.clienteRepository.save(client);
    }

    @Override
    public Client findClientById(Long id) {
        return this.clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteClientById(Long id) {
        this.clienteRepository.deleteById(id);
    }
}
