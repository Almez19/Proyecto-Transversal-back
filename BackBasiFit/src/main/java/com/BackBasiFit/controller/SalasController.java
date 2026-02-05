package com.BackBasiFit.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.entity.Salas;
import com.BackBasiFit.service.ClasesService;
import com.BackBasiFit.service.SalasService;

@RestController
@RequestMapping("/api/salas")
public class SalasController {

    private final SalasService salasService;
    private final ClasesService clasesService;

    public SalasController(SalasService salasService, ClasesService clasesService) {
        this.salasService = salasService;
        this.clasesService = clasesService;
    }

    // GET salas 
    @GetMapping
    public List<Salas> getAll(@RequestParam(required = false) String gimnasioId) {
        // GET salas por id gimnasio
        if (gimnasioId != null) {

            return salasService.findByGimnasio(gimnasioId);
        }

        return salasService.findAll();
    }

    // GET sala por id
    @GetMapping("/{id}")
    public Salas getById(@PathVariable String id) {

        return salasService.findById(id);
    }

    // POST nueva sala
    @PostMapping
    public ResponseEntity<Salas> create(@RequestBody Salas sala) {
        Salas salaGuardada = salasService.save(sala);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salaGuardada.getId()).toUri();
        
        return ResponseEntity.created(location).body(salaGuardada);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Salas update(@PathVariable String id, @RequestBody Salas salaActualizada) {
        Salas salaExistente = salasService.findById(id);
        salaExistente.setNumeroSala(salaActualizada.getNumeroSala());
        salaExistente.setGimnasioId(salaActualizada.getGimnasioId());

        return salasService.save(salaExistente);
    }

    // DELETE eliminar sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        salasService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET sala por id de clase
    @GetMapping("/{id}/clases")
    public List<Clases> clasesDeSala(@PathVariable String id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        // GET sala por fecha
        if (fecha != null) {

            return clasesService.findBySalaAndFecha(id, fecha);
        }

        return clasesService.findBySala(id);
    }
}
