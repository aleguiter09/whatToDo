package com.example.whattodo.model;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String nombre, descripcion, horarioInicio, horarioFin, fecha;
    private String idOrganizador;
    private String idEvento;
    private String ubicacion, latitud, longitud;
    private Organizador organizador;
    private ArrayList<Participante> participantes;

    public Evento(String nombre, String descripcion, String horarioInicio, String horarioFin, String fecha, String idOrganizador, String ubicacion, String latitud, String longitud, Organizador organizador, ArrayList<Participante> participantes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.fecha = fecha;
        this.idOrganizador = idOrganizador;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.organizador = organizador;
        this.participantes = participantes;
    }

    //este es sin organizador y sin participantes
    public Evento(String nombre, String descripcion, String horarioInicio, String horarioFin, String fecha, String idOrganizador, String ubicacion, String latitud, String longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.fecha = fecha;
        this.idOrganizador = idOrganizador;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Evento() {

    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getIdOrganizador() {
        return idOrganizador;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLontitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdOrganizador(String idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLontitud(String longitud) {
        this.longitud = longitud;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public void setParticipantes(ArrayList<Participante> participantes) {
        this.participantes = participantes;
    }
}
