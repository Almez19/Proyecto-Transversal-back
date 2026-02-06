package com.BackBasiFit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Noticias;

public interface NoticiasRepository extends JpaRepository<Noticias, String> {
    List<Noticias> findByGimnasioId(String gimnasioId);
    List<Noticias> findTop3ByOrderByFechaDesc();
}
