package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Ejercicios;
import com.BackBasiFit.repository.EjerciciosRepository;

@Service
public class EjerciciosService {
    private final EjerciciosRepository ejerciciosRepository;

    public EjerciciosService(EjerciciosRepository ejerciciosRepository) { 
        this.ejerciciosRepository = ejerciciosRepository; 
    }

    public List<Ejercicios> findAll() { 
        return ejerciciosRepository.findAll(); 
    }

    public Ejercicios findById(String id) {
        return ejerciciosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este ejercicio no existe"));
    }

    public List<Ejercicios> obtenerEjerciciosPorRutinaId(String rutinaId) {
        return ejerciciosRepository.findByRutinaId(rutinaId);
    }

    public Ejercicios save(Ejercicios ejercicio) { 
        return ejerciciosRepository.save(ejercicio); 
    }

    public void delete(String id) { 
        ejerciciosRepository.deleteById(id); 
    }

    public List<Ejercicios> findTop5ByOrderByIdDesc() { 
        return ejerciciosRepository.findTop5ByOrderByIdDesc(); 
    }
}

