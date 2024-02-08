package com.communication.app.controllers;

import com.communication.app.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*")
public class MediaController {
    @Autowired
    StorageService storageService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageRoute(MultipartFile file)
    {
        return ResponseEntity.ok(storageService.uploadFile(file));
    }
    @GetMapping("/files")
    public List<String> readAllUserImages() {
        return storageService.getAllMedias();
    }
}
