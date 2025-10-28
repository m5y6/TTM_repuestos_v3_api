package com.ttm_repuestos.ttm_repuestos.controller;


import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Administracion de usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    @Operation(summary = "Ver todos los usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por id")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }
    @PostMapping
    @Operation(summary = "agregar un nuevo usuario")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }
    @PutMapping("/{id}")
    @Operation(summary = "actualizar usuario")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellido(usuarioDetails.getApellido());
            usuario.setCorreo(usuarioDetails.getCorreo());
            usuario.setNumero_telefonico(usuarioDetails.getNumero_telefonico());
            usuario.setEdad(usuarioDetails.getEdad());
            usuario.setEmpresa(usuarioDetails.getEmpresa());
            usuario.setContrasena(usuarioDetails.getContrasena());
            usuario.setRol(usuarioDetails.getRol());
            return usuarioService.createUsuario(usuario);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar usuario")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
}