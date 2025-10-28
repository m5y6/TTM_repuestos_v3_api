package com.ttm_repuestos.ttm_repuestos.repository;


import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}
