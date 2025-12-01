package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.dto.AddItemRequest;
import com.ttm_repuestos.ttm_repuestos.dto.CarritoDto;
import com.ttm_repuestos.ttm_repuestos.dto.UpdateItemQuantityRequest;
import com.ttm_repuestos.ttm_repuestos.dto.VentaDto;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.service.CarritoService;
import com.ttm_repuestos.ttm_repuestos.service.UsuarioService;
import com.ttm_repuestos.ttm_repuestos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VentaService ventaService;

    @PostMapping("/items")
    public ResponseEntity<CarritoDto> agregarItemAlCarrito(@RequestBody AddItemRequest request, Principal principal) {
        Usuario usuario = getUsuario(principal);
        CarritoDto carritoActualizado = carritoService.agregarProductoAlCarrito(
                usuario.getId(),
                request.getProductoId(),
                request.getCantidad()
        );
        return ResponseEntity.ok(carritoActualizado);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CarritoDto> actualizarCantidadItem(
            @PathVariable Long itemId,
            @RequestBody UpdateItemQuantityRequest request,
            Principal principal) {
        Usuario usuario = getUsuario(principal);
        CarritoDto carritoActualizado = carritoService.actualizarCantidadProducto(
                usuario.getId(),
                itemId,
                request.getCantidad()
        );
        return ResponseEntity.ok(carritoActualizado);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> eliminarItemDelCarrito(@PathVariable Long itemId, Principal principal) {
        Usuario usuario = getUsuario(principal);
        carritoService.eliminarProductoDelCarrito(usuario.getId(), itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CarritoDto> verCarrito(Principal principal) {
        Usuario usuario = getUsuario(principal);
        return carritoService.verCarrito(usuario.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkout")
    public ResponseEntity<VentaDto> checkout(Principal principal) {
        Usuario usuario = getUsuario(principal);
        VentaDto nuevaVenta = ventaService.crearVentaDesdeCarrito(usuario.getId());
        return ResponseEntity.ok(nuevaVenta);
    }

    private Usuario getUsuario(Principal principal) {
        return usuarioService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
