package com.communication.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
