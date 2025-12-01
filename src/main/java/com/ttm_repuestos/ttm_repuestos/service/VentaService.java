package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.dto.UpdateVentaStatusRequest;
import com.ttm_repuestos.ttm_repuestos.dto.VentaDto;
import com.ttm_repuestos.ttm_repuestos.dto.VentaItemDto;
import com.ttm_repuestos.ttm_repuestos.model.*;
import com.ttm_repuestos.ttm_repuestos.repository.CarritoRepository;
import com.ttm_repuestos.ttm_repuestos.repository.ProductoRepository;
import com.ttm_repuestos.ttm_repuestos.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public VentaDto crearVentaDesdeCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontró el carrito para el usuario con id: " + usuarioId));
        if (carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío. No se puede crear una venta.");
        }
        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(carrito.getUsuario());
        nuevaVenta.setEstado(EstadoVenta.PENDIENTE);
        List<VentaItem> ventaItems = new ArrayList<>();
        double totalVenta = 0.0;
        for (CarritoItem carritoItem : carrito.getItems()) {
            Producto producto = productoRepository.findById(carritoItem.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto con id " + carritoItem.getProductoId() + " no encontrado."));
            VentaItem ventaItem = new VentaItem();
            ventaItem.setVenta(nuevaVenta);
            ventaItem.setProductoId(producto.getId());
            ventaItem.setNombreProducto(producto.getNombre());
            ventaItem.setCantidad(carritoItem.getCantidad());
            ventaItem.setPrecioUnitario(producto.getPrecio());
            ventaItems.add(ventaItem);
            totalVenta += producto.getPrecio() * carritoItem.getCantidad();
        }
        nuevaVenta.setItems(ventaItems);
        nuevaVenta.setTotal(totalVenta);
        Venta ventaGuardada = ventaRepository.save(nuevaVenta);
        carrito.getItems().clear();
        carritoRepository.save(carrito);
        return convertToDto(ventaGuardada);
    }

    public List<VentaDto> obtenerTodasLasVentas() {
        return ventaRepository.findAllWithDetails().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public VentaDto aceptarVenta(Long ventaId, String vendedorEmail) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));
        if (venta.getEstado() != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("La venta ya ha sido procesada o está en proceso.");
        }
        venta.setEstado(EstadoVenta.EN_PROCESO);
        venta.setVendedorEmail(vendedorEmail);
        Venta ventaActualizada = ventaRepository.save(venta);
        return convertToDto(ventaActualizada);
    }

    public VentaDto actualizarEstadoVenta(Long ventaId, EstadoVenta nuevoEstado, String vendedorEmail) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + ventaId));
        if (venta.getEstado() == EstadoVenta.PENDIENTE && (nuevoEstado == EstadoVenta.EN_PROCESO || nuevoEstado == EstadoVenta.COMPLETADO)) {
            venta.setVendedorEmail(vendedorEmail);
        }
        venta.setEstado(nuevoEstado);
        Venta ventaActualizada = ventaRepository.save(venta);
        return convertToDto(ventaActualizada);
    }

    private VentaDto convertToDto(Venta venta) {
        List<VentaItemDto> itemDtos = venta.getItems().stream()
                .map(item -> new VentaItemDto(
                        item.getId(),
                        item.getProductoId(),
                        item.getNombreProducto(),
                        item.getCantidad(),
                        item.getPrecioUnitario()
                )).collect(Collectors.toList());
        return new VentaDto(
                venta.getId(),
                venta.getUsuario().getId(),
                venta.getUsuario().getEmail(),
                venta.getFechaCreacion(),
                venta.getEstado(),
                venta.getTotal(),
                venta.getVendedorEmail(),
                itemDtos
        );
    }
}
