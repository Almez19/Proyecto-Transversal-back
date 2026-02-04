package com.BackBasiFit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {

    // Comprobando que funciona http://localhost:8080/
    @GetMapping("/")
    public String inicio() {
        
        return "Funcionando";
    }
}
