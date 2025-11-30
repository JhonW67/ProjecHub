package com.ProjectHub.controller;

import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @GetMapping("/banners/{fileName}")
    public ResponseEntity<Resource> getBanner(@PathVariable String fileName) {
        try {
            Path dir = Paths.get("uploads/banners").toAbsolutePath().normalize();
            Path filePath = dir.resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body((Resource) resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

