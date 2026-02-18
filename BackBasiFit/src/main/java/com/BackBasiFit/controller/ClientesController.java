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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.security.access.prepost.PreAuthorize;

import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.service.ClientesService;
import com.BackBasiFit.service.MembresiasService;
import com.BackBasiFit.service.ReservasService;
import com.BackBasiFit.service.RutinasService;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesService clientesService;
    private final ReservasService reservasService;
    private final MembresiasService membresiasService;
    private final RutinasService rutinasService;

    public ClientesController(ClientesService clientesService, ReservasService reservasService, MembresiasService membresiasService, RutinasService rutinasService) {
        this.clientesService = clientesService;
        this.reservasService = reservasService;
        this.membresiasService = membresiasService;
        this.rutinasService = rutinasService;
    }

    // GET todos los clientes
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping
    public List<Clientes> getAll() {

        return clientesService.findAll();
    }

    // GET cliente por id
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    @GetMapping("/{id}")
    public Clientes getById(@PathVariable String id) {

        return clientesService.findById(id);
    }

    //GET 5 clientes abonados
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @GetMapping("/numeroclientes")
    public List<Clientes> numeroclientesClientes() {
         return clientesService.findTop5ByOrderByEstadoDesc();
    }

    // POST crear cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PostMapping
    public ResponseEntity<Clientes> create(@RequestBody Clientes cliente) {
        Clientes clienteGuardado = clientesService.save(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteGuardado.getId()).toUri();
        
        return ResponseEntity.created(location).body(clienteGuardado);
    }

    // PUT actualizar datos
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PutMapping("/{id}")
    public Clientes update(@PathVariable String id, @RequestBody Clientes clienteActualizado) {
        Clientes clienteExistente = clientesService.findById(id);
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setApellido1(clienteActualizado.getApellido1());
        clienteExistente.setApellido2(clienteActualizado.getApellido2());
        clienteExistente.setDniNie(clienteActualizado.getDniNie());
        clienteExistente.setContrasena(clienteActualizado.getContrasena());
        clienteExistente.setEstado(clienteActualizado.getEstado());

        return clientesService.save(clienteExistente);
    }

    // PUT activar cuenta de cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PutMapping("/{id}/activar")
    public Clientes activar(@PathVariable String id) {
        Clientes clienteExistente = clientesService.findById(id);
        clienteExistente.setEstado(true);

        return clientesService.save(clienteExistente);
    }

    // PUT desactivar cuenta de cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @PutMapping("/{id}/desactivar")
    public Clientes desactivar(@PathVariable String id) {
        Clientes clienteExistente = clientesService.findById(id);
        clienteExistente.setEstado(false);

        return clientesService.save(clienteExistente);
    }

    // DELEETE eliminar cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        clientesService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET reservas de un cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    @GetMapping("/{id}/reservas")
    public List<Reservas> reservasDeCliente(@PathVariable String id) {

        return reservasService.findByCliente(id);
    }

    // GET membresas de un cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    @GetMapping("/{id}/membresias")
    public List<Membresias> membresiasDeCliente(@PathVariable String id) {

        return membresiasService.findByCliente(id);
    }

    // GET rutinas del cliente
    @PreAuthorize("hasAnyRole('ADMIN','EMPLEADO') or (hasRole('CLIENTE') and @securityUtil.esMiIdCliente(#p0))")
    @GetMapping("/{id}/rutinas")
    public List<Rutinas> rutinasDeCliente(@PathVariable String id) {
        
        return rutinasService.findByCliente(id);
    }
}
