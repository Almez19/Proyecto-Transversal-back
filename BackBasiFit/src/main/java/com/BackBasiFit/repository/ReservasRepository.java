package com.BackBasiFit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.enums.EstadoReserva;

public interface ReservasRepository extends JpaRepository<Reservas, String> {

    List<Reservas> findByClienteIdOrderByFechaCreacionDesc(String clienteId);

    List<Reservas> findByClaseIdAndEstado(String claseId, EstadoReserva estado);

    Optional<Reservas> findByClienteIdAndClaseIdAndEstado(String clienteId, String claseId, EstadoReserva estado);

    Optional<Reservas> findByClienteIdAndClaseId(String clienteId, String claseId);

    long countByClaseIdAndEstado(String claseId, EstadoReserva estado);
}
