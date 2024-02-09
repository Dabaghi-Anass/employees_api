package com.communication.app.controllers;

import com.communication.app.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/media")
@CrossOrigin("*")
public class MediaController {
    @Autowired
    StorageService storageService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageRoute(MultipartFile file)
    {
        return ResponseEntity.ok(storageService.uploadFile(file));
    }
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Resource resource =  storageService.loadFileAsResource(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @GetMapping("/files")
    public List<String> readAllUserImages() {
        return storageService.getAllMedias();
    }
}
