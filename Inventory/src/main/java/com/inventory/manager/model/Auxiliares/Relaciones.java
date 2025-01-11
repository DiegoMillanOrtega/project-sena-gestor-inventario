package com.inventory.manager.model.Auxiliares;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_codigo_relaciones", columnList = "codigo", unique = true)
})
public class Relaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long relacionId;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String descripcion;

    private String activo;
}
