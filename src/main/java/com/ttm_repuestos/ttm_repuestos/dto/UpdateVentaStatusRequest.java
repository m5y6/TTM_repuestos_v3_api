package com.ttm_repuestos.ttm_repuestos.dto;

import com.ttm_repuestos.ttm_repuestos.model.EstadoVenta;
import lombok.Data;

@Data
public class UpdateVentaStatusRequest {
    private EstadoVenta estado;
}
