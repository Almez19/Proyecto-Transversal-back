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

    // GET maquinas
    @GetMapping
    public List<Maquinas> getAll(@RequestParam(required = false) String gimnasioId) {
        // GET maquinas por id gimnasio
        if (gimnasioId != null) {

            return maquinasService.findByGimnasio(gimnasioId);
        }

        return maquinasService.findAll();
    }

    // GET maquinas por id
    @GetMapping("/{id}")
    public Maquinas getById(@PathVariable String id) {

        return maquinasService.findById(id);
    }

    // POST añadir maquina
    @PostMapping
    public ResponseEntity<Maquinas> create(@RequestBody Maquinas maquina) {
        Maquinas maquinaGuardada = maquinasService.save(maquina);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(maquinaGuardada.getId()).toUri();
        
        return ResponseEntity.created(location).body(maquinaGuardada);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Maquinas update(@PathVariable String id, @RequestBody Maquinas maquinaActualizada) {
        Maquinas maquinaExistente = maquinasService.findById(id);
        maquinaExistente.setNombre(maquinaActualizada.getNombre());
        maquinaExistente.setGimnasioId(maquinaActualizada.getGimnasioId());
        maquinaExistente.setDescripcion(maquinaActualizada.getDescripcion());
        maquinaExistente.setUrlImagen(maquinaActualizada.getUrlImagen());

        return maquinasService.save(maquinaExistente);
    }

    // DELETE eliminar maquina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        maquinasService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
