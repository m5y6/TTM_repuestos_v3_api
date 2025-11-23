package com.ttm_repuestos.ttm_repuestos.service;

import com.ttm_repuestos.ttm_repuestos.model.DescuentoCodigo;
import com.ttm_repuestos.ttm_repuestos.repository.DescuentoCodigoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DescuentoCodigoService {

    @Autowired
    private DescuentoCodigoRepository descuentoCodigoRepository;

    public List<DescuentoCodigo> findAll() {
        return descuentoCodigoRepository.findAll();
    }

    public DescuentoCodigo findById(long id) {
        return descuentoCodigoRepository.findById(id).orElse(null);
    }

    public DescuentoCodigo save(DescuentoCodigo descuentoCodigo) {
        return descuentoCodigoRepository.save(descuentoCodigo);
    }

    public void delete(Long id) {
        descuentoCodigoRepository.deleteById(id);
    }
}
