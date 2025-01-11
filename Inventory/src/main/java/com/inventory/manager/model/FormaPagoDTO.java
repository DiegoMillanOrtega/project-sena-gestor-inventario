package com.inventory.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagoDTO {
    private Long FormaPagoID;
    private String nombre;
    private String descrip;
}
