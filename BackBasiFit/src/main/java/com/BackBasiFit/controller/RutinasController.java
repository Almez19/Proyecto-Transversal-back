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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.service.RutinasService;

@RestController
@RequestMapping("/api/rutinas")
public class RutinasController {

    private final RutinasService rutinasService;

    public RutinasController(RutinasService rutinasService) {
        this.rutinasService = rutinasService;
    }

    // GET rutinas 
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    public List<Rutinas> getAll(@RequestParam(required = false) String clienteId) {
        if (clienteId != null) {
            return rutinasService.findByClienteOrdenadas(clienteId);
        }
        return rutinasService.findAll();
    }

    // GET rutina por id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esRutinaDeMiCliente(#p0))")
    public Rutinas getById(@PathVariable String id) {
        return rutinasService.findById(id);
    }

    //GET 5 rutinas
    @GetMapping("/recientes")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public List<Rutinas> recientes() {
        return rutinasService.findTop5Recientes();
    }

    // POST crear rutina
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0.clienteId))")
    public ResponseEntity<Rutinas> create(@RequestBody Rutinas rutina) {
        Rutinas rutinaGuardada = rutinasService.save(rutina);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rutinaGuardada.getId())
                .toUri();

        return ResponseEntity.created(location).body(rutinaGuardada);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR') or (hasRole('CLIENTE') and @securityUtil.esRutinaDeMiCliente(#p0))")
    public Rutinas update(@PathVariable String id, @RequestBody Rutinas rutinaActualizada) {
        Rutinas rutinaExistente = rutinasService.findById(id);
        rutinaExistente.setNombre(rutinaActualizada.getNombre());
        rutinaExistente.setObjetivo(rutinaActualizada.getObjetivo());
        rutinaExistente.setNivel(rutinaActualizada.getNivel());
        rutinaExistente.setDiasPorSemana(rutinaActualizada.getDiasPorSemana());
        rutinaExistente.setNotas(rutinaActualizada.getNotas());
        rutinaExistente.setClienteId(rutinaActualizada.getClienteId());
        rutinaExistente.setEntrenadorId(rutinaActualizada.getEntrenadorId());

        return rutinasService.save(rutinaExistente);
    }

    // DELETE eliminar rutina
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO','ENTRENADOR')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rutinasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
