package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BackBasiFit.entity.Ejercicios;
import com.BackBasiFit.service.EjerciciosService;

@RestController
@RequestMapping("/api/ejercicios")
public class EjerciciosController {

    private final EjerciciosService ejerciciosService;

    public EjerciciosController(EjerciciosService ejerciciosService) {
        this.ejerciciosService = ejerciciosService;
    }

    // GET ejercicios por rutina
    @GetMapping
    public List<Ejercicios> getAll(@RequestParam(required = false) String rutinaId) {
        if (rutinaId != null) return ejerciciosService.findByRutina(rutinaId);
        return ejerciciosService.findAll();
    }

    // GET ejercicio por id
    @GetMapping("/{id}")
    public Ejercicios getById(@PathVariable String id) { return ejerciciosService.findById(id); }

    // POST crear ejercicio
    @PostMapping
    public ResponseEntity<Ejercicios> create(@RequestBody Ejercicios e) {
        Ejercicios saved = ejerciciosService.save(e);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELETE borrar ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ejerciciosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
