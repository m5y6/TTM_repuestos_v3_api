package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.dto.UpdateVentaStatusRequest;
import com.ttm_repuestos.ttm_repuestos.dto.VentaDto;
import com.ttm_repuestos.ttm_repuestos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping({"/ventas", "/ventas/"}) // Acepta la ruta con y sin la barra al final
@PreAuthorize("hasAnyRole('VENDEDOR', 'ADMIN')")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDto>> obtenerTodasLasVentas() {
        List<VentaDto> ventas = ventaService.obtenerTodasLasVentas();
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/{ventaId}/estado")
    public ResponseEntity<VentaDto> actualizarEstadoVenta(
            @PathVariable Long ventaId,
            @RequestBody UpdateVentaStatusRequest request,
            Principal principal) {
        
        String vendedorEmail = principal.getName();
        VentaDto ventaActualizada = ventaService.actualizarEstadoVenta(
                ventaId,
                request.getEstado(),
                vendedorEmail
        );
        return ResponseEntity.ok(ventaActualizada);
    }
}
