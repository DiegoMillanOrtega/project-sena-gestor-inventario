package com.inventory.manager.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerceroDetalleRequest {
    private Long terceroDetalleId;
    private Long terceroId;
    private Long relacionId;
    private String valor;
    private String observ;
}
