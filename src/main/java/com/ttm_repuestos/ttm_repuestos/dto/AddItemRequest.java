package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.Data;

@Data
public class AddItemRequest {
    private Long productoId;
    private int cantidad;
}
