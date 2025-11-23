package com.ttm_repuestos.ttm_repuestos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttm_repuestos.ttm_repuestos.model.Producto;
import com.ttm_repuestos.ttm_repuestos.service.ProductoService;
import com.ttm_repuestos.ttm_repuestos.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Administracion de productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    @Operation(summary = "Ver todos los productos")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por id")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "agregar un nuevo producto")
    public ResponseEntity<Producto> createProducto(@RequestParam("producto") String productoJson, @RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Producto producto = objectMapper.readValue(productoJson, Producto.class);

            String filename = storageService.store(file);
            producto.setImagenUrl("/images/" + filename);

            Producto nuevoProducto = productoService.createProducto(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("Error al procesar la creación del producto: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "actualizar producto")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        try {
            Producto updatedProducto = productoService.updateProducto(id, productoDetails);
            return ResponseEntity.ok(updatedProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar producto")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
