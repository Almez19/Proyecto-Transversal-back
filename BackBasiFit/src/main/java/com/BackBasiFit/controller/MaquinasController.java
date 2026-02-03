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

import com.BackBasiFit.entity.Maquinas;
import com.BackBasiFit.service.MaquinasService;

@RestController
@RequestMapping("/api/maquinas")
public class MaquinasController {

    private final MaquinasService maquinasService;

    public MaquinasController(MaquinasService maquinasService) {
        this.maquinasService = maquinasService;
    }
    // GET maquinas por gimnasio
    @GetMapping
    public List<Maquinas> getAll(@RequestParam(required = false) String gimnasioId) {
        if (gimnasioId != null) return maquinasService.findByGimnasio(gimnasioId);
        return maquinasService.findAll();
    }

    // GET maquinas por id
    @GetMapping("/{id}")
    public Maquinas getById(@PathVariable String id) { return maquinasService.findById(id); }

    // POST añadir maquina
    @PostMapping
    public ResponseEntity<Maquinas> create(@RequestBody Maquinas m) {
        Maquinas saved = maquinasService.save(m);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELETE eliminar maquina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        maquinasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
