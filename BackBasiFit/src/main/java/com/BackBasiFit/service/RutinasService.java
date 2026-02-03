package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.repository.RutinasRepository;

@Service
public class RutinasService {
    private final RutinasRepository repo;

    public RutinasService(RutinasRepository repo) { 
        this.repo = repo; 
    }

    public List<Rutinas> findAll() { 
        return repo.findAll(); 
    }

    public Rutinas findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta rutina no se ha encontrada"));
    }

    public List<Rutinas> findByCliente(String clienteId) { 
        return repo.findByClienteId(clienteId); 
    }

    public Rutinas save(Rutinas r) { 
        return repo.save(r); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
