package com.BackBasiFit.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.repository.MembresiasRepository;

@Service
public class MembresiasService {
    private final MembresiasRepository membresiasRepository;

    public MembresiasService(MembresiasRepository membresiasRepository) { 
        this.membresiasRepository = membresiasRepository; 
    }

    public List<Membresias> findAll() { 
        return membresiasRepository.findAll(); 
    }

    public Membresias findById(String id) {
        return membresiasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encuentra esta membresia"));
    }

    public List<Membresias> findByCliente(String clienteId) { 
        return membresiasRepository.findByClienteId(clienteId); 
    }

    public Membresias findActivaByCliente(String clienteId) {
        return membresiasRepository.findFirstByClienteIdAndEstadoTrueOrderByFechaFinalDesc(clienteId).orElseThrow(() -> new IllegalArgumentException("No hay ninguna membresia activada"));
    }

    public Membresias save(Membresias membresia) {
        return membresiasRepository.save(membresia);
    }

    public Membresias cambiarEstado(String membresiaId, boolean estado) {
        Membresias membresia = findById(membresiaId);
        membresia.setEstado(estado);
        return membresiasRepository.save(membresia);
    }

    public void delete(String id) { 
        membresiasRepository.deleteById(id); 
    }
}
