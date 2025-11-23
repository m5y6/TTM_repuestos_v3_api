package com.ttm_repuestos.ttm_repuestos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);
    private final Path rootLocation;

    public FileSystemStorageService() {
        // Corregir la ruta para que apunte al directorio de fuentes correcto
        this.rootLocation = Paths.get("TTM_repuestos_v3_api-main/ttm_repuestos/src/main/resources/static/images");
        logger.info("Ruta de almacenamiento de imágenes configurada en: {}", rootLocation.toAbsolutePath());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            logger.info("Directorio de almacenamiento inicializado en: {}", rootLocation.toAbsolutePath());
        } catch (IOException e) {
            logger.error("No se pudo inicializar el almacenamiento de archivos", e);
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                logger.warn("Intento de almacenar un archivo vacío.");
                throw new RuntimeException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            
            logger.info("Intentando guardar el archivo en: {}", destinationFile);

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                logger.error("Error de seguridad: Intento de guardar un archivo fuera del directorio de almacenamiento. Ruta de destino: {}", destinationFile);
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Archivo guardado exitosamente en: {}", destinationFile);
            }
            return file.getOriginalFilename();
        } catch (IOException e) {
            logger.error("Fallo al almacenar el archivo.", e);
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public void deleteAll() {
        // No implementado
    }
}
