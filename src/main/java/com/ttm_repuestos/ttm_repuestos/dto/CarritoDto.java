package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDto {
    private Long id;
    private List<CarritoItemDto> items;
    private double total; // Campo calculado para el total del carrito
}
