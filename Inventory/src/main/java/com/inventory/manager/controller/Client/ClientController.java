package com.inventory.manager.controller.Client;



import com.inventory.manager.model.Tercero;
import com.inventory.manager.model.ClientDTO;
import com.inventory.manager.service.Client.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tercero")
@CrossOrigin(value = "http://localhost:4200")
public class ClientController {

    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/getAllTercero")
    public List<ClientDTO> clientList() {
        logger.info("return All-client");
        return this.clienteService.findAllClient();
    }

    @GetMapping("/getTerceroById/{id}")
    public Tercero findClientById(@PathVariable Long id) {
        return this.clienteService.findClientById(id);
    }

    @PostMapping("/saveTercero")
    public ResponseEntity<Tercero> saveClient(@RequestBody Tercero tercero) {
        try {
            Tercero savedTercero = this.clienteService.saveClient(tercero);
            logger.info("saved Tercero");

            return new ResponseEntity<>(savedTercero, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e) {

            logger.error("Error, tercero duplicado con Tipo de documento: {} y Numero de documento: {}", tercero.getTipoDocumento(), tercero.getNumeroDocumento());

            HttpHeaders headers = new HttpHeaders();

            headers.add("Error", "Error, tercero duplicado con Tipo de documento: " + tercero.getTipoDocumento()
                       + " y Numero de documento: " + tercero.getNumeroDocumento());


            return new ResponseEntity<>(null, headers, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error("Error saving Tercero: " + e.getMessage(), e);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error-Message", "Error al guardar el tercero: " + e.getMessage());
            return new ResponseEntity<>(null,headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTercero/{id}")
    public void deleteClientById(@PathVariable Long id) {
        logger.info("Tercero deleted");
        this.clienteService.deleteClientById(id);
    }

}
