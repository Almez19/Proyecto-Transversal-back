package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.security.access.prepost.PreAuthorize;

import com.BackBasiFit.entity.Ejercicios;
import com.BackBasiFit.service.EjerciciosService;

@RestController
@RequestMapping("/api/ejercicios")
@PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
public class EjerciciosController {

    private final EjerciciosService ejerciciosService;

    public EjerciciosController(EjerciciosService ejerciciosService) {
        this.ejerciciosService = ejerciciosService;
    }

    // GET ejercicios 
    @GetMapping
    public List<Ejercicios> getAll(@RequestParam(required = false) String rutinaId) {
        // GET ejercicios por id de rutina
        if (rutinaId != null) {

            return ejerciciosService.obtenerEjerciciosPorRutinaId(rutinaId);
        }

        return ejerciciosService.findAll();
    }

    // GET ejercicios por id
    @GetMapping("/{id}")
    public Ejercicios getById(@PathVariable String id) {

        return ejerciciosService.findById(id);
    }

    //GET 5 ejercicios 
    @GetMapping("/numeroejercicios")
    public List<Ejercicios> numeroejerciciosEjercicios() {
        
         return ejerciciosService.findTop5ByOrderByIdDesc();
    }

    // POST crear ejercicio
    @PostMapping
    public ResponseEntity<Ejercicios> create(@RequestBody Ejercicios ejercicio) {
        Ejercicios ejercicioGuardado = ejerciciosService.save(ejercicio);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ejercicioGuardado.getId()).toUri();
        
        return ResponseEntity.created(location).body(ejercicioGuardado);
    }

    // PUT actualizar ejercicio
    @PutMapping("/{id}")
    public Ejercicios update(@PathVariable String id, @RequestBody Ejercicios ejercicioActualizado) {
        Ejercicios ejercicioExistente = ejerciciosService.findById(id);
        ejercicioExistente.setNombre(ejercicioActualizado.getNombre());
        ejercicioExistente.setRutinaId(ejercicioActualizado.getRutinaId());
        ejercicioExistente.setMaquinaId(ejercicioActualizado.getMaquinaId());

        return ejerciciosService.save(ejercicioExistente);
    }

    // DELETE borrar ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ejerciciosService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
