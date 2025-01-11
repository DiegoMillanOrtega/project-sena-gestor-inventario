package com.inventory.manager.model.TablasSistemas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tipoDocumentoId;
    private String codigo;
    private String descrip;

}
