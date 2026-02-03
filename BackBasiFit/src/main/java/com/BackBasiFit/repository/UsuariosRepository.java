package com.BackBasiFit.repository;

import com.BackBasiFit.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    
}
