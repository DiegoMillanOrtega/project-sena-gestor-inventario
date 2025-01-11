package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.inventory.manager.Custom.CustomDateDeserializer;
import com.inventory.manager.model.Auxiliares.ClasificacionesFiscales;
import com.inventory.manager.model.Auxiliares.TerceroDetalle;
import com.inventory.manager.model.Inventario.Bodega.Bodega;
import com.inventory.manager.model.TablasSistemas.RolOperacion;
import com.inventory.manager.model.TablasSistemas.SubTipoTercero;
import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import com.inventory.manager.model.TablasSistemas.TipoTercero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.mapping.Join;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(
        name = "terceros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"numeroDocumento","tipo_documento_id"}, name = "uk_tercero_numero_tipo"),
        indexes = {
        @Index(name = "idx_numero_documento", columnList = "numero_documento"),
        }
        
)
public class Tercero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tercero_id;

    @Column(nullable = false, length = 20)
    private String numeroDocumento;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    private String email;
    private String phoneNumber;
    private String ciudad;
    private String pais;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "client-pedidos")
    private List<Pedido> pedidos;

    @Transient
    public String getNombreCompleto() {
        return this.name + " " + this.lastName;
    }

    private String genero;
    private String estadoCivil;
    private String departamento;
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "tipo_tercero_id", nullable = false)
    private TipoTercero tipoTercero;

    private String razonSocial;

    @ManyToOne
    @JoinColumn(name = "sub_tipo_tercero_id")
    private SubTipoTercero subTipoTercero;

    @ManyToMany
    @JoinTable(
            name = "terceros_rol_operacion",
            joinColumns = @JoinColumn(name = "tercero_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_operacion_id"),
            indexes = {
                    @Index(name = "idx_tercero_id", columnList = "tercero_id"),
                    @Index(name = "idx_rol_operacion_id", columnList = "rol_operacion_id")
            }
    )
    @JsonProperty("rolOperaciones")
    private List<RolOperacion> rolOperacionList;

    @ManyToMany
    @JoinTable(
            name = "terceros_clasificacion_fiscal",
            joinColumns = @JoinColumn(name = "tercero_id"),
            inverseJoinColumns = @JoinColumn(name = "clasificacion_fiscal_id"),
            indexes = {
                    @Index(name = "idx_tercero_id", columnList = "tercero_id"),
                    @Index(name = "idx_clasificacion_fiscal_id", columnList = "clasificacion_fiscal_id")
            }
    )
    private List<ClasificacionesFiscales> clasificacionesFiscales;

    @Column(length = 300)
    private String observaciones;
    private String direccion;

}
