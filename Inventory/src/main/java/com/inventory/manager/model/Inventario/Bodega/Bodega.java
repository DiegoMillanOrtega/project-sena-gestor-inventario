package com.inventory.manager.model.Inventario.Bodega;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inventory.manager.model.Tercero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bodId;
    @Column(unique = true)
    private String nombre;
    @Column(length = 500)
    private String descripcion;
    private String ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tercero_responsable")
    @JsonIgnoreProperties("responsable")
    private Tercero responsable;
    private Double capacidadMaxima;
    private String estado;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    @CreationTimestamp
    private LocalDateTime fechaActualizacion;
    private String telefono;
    private String emailContacto;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipoBodega")
    @JsonIgnoreProperties("bodega")
    private TipoBodega tipoBodega;

}
