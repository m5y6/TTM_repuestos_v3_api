package com.ttm_repuestos.ttm_repuestos.dto;

import com.ttm_repuestos.ttm_repuestos.model.EstadoVenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDto {
    private Long id;
    private Long usuarioId;
    private String usuarioEmail;
    private LocalDateTime fechaCreacion;
    private EstadoVenta estado;
    private Double total;
    private String vendedorEmail;
    private List<VentaItemDto> items;
}
