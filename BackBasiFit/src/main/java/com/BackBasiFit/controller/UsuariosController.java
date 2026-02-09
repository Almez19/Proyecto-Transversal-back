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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.enums.Rol;
import com.BackBasiFit.service.UsuariosService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    // GET todos los usurios
    @GetMapping
    public List<Usuarios> getAll() {

        return usuariosService.findAll();
    }

    // GET usuario por id
    @GetMapping("/{id}")
    public Usuarios getById(@PathVariable String id) {

        return usuariosService.findById(id);
    }

    // GET usuario por rol
    @GetMapping
    public List<Usuarios> getAll(@RequestParam(required = false) Rol rol) {
        if (rol != null) return usuariosService.findByRol(rol);

        return usuariosService.findAll();
    }

    // POST nueva usuarios
    @PostMapping
    public ResponseEntity<Usuarios> create(@RequestBody Usuarios usuario) {
        Usuarios usuarioGuardado = usuariosService.save(usuario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioGuardado.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioGuardado);
    }

    // PUT actualizar datos
    @PutMapping("/{id}")
    public Usuarios update(@PathVariable String id, @RequestBody Usuarios usuarioActualizado) {
        Usuarios usuarioExistente = usuariosService.findById(id);
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellido1(usuarioActualizado.getApellido1());
        usuarioExistente.setApellido2(usuarioActualizado.getApellido2());
        usuarioExistente.setDniNie(usuarioActualizado.getDniNie());
        usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
        usuarioExistente.setRol(usuarioActualizado.getRol());
        usuarioExistente.setGimnasioId(usuarioActualizado.getGimnasioId());
        usuarioExistente.setEstado(usuarioActualizado.getEstado());

        return usuariosService.save(usuarioExistente);
    }

    
    // PUT activar usuario
    @PutMapping("/{id}/activar")
    public Usuarios activar(@PathVariable String id) {
        Usuarios usuarioExistente = usuariosService.findById(id);
        usuarioExistente.setEstado(true);

        return usuariosService.save(usuarioExistente);
    }

    // PUT desactivar usuario
    @PutMapping("/{id}/desactivar")
    public Usuarios desactivar(@PathVariable String id) {
        Usuarios usuarioExistente = usuariosService.findById(id);
        usuarioExistente.setEstado(false);

        return usuariosService.save(usuarioExistente);
    }

    // DELETE eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        usuariosService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
