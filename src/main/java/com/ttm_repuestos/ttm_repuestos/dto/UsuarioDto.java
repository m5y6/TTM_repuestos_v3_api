package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer edad;
    private Integer rol;
}
