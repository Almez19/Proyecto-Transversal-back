package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Salas;
import com.BackBasiFit.repository.SalasRepository;

@Service
public class SalasService {
    private final SalasRepository salasRepository;

    public SalasService(SalasRepository salasRepository) { 
        this.salasRepository = salasRepository; 
    }

    public List<Salas> findAll() { 
        return salasRepository.findAll(); 
    }

    public Salas findById(String id) {
        return salasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta sala no se ha encontrada"));
    }

    public List<Salas> findByGimnasio(String gimnasioId) {
        return salasRepository.findByGimnasioId(gimnasioId);
    }

    public Salas save(Salas sala) { 
        return salasRepository.save(sala); 
    }

    public void delete(String id) { 
        salasRepository.deleteById(id); 
    }

     public List<Salas> findTop5ByOrderByGimnasioIdDesc() { 
        return salasRepository.findTop5ByOrderByGimnasioIdDesc(); 
    }
}
