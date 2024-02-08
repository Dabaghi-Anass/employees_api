package com.communication.app.controllers;

import com.communication.app.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.List;

@Controller
public class ConfigController {
    @GetMapping("/")
    public String getIndexPage()
    {
        return "index";
    }
    @GetMapping("/usersfiles")
    public String getFilesPage()
    {
        return "files";
    }
}
