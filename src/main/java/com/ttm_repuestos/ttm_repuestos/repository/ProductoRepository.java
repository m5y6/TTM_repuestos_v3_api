package com.ttm_repuestos.ttm_repuestos.repository;

import com.ttm_repuestos.ttm_repuestos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
