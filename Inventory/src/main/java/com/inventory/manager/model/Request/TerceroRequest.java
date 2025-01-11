package com.inventory.manager.model.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TerceroRequest {
    @JsonProperty("id")
    private Long id;
    private Long nit;
    private String tipoDocumento;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date fechaNacimiento;
    private String nombreCompleto;
    private String genero;
    private String estadoCivil;
    private String departamento;
    private String codigoPostal;
    private String tipoPersona;
    private String razonSocial;
    @Column(length = 300)
    private String observaciones;
    private String direccion;
    private List<TerceroDetalleRequest> detalles;
}
