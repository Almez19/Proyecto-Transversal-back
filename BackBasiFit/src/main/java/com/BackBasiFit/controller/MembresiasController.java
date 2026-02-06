package com.BackBasiFit.controller;

import java.net.URI;

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

import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.service.MembresiasService;

@RestController
@RequestMapping("/api/membresias")
public class MembresiasController {

    private final MembresiasService membresiasService;

    public MembresiasController(MembresiasService membresiasService) {
        this.membresiasService = membresiasService;
    }

    // GET membresias, por id, activas
    @GetMapping
    public Object getAll(@RequestParam(required = false) String clienteId, @RequestParam(required = false) Boolean activa) {
        // GET membresias por id de cliente
        if (clienteId != null && Boolean.TRUE.equals(activa)) {

            return membresiasService.findActivaByCliente(clienteId);
        }

        return membresiasService.findAll();
    }

    // GET por id
    @GetMapping("/{id}")
    public Membresias getById(@PathVariable String id) {

        return membresiasService.findById(id);
    }

    // POST crear membresia
    @PostMapping
    public ResponseEntity<Membresias> create(@RequestBody Membresias membresia) {
        Membresias membresiaGuardada = membresiasService.save(membresia);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(membresiaGuardada.getId()).toUri();
        
        return ResponseEntity.created(location).body(membresiaGuardada);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Membresias update(@PathVariable String id, @RequestBody Membresias membresiaActualizada) {
        Membresias membresiaExistente = membresiasService.findById(id);
        membresiaExistente.setFechaInicio(membresiaActualizada.getFechaInicio());
        membresiaExistente.setFechaFinal(membresiaActualizada.getFechaFinal());
        membresiaExistente.setEstado(membresiaActualizada.getEstado());
        membresiaExistente.setDuracion(membresiaActualizada.getDuracion());
        membresiaExistente.setCalidad(membresiaActualizada.getCalidad());
        membresiaExistente.setPrecio(membresiaActualizada.getPrecio());
        membresiaExistente.setClienteId(membresiaActualizada.getClienteId());

        return membresiasService.save(membresiaExistente);
    }

    // PUT activar membresia
    @PutMapping("/{id}/activar")
    public Membresias activar(@PathVariable String id) {

        return membresiasService.cambiarEstado(id, true);
    }

    // PUT desactivar membresia
    @PutMapping("/{id}/desactivar")
    public Membresias desactivar(@PathVariable String id) {

        return membresiasService.cambiarEstado(id, false);
    }

    // DELTE eliminar menmbresia 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        membresiasService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
