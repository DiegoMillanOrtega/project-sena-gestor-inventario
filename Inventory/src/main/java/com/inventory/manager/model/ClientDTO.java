package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inventory.manager.model.Auxiliares.ClasificacionesFiscales;
import com.inventory.manager.model.Auxiliares.TerceroDetalle;
import com.inventory.manager.model.Request.TerceroDetalleRequest;
import com.inventory.manager.model.TablasSistemas.RolOperacion;
import com.inventory.manager.model.TablasSistemas.SubTipoTercero;
import com.inventory.manager.model.TablasSistemas.TipoDocumento;
import com.inventory.manager.model.TablasSistemas.TipoTercero;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {

    private Long tercero_id;
    private String numeroDocumento;
    private TipoDocumento tipoDocumento;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;
    private String ciudad;
    private String pais;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date fechaNacimiento;
    private String nombreCompleto;
    private String genero;
    private String estadoCivil;
    private String departamento;
    private String codigoPostal;
    private TipoTercero tipoTercero;
    private String razonSocial;
    private SubTipoTercero subTipoTercero;
    private List<RolOperacion> rolOperaciones;
    private List<ClasificacionesFiscales> clasificacionesFiscales;
    @Column(length = 300)
    private String observaciones;
    private String direccion;

}
