package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Ejercicios;
import com.BackBasiFit.repository.EjerciciosRepository;

@Service
public class EjerciciosService {
    private final EjerciciosRepository repo;

    public EjerciciosService(EjerciciosRepository repo) { 
        this.repo = repo; 
    }

    public List<Ejercicios> findAll() { 
        return repo.findAll(); 
    }

    public Ejercicios findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Este ejercicio no existe"));
    }

    public List<Ejercicios> findByRutina(String rutina) { 
        return repo.findByRutina(rutina); 
    }

    public Ejercicios save(Ejercicios e) { 
        return repo.save(e); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
