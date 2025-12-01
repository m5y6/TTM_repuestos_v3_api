package com.ttm_repuestos.ttm_repuestos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta_items")
@Data
@NoArgsConstructor
public class VentaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario; // Guarda el precio en el momento de la compra
    
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto; // Guarda el nombre del producto para referencia rápida
}
