package com.BackBasiFit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.repository.RutinasRepository;

@Service
public class RutinasService {

    private final RutinasRepository rutinasRepository;

    public RutinasService(RutinasRepository rutinasRepository) {
        this.rutinasRepository = rutinasRepository;
    }

    public List<Rutinas> findAll() {
        return rutinasRepository.findAll();
    }

    public Rutinas findById(String id) {
        return rutinasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Esta rutina no se ha encontrado"));
    }

    public List<Rutinas> findByCliente(String clienteId) {
        return rutinasRepository.findByClienteId(clienteId);
    }

    public List<Rutinas> findByClienteOrdenadas(String clienteId) {
        return rutinasRepository.findByClienteIdOrderByFechaActualizacionDesc(clienteId);
    }

    public Rutinas save(Rutinas rutina) {
        return rutinasRepository.save(rutina);
    }

    public void delete(String id) {
        rutinasRepository.deleteById(id);
    }

    public List<Rutinas> findTop5Recientes() {
        return rutinasRepository.findTop5ByOrderByFechaCreacionDesc();
    }
}
