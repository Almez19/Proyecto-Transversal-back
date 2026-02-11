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
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;
    private final ClientesRepository clientesRepository;

    public CustomUserDetailsService(UsuariosRepository usuariosRepository,
                                    ClientesRepository clientesRepository) {
        this.usuariosRepository = usuariosRepository;
        this.clientesRepository = clientesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String dniNie) throws UsernameNotFoundException {

        // login como usuario
        Usuarios u = usuariosRepository.findByDniNie(dniNie).orElse(null);
        if (u != null) {
            String role = "ROLE_" + u.getRol().name().toUpperCase(); 

            return User.withUsername(u.getDniNie())
                    .password(u.getContrasena())
                    .authorities(List.of(new SimpleGrantedAuthority(role)))
                    .disabled(!u.getEstado())
                    .build();
        }

        // login como cliente
        Clientes c = clientesRepository.findByDniNie(dniNie).orElse(null);
        if (c != null) {
            return User.withUsername(c.getDniNie())
                    .password(c.getContrasena())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")))
                    .disabled(!c.getEstado())
                    .build();
        }

        throw new UsernameNotFoundException("No existe usuario/cliente con DNI/NIE: " + dniNie);
    }
}
