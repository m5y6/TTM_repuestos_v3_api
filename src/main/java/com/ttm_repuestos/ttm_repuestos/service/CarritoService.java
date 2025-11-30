package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.dto.CarritoDto;
import com.ttm_repuestos.ttm_repuestos.dto.CarritoItemDto;
import com.ttm_repuestos.ttm_repuestos.dto.ProductoDto;
import com.ttm_repuestos.ttm_repuestos.model.Carrito;
import com.ttm_repuestos.ttm_repuestos.model.CarritoItem;
import com.ttm_repuestos.ttm_repuestos.model.Producto;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.repository.CarritoItemRepository;
import com.ttm_repuestos.ttm_repuestos.repository.CarritoRepository;
import com.ttm_repuestos.ttm_repuestos.repository.ProductoRepository;
import com.ttm_repuestos.ttm_repuestos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarritoItemRepository carritoItemRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public CarritoDto agregarProductoAlCarrito(Long usuarioId, Long productoId, int cantidad) {
        productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

        Carrito carrito = getOrCreateCarrito(usuarioId);

        Optional<CarritoItem> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setProductoId(productoId);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setCarrito(carrito);
            carrito.getItems().add(nuevoItem);
        }

        Carrito carritoGuardado = carritoRepository.save(carrito);
        return mapCarritoToDto(carritoGuardado);
    }

    public CarritoDto actualizarCantidadProducto(Long usuarioId, Long itemId, int cantidad) {
        if (cantidad <= 0) {
            eliminarProductoDelCarrito(usuarioId, itemId);
            return verCarrito(usuarioId).orElse(null);
        }

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem del carrito no encontrado con id: " + itemId));

        if (!Objects.equals(item.getCarrito().getUsuario().getId(), usuarioId)) {
            throw new SecurityException("Acceso denegado: no puede modificar un ítem de otro usuario.");
        }

        item.setCantidad(cantidad);
        carritoItemRepository.save(item);

        return mapCarritoToDto(item.getCarrito());
    }

    public void eliminarProductoDelCarrito(Long usuarioId, Long itemId) {
        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem del carrito no encontrado con id: " + itemId));

        if (!Objects.equals(item.getCarrito().getUsuario().getId(), usuarioId)) {
            throw new SecurityException("Acceso denegado: no puede eliminar un ítem de otro usuario.");
        }

        // **LA CORRECCIÓN ESTÁ AQUÍ**
        // En lugar de borrar el item directamente, lo quitamos de la lista del carrito.
        // `orphanRemoval=true` se encargará de borrarlo de la base de datos.
        item.getCarrito().getItems().remove(item);
    }

    public Optional<CarritoDto> verCarrito(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).map(this::mapCarritoToDto);
    }

    private Carrito getOrCreateCarrito(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    private CarritoDto mapCarritoToDto(Carrito carrito) {
        List<CarritoItemDto> itemDtos = carrito.getItems().stream()
                .map(this::mapCarritoItemToDto)
                .collect(Collectors.toList());

        double total = itemDtos.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();

        return new CarritoDto(carrito.getId(), itemDtos, total);
    }

    private CarritoItemDto mapCarritoItemToDto(CarritoItem item) {
        Producto producto = productoRepository.findById(item.getProductoId())
                .orElseThrow(() -> new RuntimeException("Inconsistencia de datos: Producto con id " + item.getProductoId() + " no encontrado."));

        ProductoDto productoDto = new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescription(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getImagenUrl(),
                producto.getStock()
        );

        return new CarritoItemDto(item.getId(), item.getCantidad(), productoDto);
    }
}
