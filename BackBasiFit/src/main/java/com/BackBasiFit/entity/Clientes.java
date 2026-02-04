package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Clientes {

    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido1;

    @Column(nullable = false, length = 50)
    private String apellido2;

    @Column(name = "DNI_NIE", nullable = false, unique = true, length = 9)
    private String dniNie;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado = true;

    public String getId() { 
        return id; 
    }

    public void setId(String id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getApellido1() { 
        return apellido1; 
    }

    public void setApellido1(String apellido1) { 
        this.apellido1 = apellido1; 
    }

    public String getApellido2() { 
        return apellido2; 
    }

    public void setApellido2(String apellido2) { 
        this.apellido2 = apellido2; 
    }

    public String getDniNie() { 
        return dniNie; 
    }

    public void setDniNie(String dniNie) { 
        this.dniNie = dniNie; 
    }

    public String getContrasena() { 
        return contrasena; 
    }

    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena; 
    }

    public Boolean getEstado() { 
        return estado; 
    }

    public void setEstado(Boolean estado) { 
        this.estado = estado; 
    }
}
