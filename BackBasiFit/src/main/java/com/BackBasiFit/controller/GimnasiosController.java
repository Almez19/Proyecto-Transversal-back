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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public GimnasiosController(GimnasiosService gimnasiosService, SalasService salasService, MaquinasService maquinasService, NoticiaService noticiaService, ClasesService clasesService) {
        this.gimnasiosService = gimnasiosService;
        this.salasService = salasService;
        this.maquinasService = maquinasService;
        this.noticiaService = noticiaService;
        this.clasesService = clasesService;
    }

    // GET todos los gimnasios
    @GetMapping
    public List<Gimnasios> getAll() {

        return gimnasiosService.findAll();
    }

    // GET gimnasio por id
    @GetMapping("/{id}")
    public Gimnasios getById(@PathVariable String id) {

        return gimnasiosService.findById(id);
    }

    // POST crear gimnasio
    @PostMapping
    public ResponseEntity<Gimnasios> create(@RequestBody Gimnasios gimnasio) {
        Gimnasios gimnasioGuardado = gimnasiosService.save(gimnasio);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gimnasioGuardado.getId()).toUri();
        
        return ResponseEntity.created(location).body(gimnasioGuardado);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Gimnasios update(@PathVariable String id, @RequestBody Gimnasios gimnasioActualizado) {
        Gimnasios gimnasioExistente = gimnasiosService.findById(id);
        gimnasioExistente.setUbicacion(gimnasioActualizado.getUbicacion());
        gimnasioExistente.setCiudad(gimnasioActualizado.getCiudad());
        gimnasioExistente.setNombre(gimnasioActualizado.getNombre());
        gimnasioExistente.setEstado(gimnasioActualizado.getEstado());

        return gimnasiosService.save(gimnasioExistente);
    }

    // PUT abrir gimnasio
    @PutMapping("/{id}/activar")
    public Gimnasios activar(@PathVariable String id) {
        Gimnasios gimnasioExistente = gimnasiosService.findById(id);
        gimnasioExistente.setEstado(true);

        return gimnasiosService.save(gimnasioExistente);
    }

    // PUT cerrar gimnasio
    @PutMapping("/{id}/desactivar")
    public Gimnasios desactivar(@PathVariable String id) {
        Gimnasios gimnasioExistente = gimnasiosService.findById(id);
        gimnasioExistente.setEstado(false);

        return gimnasiosService.save(gimnasioExistente);
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

    // @GetMapping("/{id}/clases")
    // public List<Clases> clasesDeGimnasio(@PathVariable String id, @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate fecha) {
    //     // Salas del gimnasio
    //     List<Salas> salas = salasService.findByGimnasio(id);

    //     // por fecha
    //     return salas.stream().flatMap(s -> (fecha != null? clasesService.findBySalaAndFecha(s.getId(), fecha): clasesService.findBySala(s.getId())).stream()).toList();
    // }

    // GET numero especifco de gimnasios
    @GetMapping("/numgimnasios")
    public List<Gimnasios> numgimnasiosGimnasios() {
         return gimnasiosService.findTop5ByOrderByIdDesc();
    }

    // GET 10 gimnasios abiertos
    @GetMapping("/abiertos")
    public List<Gimnasios> abiertosGimnasios() {
         return gimnasiosService.findTop10ByOrderByEstadoDesc();
    }

}
