package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formaPagoID;
    private String nombre;
    private String descrip;

    @OneToMany(mappedBy = "paymentType", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "pedido-formaPago")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "inventory-formaPago")
    private List<Inventory> metodoPago;
}
