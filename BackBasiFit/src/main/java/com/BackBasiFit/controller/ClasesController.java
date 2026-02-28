package com.BackBasiFit.controller;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.BackBasiFit.entity.CatalogoClases;
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

    // GET catalogo
    @GetMapping("/catalogo")
    public List<CatalogoClases> catalogoPublico() {
        return clasesService.obtenerCatalogoPublico();
    }

    // GET clases
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Clases> getAll(
            @RequestParam(required = false) String salaId,
            @RequestParam(required = false) String gimnasioId,
            @RequestParam(required = false) String entrenadorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        if (salaId != null && fecha != null) {
            return clasesService.findBySalaAndFecha(salaId, fecha);
        }
        if (gimnasioId != null && fecha != null) {
            return clasesService.findByGimnasioAndFecha(gimnasioId, fecha);
        }
        if (fecha != null) {
            return clasesService.findByFecha(fecha);
        }
        if (salaId != null) {
            return clasesService.findBySala(salaId);
        }
        if (gimnasioId != null) {
            return clasesService.findByGimnasio(gimnasioId);
        }
        if (entrenadorId != null) {
            return clasesService.findByEntrenador(entrenadorId);
        }
        return clasesService.findAll();
    }

    // GET clases por id
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Clases getById(@PathVariable String id) {
        return clasesService.findById(id);
    }

    // POST Crear clase
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public ResponseEntity<Clases> create(@RequestBody Clases clase) {
        Clases claseGuardada = clasesService.save(clase);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(claseGuardada.getId())
                .toUri();
        return ResponseEntity.created(location).body(claseGuardada);
    }

    // POST Reservar clase
    @PostMapping("/{claseId}/reservas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Reservas> reservarClase(@PathVariable String claseId, Principal principal) {
        Reservas reservaGuardada = reservasService.reservarPorEmailCliente(principal.getName(), claseId);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/reservas/{id}")
                .buildAndExpand(reservaGuardada.getId())
                .toUri();

        return ResponseEntity.created(location).body(reservaGuardada);
    }

    // PUT Actualizar clase
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public Clases update(@PathVariable String id, @RequestBody Clases claseActualizada) {
        Clases claseExistente = clasesService.findById(id);

        claseExistente.setNombre(claseActualizada.getNombre());
        claseExistente.setDescripcion(claseActualizada.getDescripcion());
        claseExistente.setNivel(claseActualizada.getNivel());
        claseExistente.setFecha(claseActualizada.getFecha());
        claseExistente.setHoraInicio(claseActualizada.getHoraInicio());
        claseExistente.setHoraFinal(claseActualizada.getHoraFinal());
        claseExistente.setCapacidad(claseActualizada.getCapacidad());
        claseExistente.setEstado(claseActualizada.getEstado());
        claseExistente.setSalaId(claseActualizada.getSalaId());
        claseExistente.setEntrenadorId(claseActualizada.getEntrenadorId());
        claseExistente.setGimnasioId(claseActualizada.getGimnasioId());

        claseExistente.recalcularDuracionSiProcede();

        return clasesService.save(claseExistente);
    }

    // DELETE Borrar clase
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clasesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
