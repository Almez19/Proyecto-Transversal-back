package com.BackBasiFit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.enums.Rol;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    List<Usuarios> findByRol(Rol rol);
    List<Usuarios> findTop5ByOrderByEstadoDesc();
    Optional<Usuarios> findByDniNie(String dniNie);
}
