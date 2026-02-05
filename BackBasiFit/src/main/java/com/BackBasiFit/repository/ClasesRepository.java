package com.BackBasiFit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Clases;

public interface ClasesRepository extends JpaRepository<Clases, String> {
    List<Clases> findBySalaId(String salaId);
    List<Clases> findBySalaIdAndFecha(String salaId, LocalDate fecha);
    List<Clases> findByUsuarioClaseId(String usuarioClaseId);
}
