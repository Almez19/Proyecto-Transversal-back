package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reservas {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(name = "cliente_id", nullable = false, length = 36)
    private String clienteId;

    @Column(name = "clase_id", nullable = false, length = 36)
    private String claseId;

    @Column(nullable = false)
    private Boolean estado = true;

    public String getId() { 
        return id; 
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public String getClienteId() { 
        return clienteId; 
    }

    public void setClienteId(String clienteId) { 
        this.clienteId = clienteId; 
    }

    public String getClaseId() { 
        return claseId; 
    }
    
    public void setClaseId(String claseId) { 
        this.claseId = claseId; 
    }

    public Boolean getEstado() { 
        return estado; 
    }

    public void setEstado(Boolean estado) { 
        this.estado = estado; 
    }
}
