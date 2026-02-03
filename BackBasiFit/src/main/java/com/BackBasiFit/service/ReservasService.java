package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.repository.ClasesRepository;
import com.BackBasiFit.repository.ReservasRepository;
import com.BackBasiFit.repository.ClientesRepository;

@Service
public class ReservasService {
    private final ReservasRepository repo;
    private final ClientesRepository clientesRepo;
    private final ClasesRepository clasesRepo;

    public ReservasService(ReservasRepository repo, ClientesRepository clientesRepo, ClasesRepository clasesRepo) {
        this.repo = repo;
        this.clientesRepo = clientesRepo;
        this.clasesRepo = clasesRepo;
    }

    public List<Reservas> findAll() { 
        return repo.findAll(); 
    }

    public Reservas findById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encuentra esta reserva"));
    }

    public List<Reservas> findByCliente(String clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public List<Reservas> findActivasByClase(String claseId) {
        return repo.findByClaseIdAndEstadoTrue(claseId);
    }

    public Reservas reservar(String clienteId, String claseId) {
        clientesRepo.findById(clienteId).orElseThrow(() -> new IllegalArgumentException("Este cliente no existe"));
        clasesRepo.findById(claseId).orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));
        repo.findByClienteIdAndClaseIdAndEstadoTrue(clienteId, claseId).ifPresent(r -> { throw new IllegalArgumentException("Ya hay ninguna reserva para esta clase"); });

        Reservas r = new Reservas();
        r.setClienteId(clienteId);
        r.setClaseId(claseId);
        r.setEstado(true);
        return repo.save(r);
    }

    public Reservas cancelar(String reservaId) {
        Reservas r = findById(reservaId);
        r.setEstado(false);
        return repo.save(r);
    }

    public Reservas save(Reservas r) { 
        return repo.save(r); 
    }

    public void delete(String id) { 
        repo.deleteById(id); 
    }
}
