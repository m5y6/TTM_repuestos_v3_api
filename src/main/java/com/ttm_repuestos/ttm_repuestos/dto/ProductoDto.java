package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private String description;
    private Double precio;
    private String categoria;
    private String imagenUrl;
    private Integer stock;
}
