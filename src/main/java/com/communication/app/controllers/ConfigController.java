package com.communication.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {
    @Value("${spring.data.rest.cors.allowed-origins}")
    private String allowedOrigins;
    @GetMapping("/")
    public String getIndexPage()
    {
        System.out.println(allowedOrigins);
        return "index";
    }
}
