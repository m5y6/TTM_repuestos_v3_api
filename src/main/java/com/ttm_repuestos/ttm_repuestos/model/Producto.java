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

    @Column(name = "descripcion", length = 1000) // Mapea al nombre de columna existente
    private String description; // Corregido para coincidir con el JSON

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private String categoria;
    
    @JsonProperty("imagen_url")
    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(nullable = false)
    private Integer stock;

}
