package com.inventory.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private Double price;
    private String address;
    private String name;
    private String lastName;
    private String descrip;
}
