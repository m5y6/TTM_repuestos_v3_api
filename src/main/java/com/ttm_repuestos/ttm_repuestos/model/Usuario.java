package com.ttm_repuestos.ttm_repuestos.model;

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
@Table(name = "usuarios") // Es una buena práctica nombrar las tablas en plural
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Contraseña hasheada

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = true) // Opcional
    private String empresa;

    @Column(name = "recibe_newsletter", nullable = false)
    private boolean recibeNewsletter;

}
