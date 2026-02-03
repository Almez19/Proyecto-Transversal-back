package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.repository.UsuariosRepository;

@Service
public class UsuariosService {
    private final UsuariosRepository repo;

    public UsuariosService(UsuariosRepository repo) { 
        this.repo = repo; 
    }

    public List<Usuarios> findAll() { 
        return repo.findAll(); 
    }

    public Usuarios findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Este usuario no existe"));
    }

    public Usuarios save(Usuarios u) { 
        return repo.save(u); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
