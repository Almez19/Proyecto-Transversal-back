package com.BackBasiFit.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.repository.ClasesRepository;

@Service
public class ClasesService {
    private final ClasesRepository clasesRepository;

    public ClasesService(ClasesRepository clasesRepository) { 
        this.clasesRepository = clasesRepository; 
    }

    public List<Clases> findAll() { 
        return clasesRepository.findAll(); 
    }

    public Clases findById(String id) {
        return clasesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));
    }
    
    public List<Clases> findBySala(String salaId) { 
        return clasesRepository.findBySalaId(salaId); 
    }

    public List<Clases> findBySalaAndFecha(String salaId, LocalDate fecha) { 
        return clasesRepository.findBySalaIdAndFecha(salaId, fecha); 
    }

    public List<Clases> findByUsuarioClase(String usuarioClaseId) {
        return clasesRepository.findByUsuarioClaseId(usuarioClaseId);
    }

    public Clases save(Clases clase) {
        return clasesRepository.save(clase);
    }

    public void delete(String id) { 
        clasesRepository.deleteById(id); 
    }
}
