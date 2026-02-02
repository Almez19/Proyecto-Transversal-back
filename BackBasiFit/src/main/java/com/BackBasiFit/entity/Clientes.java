package com.BackBasiFit.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name= "clientes")
public class Clientes {
    @Id
    
    private String id = UUID.randomUUID().toString();

    @Column(nullable= false, length= 50)
    String nombre;

    @Column(nullable= false, length= 50)
    String apellido1;
    

    @Column(nullable= false, length= 50)
    String apellido2;

    @Column(nullable= false, length= 9)
    String DNI_NIE;

    @Column(nullable= false)
    boolean estado;

    @Column(nullable= false)
    String usuarios;

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

    public String getDNI_NIE() {
        return DNI_NIE;
    }

    public void setDNI_NIE(String dNI_NIE) {
        DNI_NIE = dNI_NIE;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    








}
