package com.inventory.manager.model.Auxiliares;


import com.inventory.manager.model.Tercero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "terceros_detalle", indexes = {
        @Index(name = "idx_tercero_relacion", columnList = "tercero_id, relacion_id"),
        @Index(name = "idx_relacion", columnList = "relacion_id"),
        @Index(name = "idx_valor", columnList = "valor")
})
public class TerceroDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long terceroDetalleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tercero_id", nullable = false)
    private Tercero tercero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relacion_id", nullable = false)
    private Relaciones relacion;

    @Column(nullable = false)
    private String valor;

    private String observ;

}
