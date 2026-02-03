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
import com.BackBasiFit.entity.Gimnasios;
import com.BackBasiFit.entity.Maquinas;
import com.BackBasiFit.entity.Noticias;
import com.BackBasiFit.entity.Salas;
import com.BackBasiFit.service.ClasesService;
import com.BackBasiFit.service.GimnasiosService;
import com.BackBasiFit.service.MaquinasService;
import com.BackBasiFit.service.NoticiaService;
import com.BackBasiFit.service.SalasService;

@RestController
@RequestMapping("/api/gimnasios")
public class GimnasiosController {

    private final GimnasiosService gimnasiosService;
    private final SalasService salasService;
    private final MaquinasService maquinasService;
    private final NoticiaService noticiaService;
    private final ClasesService clasesService;

    public GimnasiosController(GimnasiosService gimnasiosService, SalasService salasService, MaquinasService maquinasService,
                              NoticiaService noticiaService, ClasesService clasesService) {
        this.gimnasiosService = gimnasiosService;
        this.salasService = salasService;
        this.maquinasService = maquinasService;
        this.noticiaService = noticiaService;
        this.clasesService = clasesService;
    }

    // GET todos los gimnasios
    @GetMapping
    public List<Gimnasios> getAll() { return gimnasiosService.findAll(); }

    // GET gimnasio por id
    @GetMapping("/{id}")
    public Gimnasios getById(@PathVariable String id) { return gimnasiosService.findById(id); }

    // POS crear gimnasio
    @PostMapping
    public ResponseEntity<Gimnasios> create(@RequestBody Gimnasios g) {
        Gimnasios saved = gimnasiosService.save(g);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELETE borrar gimnasio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        gimnasiosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET salas de un gimnasio
    @GetMapping("/{id}/salas")
    public List<Salas> salas(@PathVariable String id) {
        return salasService.findByGimnasio(id);
    }

    // GET maquinas de un gimnasio
    @GetMapping("/{id}/maquinas")
    public List<Maquinas> maquinas(@PathVariable String id) {
        return maquinasService.findByGimnasio(id);
    }

    // GET noticias de un gimnasio
    @GetMapping("/{id}/noticias")
    public List<Noticias> noticias(@PathVariable String id) {
        return noticiaService.findByGimnasioId(id);
    }

}
