package com.BackBasiFit.repository;

import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.enums.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    List<Usuarios> findByRol(Rol rol);
}
