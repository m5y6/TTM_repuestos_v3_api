package com.ttm_repuestos.ttm_repuestos.repository;

import com.ttm_repuestos.ttm_repuestos.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Método para encontrar un carrito por el ID del usuario
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}
