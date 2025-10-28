package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.model.Empresa;
import com.ttm_repuestos.ttm_repuestos.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;
    public List<Empresa> findAll(){
        return empresaRepository.findAll();
    }
    public Empresa findById(long id){
        return empresaRepository.findById(id).get();
    }
    public Empresa save(Empresa empresa){
        return empresaRepository.save(empresa);
    }
    public void delete(Long id){
        empresaRepository.deleteById(id);
    }
}
