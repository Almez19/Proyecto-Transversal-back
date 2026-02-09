package com.BackBasiFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackBasiFit.entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    // List<Usuarios> findByRol(Rol rol);
}
