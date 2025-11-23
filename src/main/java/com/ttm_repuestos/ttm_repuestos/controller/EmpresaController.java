package com.ttm_repuestos.ttm_repuestos.controller;


import com.ttm_repuestos.ttm_repuestos.model.Empresa;
import com.ttm_repuestos.ttm_repuestos.service.EmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Administracion de empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @GetMapping
    @Operation(summary = "Ver todas las empresas")
    public List<Empresa> findAllEmpresas() {
        return empresaService.findAllEmpresas();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por id")
    public Empresa findByIdEmpresa(@PathVariable Long id) {
        return empresaService.findByIdEmpresa(id);
    }
    
    @PostMapping
    @Operation(summary = "agregar una nueva empresa")
    public Empresa saveEmpresa(@RequestBody Empresa empresa) {
        return empresaService.saveEmpresa(empresa);
    }
    @PutMapping("/{id}")
    @Operation(summary = "actualizar empresa")
    public Empresa updateEmpresa(@PathVariable Long id, @RequestBody Empresa empresaDetails) {
        Empresa empresa = empresaService.findByIdEmpresa(id);
        if (empresa != null) {
            empresa.setNombreEmpresa(empresaDetails.getNombreEmpresa());
            empresa.setCorreo(empresaDetails.getCorreo());
            empresa.setNumeroTelefonicoEmpresa(empresaDetails.getNumeroTelefonicoEmpresa());
            return empresaService.updateEmpresa(empresa);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar empresa")
    public void deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
    }
}