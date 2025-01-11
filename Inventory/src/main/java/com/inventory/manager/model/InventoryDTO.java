package com.inventory.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private String codigo;
    private String product;
    private String marca;
    private String version;
    private Category category;
    private Long price;
    private Integer stock;
    private Double cantMinStock;
    private Date fechaCreacion;
    private Double peso;
    private Double precioMayorista;
    private Date plazoPago;
    private Date fechaVencimiento;
    private String imagen;
    private String QR;
    private Long bodega;
    private Long precioCosto;
    private Double margenGanancia;
    private Integer iva;
    private FormaPago metodoPago;
    private Tercero proveedor;
    private String estado;
    private String unidadMedida;
    private String descrip;
    private Double precioVenta;
}
