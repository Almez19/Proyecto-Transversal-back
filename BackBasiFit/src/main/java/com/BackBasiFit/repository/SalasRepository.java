package com.BackBasiFit.repository;

import java.util.List;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Salas;

public interface SalasRepository extends JpaRepository<Salas, String> {

    List<Salas> findByGimnacioId(Spring gimnasioId);
}
