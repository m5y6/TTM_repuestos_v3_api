package com.ttm_repuestos.ttm_repuestos.controller;

import com.ttm_repuestos.ttm_repuestos.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/uploads")
public class UploadController {

    private final S3Service s3Service;

    public UploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/generate-presigned-url")
    public ResponseEntity<Map<String, String>> generatePresignedUrl(@RequestBody Map<String, String> request) {
        String fileName = request.get("fileName");
        if (fileName == null || fileName.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre del archivo no puede estar vacío."));
        }

        String presignedUrl = s3Service.generatePresignedUrl(fileName);

        return ResponseEntity.ok(Map.of("url", presignedUrl));
    }
}
