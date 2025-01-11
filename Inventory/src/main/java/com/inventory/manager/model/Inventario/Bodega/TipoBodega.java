package com.inventory.manager.model.Inventario.Bodega;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoBodega {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tipoBodId;
    @Column(unique = true)
    private String nombre;
    @Column(length = 500)
    private String descripcion;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    @OneToMany(mappedBy = "tipoBodega", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tipoBodega")
    private List<Bodega> bodega;
}
