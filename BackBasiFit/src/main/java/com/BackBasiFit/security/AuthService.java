package com.BackBasiFit.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.entity.Usuarios;
import com.BackBasiFit.repository.ClientesRepository;
import com.BackBasiFit.repository.UsuariosRepository;

@Service
public class AuthService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;
    private final ClientesRepository clientesRepository;

    public AuthService(UsuariosRepository usuariosRepository, ClientesRepository clientesRepository) {
        this.usuariosRepository = usuariosRepository;
        this.clientesRepository = clientesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // login como usuario
        Usuarios usuarios = usuariosRepository.findByEmail(email).orElse(null);
        if (usuarios != null) { String rol = "ROLE_" + usuarios.getRol().name().toUpperCase(); 

            return User.withUsername(usuarios.getEmail()).password(usuarios.getContrasena()).authorities(List.of(new SimpleGrantedAuthority(rol))).disabled(!usuarios.getEstado()).build();
        }

        // login como cliente
        Clientes clientes = clientesRepository.findByEmail(email).orElse(null);
        if (clientes != null) {

            return User.withUsername(clientes.getEmail()).password(clientes.getContrasena()).authorities(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))).disabled(!clientes.getEstado()).build();
        }

        throw new UsernameNotFoundException("No existe usuario/cliente con el Email: " + email);
    }
}
