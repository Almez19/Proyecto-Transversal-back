package com.BackBasiFit.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.BackBasiFit.entity.Usuarios;
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

    // POST nueva usuarios
    @PostMapping
    public ResponseEntity<Usuarios> create(@RequestBody Usuarios usuario) {
        Usuarios saved = usuariosService.save(usuario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        
        return ResponseEntity.created(location).body(saved);
    }

    // DELETE eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        usuariosService.delete(id);
        
        return ResponseEntity.noContent().build();
    }
}
