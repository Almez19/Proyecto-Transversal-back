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
import com.BackBasiFit.entity.Noticias;
import com.BackBasiFit.service.NoticiaService;

@RestController
@RequestMapping("/api/noticias")
public class NoticiasController {

    private final NoticiaService noticiaService;

    public NoticiasController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    // GET noticia por is de gimnacio
    @GetMapping
    public List<Noticias> getAll(@RequestParam(required = false) String gimnasioId) {
        if (gimnasioId != null) return noticiaService.findByGimnasioId(gimnasioId);

        return noticiaService.findAll();
    }

    // GET por id 
    @GetMapping("/{id}")
    public Noticias getById(@PathVariable String id) { 
        
        return noticiaService.findById(id); 
    }

    // POST crear noticias
    @PostMapping
    public ResponseEntity<Noticias> create(@RequestBody Noticias noticia) {
        Noticias saved = noticiaService.save(noticia);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELTE eliminar noticias
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        noticiaService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}
