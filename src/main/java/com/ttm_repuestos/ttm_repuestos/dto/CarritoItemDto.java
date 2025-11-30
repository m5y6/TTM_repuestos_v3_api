package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItemDto {
    private Long id;
    private int cantidad;
    private ProductoDto producto;
}
