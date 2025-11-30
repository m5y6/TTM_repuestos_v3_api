package com.ttm_repuestos.ttm_repuestos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_items")
@Data
@NoArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    // Este campo guardará el ID del producto.
    // Asumo que tienes una entidad Producto con un ID de tipo Long.
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private int cantidad;

    // Es una buena práctica guardar también el precio del producto al momento de agregarlo,
    // para evitar inconsistencias si el precio del producto cambia después.
    // @Column(name = "precio_unitario", nullable = false)
    // private Double precioUnitario;
}
