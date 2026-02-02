package com.BackBasiFit.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "membresias")
public class Membresias {
    @Id
    
    private String id = UUID.randomUUID().toString();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable= false)
    String fecha_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable= false)
    String fecha_final;

    @Column(nullable= false)
    boolean estado;

    @Column(nullable= false)
    Duracion duracion;

    public enum Duracion{
        diario("diario"),
        semanal("semanal"),
        mensual("mensual"),
        trimestral("trimestral"),
        anual("anual");
    
        private final String duracion;

        Duracion(String duracion){
            this.duracion = duracion;
        }

        public String getDuracion(){
            return duracion;
        }
    }

    @Column(nullable= false)
    Calidad calidad;


    public enum Calidad{
        comfort("comfort"),
        premium("premium"),
        ultimate("ultimate");
    
        private final String calidad;

        Calidad(String calidad){
            this.calidad = calidad;
        }

        public String getCalidad(){
            return calidad;
        }
    }

    @Column(nullable= false)
    Number precio;

    @Column(nullable= false)
    String usuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
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

    public Number getPrecio() {
        return precio;
    }

    public void setPrecio(Number precio) {
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
