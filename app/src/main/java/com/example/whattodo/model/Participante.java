package com.example.whattodo.model;

import java.util.ArrayList;

public class Participante {

    private String id, nombre, contrasenia, email;
    private ArrayList<Evento> eventosFuturos, eventosPasados;
    private ArrayList<Ticket> tickets;

    public Participante() {
    }

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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Evento> getEventosFuturos() {
        return eventosFuturos;
    }

    public void setEventosFuturos(ArrayList<Evento> eventosFuturos) {
        this.eventosFuturos = eventosFuturos;
    }

    public ArrayList<Evento> getEventosPasados() {
        return eventosPasados;
    }

    public void setEventosPasados(ArrayList<Evento> eventosPasados) {
        this.eventosPasados = eventosPasados;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

}
