package com.BackBasiFit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.service.MembresiasService;

@RestController
@RequestMapping("/api/membresias")
public class MembresiasController {

    private final MembresiasService membresiasService;

    public MembresiasController(MembresiasService membresiasService) {
        this.membresiasService = membresiasService;
    }

    // GET todas las membresias, por id, activas
    @GetMapping
    public Object getAll(@RequestParam(required = false) String clienteId, @RequestParam(required = false) Boolean activa) {
        if (clienteId != null && Boolean.TRUE.equals(activa)) {
            return membresiasService.findActivaByCliente(clienteId);
        }
        if (clienteId != null) {
            return membresiasService.findByCliente(clienteId);
        }
        return membresiasService.findAll();
    }

    // GET por id
    @GetMapping("/{id}")
    public Membresias getById(@PathVariable String id) { return membresiasService.findById(id); }

    // DELTE eliminar menmbresia 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        membresiasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
