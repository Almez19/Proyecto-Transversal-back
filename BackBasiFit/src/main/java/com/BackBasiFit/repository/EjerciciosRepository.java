package com.BackBasiFit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Ejercicios;

public interface EjerciciosRepository extends JpaRepository<Ejercicios, String> {
    List<Ejercicios> findByRutinaId(String rutinaId);
    List<Ejercicios> findTop5ByOrderByIdDesc();
}
