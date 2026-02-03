package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Salas;
import com.BackBasiFit.repository.SalasRepository;

@Service
public class SalasService {
    private final SalasRepository repo;

    public SalasService(SalasRepository repo) { 
        this.repo = repo; 
    }

    public List<Salas> findAll() { 
        return repo.findAll(); 
    }

    public Salas findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta sala no se ha encontrada"));
    }

    public List<Salas> findByGimnasio(String gimnasio_id) {
        return repo.findByGimnasio_id(gimnasio_id);
    }

    public Salas save(Salas s) { 
        return repo.save(s); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
