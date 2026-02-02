package com.BackBasiFit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Ejercicios;

public interface EjerciciosRepository extends JpaRepository<Ejercicios, String> {
    
}

