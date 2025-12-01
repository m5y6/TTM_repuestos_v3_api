package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.Data;

@Data
public class UpdateUsuarioDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer edad;
    // No incluimos rol ni password para que no se puedan actualizar por esta vía.
}
