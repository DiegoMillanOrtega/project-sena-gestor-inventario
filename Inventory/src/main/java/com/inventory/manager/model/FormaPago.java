package com.inventory.manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formaPagoID;
    private String nombre;
    private String Descrip;
}
