package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.model.Producto;
import com.ttm_repuestos.ttm_repuestos.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto not found"));

        producto.setNombre(productoDetails.getNombre());
        producto.setDescription(productoDetails.getDescription());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setCategoria(productoDetails.getCategoria());
        producto.setImagenUrl(productoDetails.getImagenUrl());
        producto.setStock(productoDetails.getStock());

        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
