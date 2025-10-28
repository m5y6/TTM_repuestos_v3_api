package com.ttm_repuestos.ttm_repuestos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="descuentos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class DescuentoCodigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String codigo;

    @Column(nullable = false)
    private int porcentaje;

    @Column(nullable = false)
    private int cantidadUtilizable;

    @Column(length = 1000)
    private String descripcion;

    @Column(name="categoriasPermitidas", length = 2000)
    private String permisosJson;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id",nullable = false)
    private Empresa empresa;
}