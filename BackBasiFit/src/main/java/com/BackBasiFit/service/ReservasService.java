package com.BackBasiFit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Clases;
import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.enums.EstadoClase;
import com.BackBasiFit.enums.EstadoReserva;
import com.BackBasiFit.repository.ClasesRepository;
import com.BackBasiFit.repository.ClientesRepository;
import com.BackBasiFit.repository.ReservasRepository;

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
        return reservasRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra esta reserva"));
    }

    public List<Reservas> findByCliente(String clienteId) {
        return reservasRepository.findByClienteIdOrderByFechaCreacionDesc(clienteId);
    }

    public Reservas reservar(String clienteId, String claseId) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Este cliente no existe"));

        if (Boolean.FALSE.equals(cliente.getEstado())) {
            throw new IllegalArgumentException("El cliente está desactivado");
        }

        Clases clase = clasesRepository.findById(claseId)
                .orElseThrow(() -> new IllegalArgumentException("Esta clase no existe"));

        if (clase.getEstado() != EstadoClase.programada) {
            throw new IllegalArgumentException("Solo se pueden reservar clases en estado 'programada'");
        }

        // Validación básica: no reservar clases pasadas
        if (clase.getFecha() != null && clase.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se pueden reservar clases pasadas");
        }

        long ocupacion = reservasRepository.countByClaseIdAndEstado(claseId, EstadoReserva.activa);
        if (clase.getCapacidad() != null && ocupacion >= clase.getCapacidad()) {
            throw new IllegalArgumentException("La clase está completa");
        }

        return reservasRepository.findByClienteIdAndClaseId(clienteId, claseId)
                .map(reservaExistente -> {
                    if (reservaExistente.getEstado() == EstadoReserva.activa) {
                        throw new IllegalArgumentException("Ya tienes una reserva activa para esta clase");
                    }
                    reservaExistente.setEstado(EstadoReserva.activa);
                    reservaExistente.setFechaCancelacion(null);
                    reservaExistente.setMotivoCancelacion(null);
                    return reservasRepository.save(reservaExistente);
                })
                .orElseGet(() -> {
                    Reservas reserva = new Reservas();
                    reserva.setClienteId(clienteId);
                    reserva.setClaseId(claseId);
                    reserva.setEstado(EstadoReserva.activa);
                    return reservasRepository.save(reserva);
                });
    }

    public Reservas reservarPorEmailCliente(String emailCliente, String claseId) {
        if (emailCliente == null || emailCliente.isBlank()) {
            throw new IllegalArgumentException("No se puede determinar el cliente autenticado");
        }

        String clienteId = clientesRepository.findByEmail(emailCliente)
                .map(Clientes::getId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente con ese email"));

        return reservar(clienteId, claseId);
    }

    public Reservas cancelar(String reservaId, String motivo) {
        Reservas reserva = findById(reservaId);
        reserva.setEstado(EstadoReserva.cancelada);
        reserva.setFechaCancelacion(LocalDateTime.now());
        if (motivo != null && !motivo.isBlank()) {
            reserva.setMotivoCancelacion(motivo);
        }
        return reservasRepository.save(reserva);
    }

    public Reservas save(Reservas reserva) {
        return reservasRepository.save(reserva);
    }

    public void delete(String reservaId) {
        reservasRepository.deleteById(reservaId);
    }
}
