package com.BackBasiFit.controller;

import java.security.Principal;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BackBasiFit.entity.Clientes;
import com.BackBasiFit.entity.Ejercicios;
import com.BackBasiFit.entity.Maquinas;
import com.BackBasiFit.entity.Membresias;
import com.BackBasiFit.entity.Reservas;
import com.BackBasiFit.entity.Rutinas;
import com.BackBasiFit.repository.ClientesRepository;
import com.BackBasiFit.repository.MaquinasRepository;
import com.BackBasiFit.enums.Calidad;
import com.BackBasiFit.enums.Duracion;
import com.BackBasiFit.service.EjerciciosService;
import com.BackBasiFit.service.MembresiasService;
import com.BackBasiFit.service.ReservasService;
import com.BackBasiFit.service.RutinasService;

@RestController
@RequestMapping("/api/mi-cuenta")
@PreAuthorize("hasRole('CLIENTE')")
public class MiCuentaController {

    private final ClientesRepository clientesRepository;
    private final ReservasService reservasService;
    private final RutinasService rutinasService;
    private final EjerciciosService ejerciciosService;
    private final MembresiasService membresiasService;
    private final MaquinasRepository maquinasRepository;
    private final PasswordEncoder passwordEncoder;

    public MiCuentaController(
            ClientesRepository clientesRepository,
            ReservasService reservasService,
            RutinasService rutinasService,
            EjerciciosService ejerciciosService,
            MembresiasService membresiasService,
            MaquinasRepository maquinasRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clientesRepository = clientesRepository;
        this.reservasService = reservasService;
        this.rutinasService = rutinasService;
        this.ejerciciosService = ejerciciosService;
        this.membresiasService = membresiasService;
        this.maquinasRepository = maquinasRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET Perfil
    @GetMapping("/perfil")
    public Clientes miPerfil(Principal principal) {
        String email = principal != null ? principal.getName() : null;
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("No se puede determinar el cliente autenticado");
        }
        return clientesRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente con ese email"));
    }

    // PUT Perfil
    @PutMapping("/perfil")
    public Clientes actualizarMiPerfil(@RequestBody Clientes cambios, Principal principal) {
        String email = principal != null ? principal.getName() : null;
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("No se puede determinar el cliente autenticado");
        }
        Clientes actual = clientesRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente con ese email"));

        if (cambios.getNombre() != null) actual.setNombre(cambios.getNombre());
        if (cambios.getApellido1() != null) actual.setApellido1(cambios.getApellido1());
        if (cambios.getApellido2() != null) actual.setApellido2(cambios.getApellido2());
        if (cambios.getDniNie() != null) actual.setDniNie(cambios.getDniNie());
        actual.setTelefono(cambios.getTelefono());
        actual.setCiudad(cambios.getCiudad());
        actual.setFechaNacimiento(cambios.getFechaNacimiento());

