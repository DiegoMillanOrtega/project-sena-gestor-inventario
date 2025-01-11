package com.inventory.manager.controller.Inventario.Bodega;

import com.inventory.manager.ExcepcionesPersonalizadas.DataBaseException;
import com.inventory.manager.model.Inventario.Bodega.Bodega;
import com.inventory.manager.model.Inventario.Bodega.TipoBodega;
import com.inventory.manager.service.Inventario.Bodega.BodegaService;
import com.inventory.manager.service.Inventario.Bodega.TipoBodegaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bodega")
@CrossOrigin(value = "http://localhost:4200")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @Autowired
    private TipoBodegaService tipoBodegaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BodegaController.class);

    @GetMapping("/getBodegas")
    private ResponseEntity<List<Bodega>> obtenerBodegas() {
        try {
            return new ResponseEntity<>(bodegaService.obtenerBodegas(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Error al obtener bodegas: "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarBodega")
    private ResponseEntity<Bodega> guardarBodega(@RequestBody Bodega bodega) {
        try {
            return new ResponseEntity<>(bodegaService.guardarBodega(bodega), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.info("Error al guardar bodega: "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarBodega/{bodId}")
    private HttpStatus eliminarBodega(@PathVariable Long bodId) {
        try {
            bodegaService.eliminarBodega(bodId);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            LOGGER.info("Error al eliminar bodega "+ bodId+ " por: "+e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getTipoBodegas")
    private ResponseEntity<List<TipoBodega>> obtenerTipoBodegas() {
        try {
            return new ResponseEntity<>(tipoBodegaService.obtenerTiposBodegas(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Error al obtener bodegas: "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarTipoBodega")
    private ResponseEntity<TipoBodega> guardarBodega(@RequestBody TipoBodega tipoBodega) {
        try {
            return new ResponseEntity<>(tipoBodegaService.guardarTipoBodega(tipoBodega), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.info("Error al guardar tipo bodega: "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarTipoBodega/{tipoBodId}")
    private HttpStatus eliminarTipoBodega(@PathVariable Long tipoBodId) {
        try {
            tipoBodegaService.eliminarTipoBodega(tipoBodId);
            return HttpStatus.OK;
        } catch (Exception e) {
            LOGGER.info("Error al eliminar tipo bodega "+ tipoBodId+ " por: "+e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}
