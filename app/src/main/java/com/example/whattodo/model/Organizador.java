package com.example.whattodo.model;

import java.util.ArrayList;

public class Organizador {

    private String nombre, contrasenia, email;
    private ArrayList<Evento> eventosOrganizados;


    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEventosOrganizados(ArrayList<Evento> eventosOrganizados) {
        this.eventosOrganizados = eventosOrganizados;
    }
}
