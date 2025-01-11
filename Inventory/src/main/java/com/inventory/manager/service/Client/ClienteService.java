package com.inventory.manager.service.Client;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.Auxiliares.Relaciones;
import com.inventory.manager.model.Auxiliares.TerceroDetalle;
import com.inventory.manager.model.Request.TerceroDetalleRequest;
import com.inventory.manager.model.Request.TerceroRequest;
import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import com.inventory.manager.model.Tercero;
import com.inventory.manager.model.ClientDTO;
import com.inventory.manager.repository.Auxiliares.IRelacionesRepository;
import com.inventory.manager.repository.Auxiliares.ITercerosDetallesRepository;
import com.inventory.manager.repository.IClienteRepository;
import com.inventory.manager.repository.TablasSistema.ITipoDocumentoRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    ITipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    IRelacionesRepository relacionesRepository;

    @Autowired
    ITercerosDetallesRepository tercerosDetallesRepository;

    @Override
    public List<ClientDTO> findAllClient() {
        List<Tercero> terceros = this.clienteRepository.findAll();
        return terceros.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO convertToDTO(Tercero tercero) {
        return new ClientDTO(
                tercero.getTercero_id(),
                tercero.getNumeroDocumento(),
                tercero.getTipoDocumento(),
                tercero.getName(),
                tercero.getLastName(),
                tercero.getEmail(),
                tercero.getPhoneNumber(),
                tercero.getCiudad(),
                tercero.getPais(),
                tercero.getFechaNacimiento(),
                tercero.getNombreCompleto(),
                tercero.getGenero(),
                tercero.getEstadoCivil(),
                tercero.getDepartamento(),
                tercero.getCodigoPostal(),
                tercero.getTipoTercero(),
                tercero.getRazonSocial(),
                tercero.getSubTipoTercero(),
                tercero.getRolOperacionList(),
                tercero.getClasificacionesFiscales(),
                tercero.getObservaciones(),
                tercero.getDireccion()
        );
    }

    @Override
    public Tercero saveClient(Tercero tercero) {
        if (tercero == null) {
            throw new IllegalArgumentException("El registro del cliente no puede ser null");
        }

        return clienteRepository.save(tercero);
    }

    @Override
    public Tercero findClientById(Long id) {
        return this.clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteClientById(Long id) {
        this.clienteRepository.deleteById(id);
    }
}
