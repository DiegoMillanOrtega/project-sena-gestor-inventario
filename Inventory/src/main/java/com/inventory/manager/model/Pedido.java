package com.inventory.manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
    @Entity
    public class Pedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        private Double price;
        private String address;
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JsonBackReference(value = "client-pedidos")
        @JoinColumn(name = "id_cliente")
        private Tercero client;
    
        @ManyToOne(fetch = FetchType.EAGER)
        @JsonBackReference(value = "pedido-formaPago")
        @JoinColumn(name = "forma_pago_ID")
        private FormaPago paymentType;

        private String obs;

        @ManyToOne(fetch = FetchType.EAGER)
        @JsonBackReference
        @JoinColumn(name = "id_vendedor")
        private Tercero vendedor;

        private Date fecha;
    
        @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
        @JsonManagedReference
        private List<PedidoDetalle> pedidoDetalles;
    
    
    }
