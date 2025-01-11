package com.inventory.manager.service.Inventario.Bodega;

import com.inventory.manager.model.Inventario.Bodega.Bodega;
import com.inventory.manager.model.Inventario.Bodega.TipoBodega;
import com.inventory.manager.repository.Bodega.IBodegaRepository;
import com.inventory.manager.repository.Bodega.ITipoBodegaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TipoBodegaService {

    @Autowired
    private ITipoBodegaRepository tipoBodegaRepository;
    private static final Logger logger = LoggerFactory.getLogger(Bodega.class);
    public TipoBodega guardarTipoBodega(TipoBodega tipoBodega) {
        if (tipoBodega == null) {
            throw new IllegalArgumentException("Error, bodega llegó null");
        }

        try {
            return tipoBodegaRepository.save(tipoBodega);
        } catch (Exception e) {
            logger.info("Error al guardar bodega", e);
            throw new DataAccessException("Error al guardar bodega por: " + e.getMessage()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }

    public void eliminarTipoBodega(Long bodId){
        if (bodId == null) {
            throw new IllegalArgumentException("Error, el id de la bodega no puede ser null");
        }
        try {
            tipoBodegaRepository.deleteById(bodId);
        } catch (Exception e) {
            throw new DataAccessException("Error al eliminar bodega por: "+ e.getMessage()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }

    public List<TipoBodega> obtenerTiposBodegas() {
        try {
            return tipoBodegaRepository.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error al obtener las tipo de bodegas" + e.getMessage()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }
}
