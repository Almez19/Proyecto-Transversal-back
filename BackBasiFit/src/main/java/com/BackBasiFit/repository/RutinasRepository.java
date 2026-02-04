package com.BackBasiFit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Rutinas;

public interface RutinasRepository extends JpaRepository<Rutinas, String> {
    List<Rutinas> findByClienteId(String clienteId);
}
