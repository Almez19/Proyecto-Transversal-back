package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
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
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.service.ReservasService;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    private final ReservasService reservasService;

    public ReservasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    // GET todas las reservas, de un cliente concreto, reservas activas
    @GetMapping
    public List<Reservas> getAll(@RequestParam(required = false) String clienteId,
                                 @RequestParam(required = false) String claseId,
                                 @RequestParam(required = false, defaultValue = "false") boolean soloActivas) {
        if (clienteId != null) {
            return reservasService.findByCliente(clienteId);
        }
        if (claseId != null && soloActivas) {
            return reservasService.findActivasByClase(claseId);
        }
        return reservasService.findAll();
    }

    // GET por id
    @GetMapping("/{id}")
    public Reservas getById(@PathVariable String id) { return reservasService.findById(id); }

    // POST crear reservas
    @PostMapping
    public ResponseEntity<Reservas> create(@RequestBody Reservas r) {
        Reservas saved = reservasService.save(r);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELTE eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reservasService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST crear reserva por id de cliente y la clase
    @PostMapping("/reservar")
    public ResponseEntity<Reservas> reservar(@RequestBody Map<String, String> body) {
        String clienteId = body.get("clienteId");
        String claseId = body.get("claseId");
        if (clienteId == null || claseId == null) {
            throw new IllegalArgumentException("clienteId y claseId son obligatorios");
        }
        Reservas saved = reservasService.reservar(clienteId, claseId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/reservas/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // PUT cancelar reserva
    @PutMapping("/{id}/cancelar")
    public Reservas cancelar(@PathVariable String id) {
        return reservasService.cancelar(id);
    }
}
