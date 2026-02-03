package com.BackBasiFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BackBasiFit.entity.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, String> {
    
}
