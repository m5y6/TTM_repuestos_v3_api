package com.ttm_repuestos.ttm_repuestos.service;

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

    public Usuario createUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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

    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario not found"));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellido(usuarioDetails.getApellido());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setTelefono(usuarioDetails.getTelefono());
        usuario.setEdad(usuarioDetails.getEdad());
        usuario.setEmpresa(usuarioDetails.getEmpresa());
        usuario.setRecibeNewsletter(usuarioDetails.isRecibeNewsletter());

        if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
