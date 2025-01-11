package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 12)
    private String codigo;
    private String product;
    private String marca;
    private String version;


    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    private Long price;
    private Long precioCosto;
    private Double precioMayorista;
    private Double margenGanancia;
    private Integer iva;

    private Integer stock;
    private Double cantMinStock;
    private Double peso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date plazoPago;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date fechaCreacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date fechaVencimiento;
    private Long bodega;

    private String imagen;
    private String QR;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tercero")
    @JsonBackReference(value = "inventory-tercero")
    private Tercero proveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forma_pago_id")
    @JsonBackReference(value = "inventory-formaPago")
    private FormaPago metodoPago;

    private String estado;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "inventory-pedidoDetalle")
    @JsonIgnore
    private List<PedidoDetalle> pedidoDetalles;
    private String unidadMedida;
    @Column(length = 500)
    private String descrip;
    private Double precioVenta;

}
