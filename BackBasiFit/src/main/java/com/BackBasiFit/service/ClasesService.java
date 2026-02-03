package com.BackBasiFit.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.repository.ClasesRepository;

@Service
public class ClasesService {
    private final ClasesRepository repo;

    public ClasesService(ClasesRepository repo) { 
        this.repo = repo; 
    }

    public List<Clases> findAll() { 
        return repo.findAll(); 
    }

    public Clases findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));
    }
    
    public List<Clases> findBySala(String salaId) { 
        return repo.findBySalaId(salaId); 
    }

    public List<Clases> findBySalaAndFecha(String salaId, LocalDate fecha) { 
        return repo.findBySalaIdAndFecha(salaId, fecha); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
