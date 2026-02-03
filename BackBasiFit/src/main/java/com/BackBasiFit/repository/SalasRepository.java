package com.BackBasiFit.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BackBasiFit.entity.Salas;

public interface SalasRepository extends JpaRepository<Salas, String> {
    List<Salas> findByGimnasio_id(String gimnasio_id);
}
