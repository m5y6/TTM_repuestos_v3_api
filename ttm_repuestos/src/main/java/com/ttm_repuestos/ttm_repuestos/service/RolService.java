package com.ttm_repuestos.ttm_repuestos.service;


import com.ttm_repuestos.ttm_repuestos.model.Rol;
import com.ttm_repuestos.ttm_repuestos.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;
    public List<Rol> findAll(){
        return rolRepository.findAll();
    }
    public Rol findById(long id){
        return rolRepository.findById(id).get();
    }
    public Rol save(Rol usuario){
        return rolRepository.save(usuario);
    }
    public void delete(Long id){
        rolRepository.deleteById(id);
    }
}