package com.example.whattodo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PuntuacionAdapter extends RecyclerView.Adapter<PuntuacionAdapter.PuntuacionViewHolder> {

    ArrayList<String> listaNombresUsuarios = new ArrayList<String>();
    ArrayList<String > listaPuntuaciones = new ArrayList<String>();
    ArrayList<String> listaComentarios = new ArrayList<String>();
    ArrayList<String> listaFechaOpinion = new ArrayList<String>();

    public PuntuacionAdapter(ArrayList<String> listaNombresUsuarios, ArrayList<String> listaPuntuaciones, ArrayList<String> listaComentarios, ArrayList<String> listaFechaOpinion) {
        this.listaNombresUsuarios = listaNombresUsuarios;
        this.listaPuntuaciones = listaPuntuaciones;
        this.listaComentarios = listaComentarios;
        this.listaFechaOpinion = listaFechaOpinion;
    }

    @Override
    public PuntuacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_puntuacion_eventos, null, false);
        return new PuntuacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull PuntuacionViewHolder holder, int position) {
        holder.asignarDatos(listaNombresUsuarios.get(position), listaPuntuaciones.get(position), listaComentarios.get(position), listaFechaOpinion.get(position));
    }

    @Override
    public int getItemCount() { return listaNombresUsuarios.size();}
    public class PuntuacionViewHolder extends RecyclerView.ViewHolder{
        TextView nombreUsuarioTV, comentarioTV, fechaOpinionTV;
        RatingBar ratingBar;

        PuntuacionViewHolder (@NonNull View base){
            super(base);
            nombreUsuarioTV = (TextView) base.findViewById(R.id.nombreUsuarioPuntuacion);
            ratingBar = (RatingBar) base.findViewById(R.id.ratingBarPuntuacion);
            comentarioTV = (TextView) base.findViewById(R.id.comentario);
            fechaOpinionTV = (TextView) base.findViewById(R.id.fechaOpinionTV);


        }

        public void asignarDatos(String nombreUsuario, String puntuacion, String comentario, String fechaOpinion){
            nombreUsuarioTV.setText(nombreUsuario);
            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(puntuacion));
            comentarioTV.setText("Comentario: "+comentario);
            fechaOpinionTV.setText("Fecha: " + fechaOpinion);
        }

    }



}