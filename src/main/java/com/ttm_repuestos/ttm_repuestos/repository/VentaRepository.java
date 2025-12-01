package com.ttm_repuestos.ttm_repuestos.repository;

import com.ttm_repuestos.ttm_repuestos.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Método para buscar todas las ventas de un usuario específico
    List<Venta> findByUsuarioId(Long usuarioId);

    /**
     * Busca todas las ventas y carga de forma ansiosa (EAGER) las relaciones
     * con los ítems y el usuario para evitar LazyInitializationException.
     * @return Una lista de todas las ventas con sus datos asociados.
     */
    @Query("SELECT DISTINCT v FROM Venta v LEFT JOIN FETCH v.items LEFT JOIN FETCH v.usuario")
    List<Venta> findAllWithDetails();
}
