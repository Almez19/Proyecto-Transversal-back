package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BackBasiFit.entity.CatalogoClases;
import com.BackBasiFit.repository.CatalogoClasesRepository;

@RestController
@RequestMapping("/api/catalogo-clases")
public class CatalogoClasesController {

    private final CatalogoClasesRepository catalogoClasesRepository;

    public CatalogoClasesController(CatalogoClasesRepository catalogoClasesRepository) {
        this.catalogoClasesRepository = catalogoClasesRepository;
    }

    // Catálogo público (solo activos).
    @GetMapping
    public List<CatalogoClases> activos() {
        return catalogoClasesRepository.findByEstadoTrueOrderByNombreAsc();
    }

    //  Listado completo (interno).
    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public List<CatalogoClases> todos() {
        return catalogoClasesRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public CatalogoClases getById(@PathVariable String id) {
        return catalogoClasesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe este tipo de clase en el catálogo"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    public ResponseEntity<CatalogoClases> create(@RequestBody CatalogoClases tipo) {
        CatalogoClases guardado = catalogoClasesRepository.save(tipo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(guardado.getId())
                .toUri();
        return ResponseEntity.created(location).body(guardado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    public CatalogoClases update(@PathVariable String id, @RequestBody CatalogoClases tipoActualizado) {
        CatalogoClases existente = catalogoClasesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe este tipo de clase en el catálogo"));

        existente.setNombre(tipoActualizado.getNombre());
        existente.setDescripcion(tipoActualizado.getDescripcion());
        existente.setNivelRecomendado(tipoActualizado.getNivelRecomendado());
        existente.setUrlImagen(tipoActualizado.getUrlImagen());
        existente.setEstado(tipoActualizado.getEstado());

        return catalogoClasesRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        catalogoClasesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
