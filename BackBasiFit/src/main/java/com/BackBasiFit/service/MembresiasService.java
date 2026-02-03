package com.BackBasiFit.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.repository.MembresiasRepository;

@Service
public class MembresiasService {
    private final MembresiasRepository repo;

    public MembresiasService(MembresiasRepository repo) { 
        this.repo = repo; 
    }

    public List<Membresias> findAll() { 
        return repo.findAll(); 
    }

    public Membresias findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encuentra esta membresia"));
    }

    public List<Membresias> findByCliente(String clienteId) { 
        return repo.findByClienteId(clienteId); 
    }

    public Membresias findActivaByCliente(String clienteId) {
        return repo.findFirstByClienteIdAndEstadoTrueOrderByFechaFinalDesc(clienteId).orElseThrow(() -> new IllegalArgumentException("No hay ninguna membresia activada"));
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
