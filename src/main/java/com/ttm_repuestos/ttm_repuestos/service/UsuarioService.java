package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.dto.RegisterRequestDto;
import com.ttm_repuestos.ttm_repuestos.dto.UpdateUsuarioDto;
import com.ttm_repuestos.ttm_repuestos.model.Carrito;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createUsuario(RegisterRequestDto requestDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(requestDto.getNombre());
        usuario.setApellido(requestDto.getApellido());
        usuario.setEmail(requestDto.getEmail());
        usuario.setTelefono(requestDto.getTelefono());
        usuario.setEdad(requestDto.getEdad());
        usuario.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        usuario.setRol(0);

        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuario(usuario);
        usuario.setCarrito(nuevoCarrito);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario updateUsuario(Long id, UpdateUsuarioDto usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellido(usuarioDetails.getApellido());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setTelefono(usuarioDetails.getTelefono());
        usuario.setEdad(usuarioDetails.getEdad());

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
