package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.dto.LoginResponseDto;
import com.ttm_repuestos.ttm_repuestos.dto.RegisterRequestDto;
import com.ttm_repuestos.ttm_repuestos.dto.UsuarioDto;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.security.JwtService;
import com.ttm_repuestos.ttm_repuestos.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService,
                          UsuarioService usuarioService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDto> register(@RequestBody RegisterRequestDto requestDto) {
        Usuario nuevoUsuario = usuarioService.createUsuario(requestDto);

        // Convertir la entidad a DTO para la respuesta
        UsuarioDto responseDto = new UsuarioDto(
                nuevoUsuario.getId(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getTelefono(),
                nuevoUsuario.getEdad(),
                nuevoUsuario.getRol()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(email);

            Usuario usuario = usuarioService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Error inesperado: Usuario no encontrado después de la autenticación."));

            LoginResponseDto response = new LoginResponseDto(
                    token,
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getRol()
            );

            return ResponseEntity.ok(response);
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}
