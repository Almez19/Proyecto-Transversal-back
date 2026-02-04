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
import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.service.EjerciciosService;
import com.BackBasiFit.service.RutinasService;

@RestController
@RequestMapping("/api/rutinas")
public class RutinasController {

    private final RutinasService rutinasService;
    private final EjerciciosService ejerciciosService;

    public RutinasController(RutinasService rutinasService, EjerciciosService ejerciciosService) {
        this.rutinasService = rutinasService;
        this.ejerciciosService = ejerciciosService;
    }

    // GET rutinas de cada cliente
    @GetMapping
    public List<Rutinas> getAll(@RequestParam(required = false) String clienteId) {
        if (clienteId != null) return rutinasService.findByCliente(clienteId);

        return rutinasService.findAll();
    }

    // GET rutina por id
    @GetMapping("/{id}")
    public Rutinas getById(@PathVariable String id) { 
        
        return rutinasService.findById(id); 
    }

    // POST crear rutina
    @PostMapping
    public ResponseEntity<Rutinas> create(@RequestBody Rutinas rutina) {
        Rutinas saved = rutinasService.save(rutina);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).body(saved);
    }

    // DELETE eliminar rutina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rutinasService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET ejercicios por rutina
    @GetMapping("/{id}/ejercicios")
    public List<Ejercicios> ejerciciosDeRutina(@PathVariable String id) {
        
        return ejerciciosService.obtenerEjerciciosPorRutinaId(id);
    }
}
