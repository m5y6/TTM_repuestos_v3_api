package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private Integer edad;
}
