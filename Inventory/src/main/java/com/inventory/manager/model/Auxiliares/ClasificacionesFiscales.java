package com.inventory.manager.model.Auxiliares;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ClasificacionesFiscales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clasificacionFiscalId;
    private String codigo;
    private String descrip;
    private boolean activo;
}
