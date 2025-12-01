package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.dto.RegisterRequestDto;
import com.ttm_repuestos.ttm_repuestos.dto.UpdateUsuarioDto;
import com.ttm_repuestos.ttm_repuestos.dto.UsuarioDto;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
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
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioService.getAllUsuarios().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por id")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> ResponseEntity.ok(convertToDto(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo usuario (desde admin)")
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody RegisterRequestDto requestDto) {
        Usuario nuevoUsuario = usuarioService.createUsuario(requestDto);
        return new ResponseEntity<>(convertToDto(nuevoUsuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable Long id, @RequestBody UpdateUsuarioDto usuarioDetails) {
        try {
            Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
            return ResponseEntity.ok(convertToDto(updatedUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getEdad(),
                usuario.getRol()
        );
    }
}
