package com.BackBasiFit.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BackBasiFit.entity.Maquinas;

public interface MaquinasRepository extends JpaRepository<Maquinas, String> {
    List<Maquinas> findByGimnasiosId(String gimnasiosId);
}
