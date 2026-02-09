package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.repository.UsuariosRepository;

@Service
public class UsuariosService {
    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) { 
        this.usuariosRepository = usuariosRepository; 
    }

    public List<Usuarios> findAll() { 
        return usuariosRepository.findAll(); 
    }

    public Usuarios findById(String id) {
        return usuariosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este usuario no existe"));
    }

    public Usuarios save(Usuarios usuario) { 
        return usuariosRepository.save(usuario); 
    }

    public void delete(String id) { 
        usuariosRepository.deleteById(id); 
    }

    // public List<Usuarios> findByRol(Rol rol) {
    //     return usuariosRepository.findByRol(rol);
    // }
}
