package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.security.JwtService;
import com.ttm_repuestos.ttm_repuestos.service.UsuarioService;
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
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String contrasena = body.get("contrasena");
        if (correo == null || contrasena == null || correo.isBlank() ||
                contrasena.isBlank()) {
            throw new IllegalArgumentException("Correo y contraseña son requeridos");
        }
        // Nota: Esto probablemente fallará si no proporcionas todos los campos no nulos del Usuario.
        usuarioService.register(correo, contrasena);
        return Map.of("message", "Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String contrasena = body.get("contrasena");
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(correo, contrasena));
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(correo);
            return Map.of("token", token);
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}
