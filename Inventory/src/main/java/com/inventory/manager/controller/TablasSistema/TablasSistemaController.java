package com.inventory.manager.controller.TablasSistema;


import com.inventory.manager.CommonCrud.GenericRepository;
import com.inventory.manager.model.Auxiliares.ClasificacionesFiscales;
import com.inventory.manager.model.TablasSistemas.RolOperacion;
import com.inventory.manager.model.TablasSistemas.SubTipoTercero;
import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import com.inventory.manager.model.TablasSistemas.TipoTercero;
import com.inventory.manager.repository.TablasSistema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tablasSistema")
@CrossOrigin(value = "http://localhost:4200")
public class TablasSistemaController {

    public static final Logger logger = LoggerFactory.getLogger(TablasSistemaController.class);

    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private ITipoTerceroRepository tipoTerceroRepository;

    @Autowired
    private ISubTipoTerceroRepository subTipoTerceroRepository;

    @Autowired
    private IRolOperacion rolOperacion;

    @Autowired
    private IClasificacionesFiscalesRepository clasificacionesFiscalesRepository;


    @GetMapping("/getAllTipoDocumentos")
    public List<TipoDocumento> tipoDocumentoList(){
        return tipoDocumentoRepository.findAll();
    }

    @GetMapping("/getAllTipoTerceros")
    public List<TipoTercero> tipoTerceroList(){
        return tipoTerceroRepository.findAll();
    }

    @GetMapping("/getAllSubTipoTerceros")
    public List<SubTipoTercero> subTipoTerceroList(){
        return subTipoTerceroRepository.findAll();
    }

    @GetMapping("/getAllRolOperacion")
    public List<RolOperacion> rolOperacionList(){
        return rolOperacion.findAll();
    }

    @GetMapping("/getAllClasificacionFiscales")
    public List<ClasificacionesFiscales> clasificacionesFiscalesList() {
        return clasificacionesFiscalesRepository.findAll();
    }
}
