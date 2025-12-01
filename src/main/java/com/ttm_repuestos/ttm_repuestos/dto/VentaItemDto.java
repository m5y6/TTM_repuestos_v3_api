package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaItemDto {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private Double precioUnitario;
}
