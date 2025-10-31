package com.portafolio.minijira.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class Prueba {

    @GetMapping("/saludo")
    public String saludo(){
        System.out.println(" HOLA!! /api/saludo - " + LocalDateTime.now());
        return "HOLA!! Api funcionando";
    }
}
