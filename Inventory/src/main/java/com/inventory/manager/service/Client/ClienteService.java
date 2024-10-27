package com.inventory.manager.service.Client;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.Client;
import com.inventory.manager.model.ClientDTO;
import com.inventory.manager.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<ClientDTO> findAllClient() {
        List<Client> clients = this.clienteRepository.findAll();
        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO convertToDTO(Client client) {
        return new ClientDTO(
                client.getId(),         // id
                client.getName(),       // name
                client.getLastName(),   // lastName
                client.getGender(),     // gender (revisa si este campo está bien en el DTO)
                client.getAddress(),    // address
                client.getEmail(),      // email
                client.getPhoneNumber() // phoneNumber
        );
    }

    @Override
    public Client saveClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("El registro del cliente no puede ser null");
        }
        try {
            return this.clienteRepository.save(client);
        } catch (Exception e) {
            throw new DataBaseException("Error al guardar el cliente", e);
        }
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
