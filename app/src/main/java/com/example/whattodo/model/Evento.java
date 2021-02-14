package com.example.whattodo.model;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String nombre, descripcion, horarioInicio, horarioFin;
    private Date fechaInicio, fechaFin;
    private Organizador organizador;
    private ArrayList<Participante> participantes;


    public Evento(String nombre, String descripcion, String horarioInicio, Date fechaInicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioInicio = horarioInicio;
        this.fechaInicio = fechaInicio;
    }

    public Evento(String nombre, String descripcion, String horarioInicio, String horarioFin, Date fechaInicio, Date fechaFin, Organizador organizador, ArrayList<Participante> participantes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.organizador = organizador;
        this.participantes = participantes;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
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

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public void setParticipantes(ArrayList<Participante> participantes) {
        this.participantes = participantes;
    }
}
