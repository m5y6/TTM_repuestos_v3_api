package com.ttm_repuestos.ttm_repuestos.security;

import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: "
                        + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities("USER")
                .build();
    }
}
