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

    // GET rutinas 
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    @GetMapping
    public List<Rutinas> getAll(@RequestParam(required = false) String clienteId) {
        // GET rutinas por is cliente
        if (clienteId != null) {

            return rutinasService.findByCliente(clienteId);
        }

        return rutinasService.findAll();
    }

    // GET rutina por id
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esRutinaDeMiCliente(#p0))")
    @GetMapping("/{id}")
    public Rutinas getById(@PathVariable String id) {

        return rutinasService.findById(id);
    }

    //GET 5 rutinas
    @GetMapping("/numerorutinas")
    public List<Rutinas> numerorutinasRutinas() {
         return rutinasService.findTop5ByOrderByIdDesc();
    }

    // POST crear rutina
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    @PostMapping
    public ResponseEntity<Rutinas> create(@RequestBody Rutinas rutina) {
        Rutinas rutinaGuardada = rutinasService.save(rutina);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rutinaGuardada.getId()).toUri();
        
        return ResponseEntity.created(location).body(rutinaGuardada);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Rutinas update(@PathVariable String id, @RequestBody Rutinas rutinaActualizada) {
        Rutinas rutinaExistente = rutinasService.findById(id);
        rutinaExistente.setNombre(rutinaActualizada.getNombre());
        rutinaExistente.setClienteId(rutinaActualizada.getClienteId());

        return rutinasService.save(rutinaExistente);
    }

    // DELETE eliminar rutina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rutinasService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET ejercicios por id de rutina
    @GetMapping("/{id}/ejercicios")
    public List<Ejercicios> ejerciciosDeRutina(@PathVariable String id) {
        
        return ejerciciosService.obtenerEjerciciosPorRutinaId(id);
    }
}
