package com.example.proyectodpt.clases;

import android.widget.Toast;

import com.google.firebase.database.Exclude;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Usuario {
    String nombre ;
    String apellido;
    String email;
    String nombrePersona;
    String numeroPersona;
    String clave;

    //public Map<String, Boolean> stars = new HashMap<>();


    public Usuario(String nombre, String apellido, String email, String nombrePersona, String numeroPersona) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.nombrePersona = nombrePersona;
        this.numeroPersona = numeroPersona;
    }

    public String getClave() { return clave; }

    public void setClave(String clave) { this.clave = clave; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }


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
