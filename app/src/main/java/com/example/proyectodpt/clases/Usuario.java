package com.example.proyectodpt.clases;

import java.sql.Date;

public class Usuario {
    String nombre ;
    String email;
    String nombrePersona;
    String numeroPersona;

    public Usuario(String nombre, String email, String nombrePersona, String numeroPersona) {
        this.nombre = nombre;
        this.email = email;
        this.nombrePersona = nombrePersona;
        this.numeroPersona = numeroPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getNumeroPersona() {
        return numeroPersona;
    }

    public void setNumeroPersona(String numeroPersona) {
        this.numeroPersona = numeroPersona;
    }
}
