package com.communication.app.controllers;

import com.communication.app.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    StorageService storageService;
    @GetMapping("/")
    public String index() {
        return "MediaController";
    }
}
