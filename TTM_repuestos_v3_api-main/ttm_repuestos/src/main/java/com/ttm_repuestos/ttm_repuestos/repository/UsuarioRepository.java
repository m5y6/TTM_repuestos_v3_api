package com.ttm_repuestos.ttm_repuestos.repository;


import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
