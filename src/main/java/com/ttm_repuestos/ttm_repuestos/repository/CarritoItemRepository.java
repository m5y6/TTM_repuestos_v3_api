package com.ttm_repuestos.ttm_repuestos.repository;

import com.ttm_repuestos.ttm_repuestos.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}
