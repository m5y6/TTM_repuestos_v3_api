package com.ttm_repuestos.ttm_repuestos.repository;

import com.ttm_repuestos.ttm_repuestos.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
