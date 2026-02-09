package com.BackBasiFit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Gimnasios;

public interface GimnasiosRepository extends JpaRepository<Gimnasios, String> {
    List<Gimnasios> findTop5ByOrderByIdDesc();
    List<Gimnasios> findTop10ByOrderByEstadoDesc();
}
