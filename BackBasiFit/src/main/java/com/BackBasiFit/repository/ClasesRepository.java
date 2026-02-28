package com.BackBasiFit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BackBasiFit.entity.Clases;

public interface ClasesRepository extends JpaRepository<Clases, String> {

    List<Clases> findByGimnasioId(String gimnasioId);

    List<Clases> findBySalaId(String salaId);

    List<Clases> findByEntrenadorId(String entrenadorId);

    List<Clases> findByFecha(LocalDate fecha);

    List<Clases> findBySalaIdAndFecha(String salaId, LocalDate fecha);

    List<Clases> findByGimnasioIdAndFecha(String gimnasioId, LocalDate fecha);

    // Catálogo por gimnasio (sin horarios): nombres de clases que se imparten en un gimnasio
    @Query("select distinct c.nombre from Clases c where c.gimnasioId = :gimnasioId order by c.nombre asc")
    List<String> findNombresDisponiblesPorGimnasio(@Param("gimnasioId") String gimnasioId);
}
