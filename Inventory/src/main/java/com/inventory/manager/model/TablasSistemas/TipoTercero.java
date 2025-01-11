package com.inventory.manager.model.TablasSistemas;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoTercero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tipoTerceroId;
    private String codigo;
    private String descrip;
    @Column(nullable = false)
    private boolean activo;

}
