package com.example.whattodo.model;

public class Ticket {

    private Participante participante;
    private Evento evento;

    public Ticket(Participante participante, Evento evento) {
        this.evento = evento;
        this.participante = participante;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
