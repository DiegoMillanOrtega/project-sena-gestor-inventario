package com.inventory.manager.model.TablasSistemas;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubTipoTercero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subTipoTerceroId;
    private String codigo;
    private String descrip;

    @ManyToOne
    @JoinColumn(name = "tipoTerceroId", nullable = false)
    private TipoTercero tipoTercero;

    private Boolean activo;
}
