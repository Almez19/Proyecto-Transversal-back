package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // GET reservas
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping
    public List<Reservas> getAll(@RequestParam(required = false) String clienteId) {
        if (clienteId != null) {
            return reservasService.findByCliente(clienteId);
        }
        return reservasService.findAll();
    }

    // GET por id
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/{id}")
    public Reservas getById(@PathVariable String id) {
        return reservasService.findById(id);
    }

    // POST crear reservas
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<Reservas> create(@RequestBody Reservas reserva) {
        Reservas reservaGuardada = reservasService.save(reserva);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservaGuardada.getId())
                .toUri();

        return ResponseEntity.created(location).body(reservaGuardada);
    }

    // PUT actualizar datos
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PutMapping("/{id}")
    public Reservas update(@PathVariable String id, @RequestBody Reservas reservaActualizada) {
        Reservas reservaExistente = reservasService.findById(id);
        reservaExistente.setClienteId(reservaActualizada.getClienteId());
        reservaExistente.setClaseId(reservaActualizada.getClaseId());
        reservaExistente.setEstado(reservaActualizada.getEstado());
        reservaExistente.setMotivoCancelacion(reservaActualizada.getMotivoCancelacion());
        reservaExistente.setFechaCancelacion(reservaActualizada.getFechaCancelacion());

        return reservasService.save(reservaExistente);
    }

    // DELTE eliminar reserva
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reservasService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT cancelar reserva
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO') or (hasRole('CLIENTE') and @securityUtil.esReservaDeMiCliente(#p0))")
    @PutMapping("/{id}/cancelar")
    public Reservas cancelar(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        String motivo = body != null ? body.getOrDefault("motivo", "") : "";
        return reservasService.cancelar(id, motivo);
    }
}
