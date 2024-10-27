package com.inventory.manager.controller.Client;


import com.inventory.manager.model.Client;
import com.inventory.manager.model.ClientDTO;
import com.inventory.manager.service.Client.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(value = "http://localhost:4200")
public class ClientController {

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/getAllClient")
    public List<ClientDTO> clientList() {
        logger.info("return All-client");
        return this.clienteService.findAllClient();
    }

    @GetMapping("/getClientById/{id}")
    public Client findClientById(@PathVariable Long id) {
        return this.clienteService.findClientById(id);
    }

    @PostMapping("/saveClient")
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        try {
            logger.info("saved client");
            Client savedClient = this.clienteService.saveClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving client: " + e.getMessage(), e);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error-Message", "Error saving client: " + e.getMessage());
            return new ResponseEntity<>(null,headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteClient/{id}")
    public void deleteClientById(@PathVariable Long id) {
        logger.info("Client deleted");
        this.clienteService.deleteClientById(id);
    }

}
