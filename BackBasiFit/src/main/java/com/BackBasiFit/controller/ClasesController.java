package com.BackBasiFit.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @GetMapping
    public List<Clases> getAll(@RequestParam(required = false) String salaId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, @RequestParam(required = false) String usuarioClaseId) {
        // GET por fecha
        if (salaId != null && fecha != null) {

            return clasesService.findBySalaAndFecha(salaId, fecha);
        }
        // GET por id de sala
        if (salaId != null) {

            return clasesService.findBySala(salaId);
        }
        // GET por id entrenador
        if (usuarioClaseId != null) {

            return clasesService.findByUsuarioClase(usuarioClaseId);
        }

        return clasesService.findAll();
    }

    // GET clases por id
    @GetMapping("/{id}")
    public Clases getById(@PathVariable String id) {

        return clasesService.findById(id);
    }

    // POST Crear clase
    @PostMapping
    public ResponseEntity<Clases> create(@RequestBody Clases clase) {
        Clases claseGuardada = clasesService.save(clase);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(claseGuardada.getId()).toUri();

        return ResponseEntity.created(location).body(claseGuardada);
    }

    // POST Reservar clase
    @PostMapping("/{claseId}/reservas")
    public ResponseEntity<Reservas> reservarClase(@PathVariable String claseId,@RequestBody Map<String, String> body) {
        String clienteId = body.get("clienteId");
        if (clienteId == null) {
            throw new IllegalArgumentException("clienteId es obligatorio");
        }

        Reservas reservaGuardada = reservasService.reservar(clienteId, claseId);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/reservas/{id}").buildAndExpand(reservaGuardada.getId()).toUri();

        return ResponseEntity.created(location).body(reservaGuardada);
    }

    // PUT Actualizar clase
    @PutMapping("/{id}")
    public Clases update(@PathVariable String id, @RequestBody Clases claseActualizada) {
        Clases claseExistente = clasesService.findById(id);
        claseExistente.setDeporte(claseActualizada.getDeporte());
        claseExistente.setHoraInicio(claseActualizada.getHoraInicio());
        claseExistente.setHoraFinal(claseActualizada.getHoraFinal());
        claseExistente.setFecha(claseActualizada.getFecha());
        claseExistente.setSalaId(claseActualizada.getSalaId());
        claseExistente.setUsuarioClaseId(claseActualizada.getUsuarioClaseId());

        return clasesService.save(claseExistente);
    }

    // DELETE Borrar clase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clasesService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
