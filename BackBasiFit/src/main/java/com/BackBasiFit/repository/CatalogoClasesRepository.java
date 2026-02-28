package com.BackBasiFit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.CatalogoClases;

public interface CatalogoClasesRepository extends JpaRepository<CatalogoClases, String> {

    Optional<CatalogoClases> findByNombreIgnoreCase(String nombre);

    List<CatalogoClases> findByEstadoTrueOrderByNombreAsc();
}
