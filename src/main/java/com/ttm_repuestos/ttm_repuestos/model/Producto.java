package com.ttm_repuestos.ttm_repuestos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String description;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private String categoria;
    
    @Column(name = "imagen_url")
    @JsonProperty("imagen_url") // <-- ESTA ES LA CORRECCIÓN
    private String imagenUrl;

    @Column(nullable = false)
    private Integer stock;

}
