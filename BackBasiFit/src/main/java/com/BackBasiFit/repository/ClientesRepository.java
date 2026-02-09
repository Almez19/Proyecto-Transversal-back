package com.BackBasiFit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, String> {
    List<Clientes> findTop5ByOrderByEstadoDesc();
}
