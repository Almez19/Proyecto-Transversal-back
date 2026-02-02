package com.BackBasiFit.repository;

import java.util.List;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Maquinas;


public interface MaquinasRepository extends JpaRepository<Maquinas, Long> {

    List<Maquinas> findByGimnacioId(Spring gimnasioId);
}
