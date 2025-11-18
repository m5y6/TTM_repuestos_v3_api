package com.ttm_repuestos.ttm_repuestos.controller;

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
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
        nuevoUsuario.setPassword(null);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(email);
            return Map.of("token", token);
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}