        return clientesRepository.save(actual);
    }

    // PUT Contraseña
    @PutMapping("/perfil/password")
    public void cambiarPassword(@RequestBody PasswordChangeRequest req, Principal principal) {
        if (req == null || req.actual == null || req.actual.isBlank() || req.nueva == null || req.nueva.isBlank()) {
            throw new IllegalArgumentException("Contraseña actual y nueva son obligatorias");
        }
        String email = principal != null ? principal.getName() : null;
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("No se puede determinar el cliente autenticado");
        }
        Clientes actual = clientesRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente con ese email"));

        if (!passwordEncoder.matches(req.actual, actual.getContrasena())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta");
        }
        actual.setContrasena("{noop}" + req.nueva);
        clientesRepository.save(actual);
    }

    // GET Reservas
    @GetMapping("/reservas")
    public List<Reservas> misReservas(Principal principal) {
        return reservasService.findByCliente(obtenerClienteId(principal));
    }

    // GET Rutinas
    @GetMapping("/rutinas")
    public List<Rutinas> misRutinas(Principal principal) {
        return rutinasService.findByClienteOrdenadas(obtenerClienteId(principal));
    }

    // GET Membresias
    @GetMapping("/membresias")
    public List<Membresias> misMembresias(Principal principal) {
        return membresiasService.findByCliente(obtenerClienteId(principal));
    }

    // POST contratar membresia
    @PostMapping("/membresias/contratar")
    public Membresias contratar(@RequestBody ContratarMembresiaRequest req, Principal principal) {
        if (req == null || req.duracion == null || req.calidad == null) {
            throw new IllegalArgumentException("Duración y calidad son obligatorias");
        }

        String clienteId = obtenerClienteId(principal);

        Duracion duracion;
        Calidad calidad;
        try {
            duracion = Duracion.valueOf(req.duracion);
            calidad = Calidad.valueOf(req.calidad);
        } catch (Exception e) {
            throw new IllegalArgumentException("Duración o calidad no válidas");
        }

        // Desactivar membresía activa previa si existe
        try {
            Membresias activa = membresiasService.findActivaByCliente(clienteId);
            activa.setEstado(false);
            membresiasService.save(activa);
        } catch (Exception ignore) {
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fin;
        switch (duracion) {
            case diario -> fin = hoy.plusDays(1);
            case semanal -> fin = hoy.plusWeeks(1);
            case mensual -> fin = hoy.plusMonths(1);
            case trimestral -> fin = hoy.plusMonths(3);
            case anual -> fin = hoy.plusYears(1);
            default -> fin = hoy.plusMonths(1);
        }

        BigDecimal baseMensual = switch (calidad) {
            case comfort -> new BigDecimal("24.99");
            case premium -> new BigDecimal("29.99");
            case ultimate -> new BigDecimal("34.99");
        };

        BigDecimal precio = switch (duracion) {
            case diario -> baseMensual.divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
            case semanal -> baseMensual.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
            case mensual -> baseMensual;
            case trimestral -> baseMensual.multiply(new BigDecimal("3")).multiply(new BigDecimal("0.95"));
            case anual -> baseMensual.multiply(new BigDecimal("12")).multiply(new BigDecimal("0.85"));
        };

        Membresias m = new Membresias();
        m.setClienteId(clienteId);
        m.setDuracion(duracion);
        m.setCalidad(calidad);
        m.setFechaInicio(hoy);
        m.setFechaFinal(fin);
        m.setPrecio(precio);
        m.setEstado(true);

        return membresiasService.save(m);
    }

    @org.springframework.web.bind.annotation.PutMapping("/membresias/{id}/cancelar")
    public Membresias cancelarMembresia(@PathVariable String id, Principal principal) {
        String clienteId = obtenerClienteId(principal);
        Membresias m = membresiasService.findById(id);
        if (!clienteId.equals(m.getClienteId())) {
            throw new IllegalArgumentException("No tienes permisos para cancelar esta membresía");
        }
        return membresiasService.cambiarEstado(id, false);
    }

    // GET Rutinas
    @GetMapping("/rutinas/{rutinaId}/ejercicios")
    public List<Ejercicios> ejerciciosDeMiRutina(@PathVariable String rutinaId, Principal principal) {
        String clienteId = obtenerClienteId(principal);
        Rutinas rutina = rutinasService.findById(rutinaId);
        if (!clienteId.equals(rutina.getClienteId())) {
            throw new IllegalArgumentException("No tienes permisos para ver esta rutina");
        }
        return ejerciciosService.obtenerEjerciciosPorRutinaId(rutinaId);
    }

    // GET Reservas detalles
    @GetMapping("/rutinas/{rutinaId}/detalle")
    public RutinaDetalleDTO detalleRutina(@PathVariable String rutinaId, Principal principal) {
        String clienteId = obtenerClienteId(principal);
        Rutinas rutina = rutinasService.findById(rutinaId);
        if (!clienteId.equals(rutina.getClienteId())) {
            throw new IllegalArgumentException("No tienes permisos para ver esta rutina");
        }

        List<Ejercicios> ejercicios = ejerciciosService.obtenerEjerciciosPorRutinaId(rutinaId);
        List<EjercicioDetalleDTO> ejerciciosDetalle = ejercicios.stream().map(e -> {
            Maquinas maquina = null;
            if (e.getMaquinaId() != null && !e.getMaquinaId().isBlank()) {
                maquina = maquinasRepository.findById(e.getMaquinaId()).orElse(null);
            }
            return new EjercicioDetalleDTO(e, maquina);
        }).toList();

        return new RutinaDetalleDTO(rutina, ejerciciosDetalle);
    }

    public static class RutinaDetalleDTO {
        public Rutinas rutina;
        public List<EjercicioDetalleDTO> ejercicios;

        public RutinaDetalleDTO(Rutinas rutina, List<EjercicioDetalleDTO> ejercicios) {
            this.rutina = rutina;
            this.ejercicios = ejercicios;
        }
    }

    public static class EjercicioDetalleDTO {
        public Ejercicios ejercicio;
        public Maquinas maquina; 

        public EjercicioDetalleDTO(Ejercicios ejercicio, Maquinas maquina) {
            this.ejercicio = ejercicio;
            this.maquina = maquina;
        }
    }

    public static class ContratarMembresiaRequest {
        public String duracion;
        public String calidad;
    }

    public static class PasswordChangeRequest {
        public String actual;
        public String nueva;
    }

    private String obtenerClienteId(Principal principal) {
        if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
            throw new IllegalArgumentException("No se puede determinar el cliente autenticado");
        }
        return clientesRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("No existe un cliente con ese email"))
                .getId();
    }
}
