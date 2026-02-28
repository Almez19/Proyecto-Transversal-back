package com.BackBasiFit.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.repository.ClientesRepository;
import com.BackBasiFit.repository.ReservasRepository;
import com.BackBasiFit.repository.RutinasRepository;

@Component("securityUtil")
public class SecurityUtil {

    private final ClientesRepository clientesRepository;
    private final ReservasRepository reservasRepository;
    private final RutinasRepository rutinasRepository;

    public SecurityUtil(
            ClientesRepository clientesRepository,
            ReservasRepository reservasRepository,
            RutinasRepository rutinasRepository
    ) {
        this.clientesRepository = clientesRepository;
        this.reservasRepository = reservasRepository;
        this.rutinasRepository = rutinasRepository;
    }

    // Permite el acceso si la id coincide con la propia
    public boolean esMiIdCliente(String clienteId) {
        String email = emailAutenticado();
        if (email == null || clienteId == null) return false;

        return clientesRepository.findByEmail(email)
                .map(c -> c.getId().equals(clienteId))
                .orElse(false);
    }

    // Comprueba si una reserva pertenece al cliente autenticado
    public boolean esReservaDeMiCliente(String reservaId) {
        String email = emailAutenticado();
        if (email == null || reservaId == null) return false;

        return clientesRepository.findByEmail(email)
                .flatMap(cliente -> reservasRepository.findById(reservaId)
                        .map(Reservas::getClienteId)
                        .map(idClienteReserva -> idClienteReserva.equals(cliente.getId())))
                .orElse(false);
    }

    // Comprueba si una rutina pertenece al cliente autenticado
    public boolean esRutinaDeMiCliente(String rutinaId) {
        String email = emailAutenticado();
        if (email == null || rutinaId == null) return false;

        return clientesRepository.findByEmail(email)
                .flatMap(cliente -> rutinasRepository.findById(rutinaId)
                        .map(Rutinas::getClienteId)
                        .map(idClienteRutina -> idClienteRutina.equals(cliente.getId())))
                .orElse(false);
    }

    private String emailAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        return auth.getName();
    }
}
