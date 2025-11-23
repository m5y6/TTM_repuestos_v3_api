package com.ttm_repuestos.ttm_repuestos.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile file);
    void deleteAll();
}
