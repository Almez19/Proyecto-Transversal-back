package com.BackBasiFit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clases")
public class Clases {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 50)
    private String deporte;

    @Column(name = "hora_inicio", nullable = false)
    private LocalDateTime horaInicio;

    @Column(name = "hora_final", nullable = false)
    private LocalDateTime horaFinal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "sala_id", nullable = false, length = 36)
    private String salaId;

    @Column(name = "id_usuarios_c", nullable = false, length = 36)
    private String usuarioCreadorId;

    public String getId() { 
        return id;
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public String getDeporte() { 
        return deporte; 
    }
    
    public void setDeporte(String deporte) { 
        this.deporte = deporte; 
    }

    public LocalDateTime getHoraInicio() { 
        return horaInicio; 
    }

    public void setHoraInicio(LocalDateTime horaInicio) { 
        this.horaInicio = horaInicio; 
    }

    public LocalDateTime getHoraFinal() { 
        return horaFinal; 
    }

    public void setHoraFinal(LocalDateTime horaFinal) { 
        this.horaFinal = horaFinal; 
    }

    public LocalDate getFecha() { 
        return fecha; 
    }

    public void setFecha(LocalDate fecha) { 
        this.fecha = fecha; 
    }

    public String getSalaId() { 
        return salaId; 
    }

    public void setSalaId(String salaId) { 
        this.salaId = salaId; 
    }

    public String getUsuarioCreadorId() { 
        return usuarioCreadorId; 
    }

    public void setUsuarioCreadorId(String usuarioCreadorId) { 
        this.usuarioCreadorId = usuarioCreadorId; 
    }
}
