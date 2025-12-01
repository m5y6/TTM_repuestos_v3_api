package com.ttm_repuestos.ttm_repuestos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private Long id;
    private String nombre;
    private String email;
    private Integer rol;
}
