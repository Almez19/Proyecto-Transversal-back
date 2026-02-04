package com.BackBasiFit.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.BackBasiFit.enums.Calidad;
import com.BackBasiFit.enums.Duracion;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "membresias")
public class Membresias {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_final", nullable = false)
    private LocalDate fechaFinal;

    @Column(nullable = false)
    private Boolean estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Duracion duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Calidad calidad;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal precio;

    @Column(name = "cliente_id", nullable = false, length = 36)
    private String clienteId;

    public String getId() { 
        return id; 
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public LocalDate getFechaInicio() { 
        return fechaInicio; 
    }

    public void setFechaInicio(LocalDate fechaInicio) { 
        this.fechaInicio = fechaInicio; 
    }

    public LocalDate getFechaFinal() { 
        return fechaFinal; 
    }

    public void setFechaFinal(LocalDate fechaFinal) { 
        this.fechaFinal = fechaFinal; 
    }

    public Boolean getEstado() { 
        return estado; 
    }

    public void setEstado(Boolean estado) { 
        this.estado = estado; 
    }

    public Duracion getDuracion() { 
        return duracion; 
    }

    public void setDuracion(Duracion duracion) { 
        this.duracion = duracion; 
    }

    public Calidad getCalidad() { 
        return calidad; 
    }

    public void setCalidad(Calidad calidad) { 
        this.calidad = calidad; 
    }

    public BigDecimal getPrecio() { 
        return precio; 
    }

    public void setPrecio(BigDecimal precio) { 
        this.precio = precio; 
    }

    public String getClienteId() { 
        return clienteId; 
    }

    public void setClienteId(String clienteId) { 
        this.clienteId = clienteId; 
    }
}
