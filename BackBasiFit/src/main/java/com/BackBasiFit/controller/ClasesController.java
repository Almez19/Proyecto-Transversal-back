package com.BackBasiFit.controller;

import java.net.URI;
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
import com.BackBasiFit.entity.Reservas;
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

    // GET clases
    @GetMapping("/{id}")
    public Clases getById(@PathVariable String id) { 
        
        return clasesService.findById(id); 
    }

    // POST Reservar clase
    @PostMapping("/{claseId}/reservas")
    public ResponseEntity<Reservas> reservarClase(@PathVariable String claseId, @RequestBody java.util.Map<String, String> body) {
        String clienteId = body.get("clienteId");
        Reservas saved = reservasService.reservar(clienteId, claseId);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/reservas/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).body(saved);
    }

    // DELETE Borrar clase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clasesService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}
