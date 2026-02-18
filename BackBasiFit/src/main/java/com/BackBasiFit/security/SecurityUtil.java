package com.BackBasiFit.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.BackBasiFit.repository.ClientesRepository;
import com.BackBasiFit.repository.MembresiasRepository;
import com.BackBasiFit.repository.ReservasRepository;
import com.BackBasiFit.repository.RutinasRepository;

@Component("securityUtil")
public class SecurityUtil { private final ClientesRepository clientesRepository; 
                            private final ReservasRepository reservasRepository;
                            private final MembresiasRepository membresiasRepository;
                            private final RutinasRepository rutinasRepository;

    public SecurityUtil(ClientesRepository clientesRepository, ReservasRepository reservasRepository, MembresiasRepository membresiasRepository, RutinasRepository rutinasRepository) {
        this.clientesRepository = clientesRepository;
        this.reservasRepository = reservasRepository;
        this.membresiasRepository = membresiasRepository;
        this.rutinasRepository = rutinasRepository;
    }

    private String emailAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) return null;
        
        return auth.getName();
    }

    // Permite el acceso si la id coincide con la propia
    public boolean esMiIdCliente(String idCliente) {
        String email = emailAutenticado();
        if (email == null || idCliente == null) return false;

        return clientesRepository.findById(idCliente).map(cliente -> email.equalsIgnoreCase(cliente.getEmail())).orElse(false);
    }

    // Comprueba si una reserva pertenece al cliente autenticado
    public boolean esReservaDeMiCliente(String idReserva) {
        if (idReserva == null) return false;

        return reservasRepository.findById(idReserva).map(reserva -> esMiIdCliente(reserva.getClienteId())).orElse(false);
    }

    // Comprueba si una membresía pertenece al cliente autenticado
    public boolean esMembresiaDeMiCliente(String idMembresia) {
        if (idMembresia == null) return false;

        return membresiasRepository.findById(idMembresia).map(membresia -> esMiIdCliente(membresia.getClienteId())).orElse(false);
    }

    // Comprueba si una rutina pertenece al cliente autenticado
    public boolean esRutinaDeMiCliente(String idRutina) {
        if (idRutina == null) return false;

        return rutinasRepository.findById(idRutina).map(rutina -> esMiIdCliente(rutina.getClienteId())).orElse(false);
    }

    public boolean esClienteConId(Authentication authentication, String idCliente) {
        if (authentication == null || authentication.getName() == null) {

            return false;
        }

        return clientesRepository.findById(idCliente).map(cliente -> authentication.getName().equalsIgnoreCase(cliente.getEmail())).orElse(false);
    }
}
