package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.model.Empresa;
import com.ttm_repuestos.ttm_repuestos.model.Usuario;
import com.ttm_repuestos.ttm_repuestos.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;
    public List<Empresa> findAllEmpresas(){
        return empresaRepository.findAll();
    }
    public Empresa findByIdEmpresa(long id){
        return empresaRepository.findById(id).orElse(null);
    }
    public Empresa saveEmpresa(Empresa empresa){
        return empresaRepository.save(empresa);
    }
    public Empresa updateEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    public void deleteEmpresa(Long id){
        empresaRepository.deleteById(id);
    }
}
