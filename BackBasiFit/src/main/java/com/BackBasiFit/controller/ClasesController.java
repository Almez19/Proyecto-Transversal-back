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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.service.ClasesService;
import com.BackBasiFit.service.ReservasService;


@RestController
@RequestMapping("/api/clases")
public class ClasesController {

    private final ClasesService clasesService;
    private final ReservasService reservasService;

    public ClasesController(ClasesService clasesService, ReservasService reservasService) {
        this.clasesService = clasesService;
        this.reservasService = reservasService;
    }

    @GetMapping
    public List<Clases> getAll(){
        return clasesService.findAll();
    }
    

    // GET clases
    @GetMapping("/{id}")
    public Clases getById(@PathVariable String id) { 
        
        return clasesService.findById(id); 
    }

    @PostMapping
    public ResponseEntity<Clases> create(@RequestBody Clases clases){
        Clases saved = clasesService.save(clases);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).body(saved);
    }
    

    // DELETE Borrar clase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clasesService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}
