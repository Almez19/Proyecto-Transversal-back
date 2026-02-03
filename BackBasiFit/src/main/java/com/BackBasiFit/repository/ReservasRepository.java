package com.BackBasiFit.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BackBasiFit.entity.Reservas;

public interface ReservasRepository extends JpaRepository<Reservas, String> {
    List<Reservas> findByClienteId(String clienteId);
    List<Reservas> findByClaseIdAndEstadoTrue(String claseId);
    Optional<Reservas> findByClienteIdAndClaseIdAndEstadoTrue(String clienteId, String claseId);
}
