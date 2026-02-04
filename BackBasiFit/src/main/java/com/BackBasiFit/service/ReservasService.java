package com.BackBasiFit.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.repository.ClasesRepository;
import com.BackBasiFit.repository.ReservasRepository;
import com.BackBasiFit.repository.ClientesRepository;

@Service
public class ReservasService {
    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final ClasesRepository clasesRepository;

    public ReservasService(
            ReservasRepository reservasRepository,
            ClientesRepository clientesRepository,
            ClasesRepository clasesRepository
    ) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.clasesRepository = clasesRepository;
    }

    public List<Reservas> findAll() { 
        return reservasRepository.findAll(); 
    }

    public Reservas findById(String reservaId) {
        return reservasRepository.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("No se encuentra esta reserva"));
    }

    public List<Reservas> findByCliente(String clienteId) {
        return reservasRepository.findByClienteId(clienteId);
    }

    public List<Reservas> findActivasByClase(String claseId) {
        return reservasRepository.findByClaseIdAndEstadoTrue(claseId);
    }

    public Reservas reservar(String clienteId, String claseId) {
        clientesRepository.findById(clienteId).orElseThrow(() -> new IllegalArgumentException("Este cliente no existe"));
        clasesRepository.findById(claseId).orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));
        reservasRepository.findByClienteIdAndClaseIdAndEstadoTrue(clienteId, claseId).ifPresent(reservaExistente -> {
            throw new IllegalArgumentException("Ya hay una reserva para esta clase");
        });

        Reservas reserva = new Reservas();
        reserva.setClienteId(clienteId);
        reserva.setClaseId(claseId);
        reserva.setEstado(true);
        return reservasRepository.save(reserva);
    }

    public Reservas cancelar(String reservaId) {
        Reservas reserva = findById(reservaId);
        reserva.setEstado(false);
        return reservasRepository.save(reserva);
    }

    public Reservas save(Reservas reserva) { 
        return reservasRepository.save(reserva); 
    }

    public void delete(String reservaId) {
        reservasRepository.deleteById(reservaId);
    }
}
