package com.BackBasiFit.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BackBasiFit.entity.Membresias;

public interface MembresiasRepository extends JpaRepository<Membresias, String> {
    List<Membresias> findByClienteId(String clienteId);
    Optional<Membresias> findFirstByClienteIdAndEstadoTrueOrderByFechaFinalDesc(String clienteId);
}
