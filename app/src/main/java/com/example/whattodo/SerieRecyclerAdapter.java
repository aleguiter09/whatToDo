package com.example.whattodo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whattodo.model.Evento;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class SerieRecyclerAdapter extends RecyclerView.Adapter<SerieRecyclerAdapter.EventoViewHolder> {

    ArrayList<String> listaNombres, listaDescripciones, listaInicioEvento, listaFinEvento, listaFechas, listaUbicaciones, listaLatitudes, listaLongitudes;
    Dialog myDialog;
    Context context;
    static final int REQUEST_CODE_MAPS = 111;

    public SerieRecyclerAdapter(ArrayList<Evento> events, Dialog dialog, Context context) {
        listaNombres = new ArrayList<String>();
        listaDescripciones = new ArrayList<String>();
        listaInicioEvento = new ArrayList<String>();
        listaFinEvento = new ArrayList<String>();
        listaFechas = new ArrayList<String>();
        listaUbicaciones = new ArrayList<String>();
        listaLatitudes = new ArrayList<String>();
        listaLongitudes = new ArrayList<String>();
        myDialog = dialog;
        this.context = context;

        for(int i=0; i<events.size(); i++){
            listaNombres.add(events.get(i).getNombre());
            listaDescripciones.add(events.get(i).getDescripcion());
            listaInicioEvento.add(events.get(i).getHorarioInicio());
            listaFinEvento.add(events.get(i).getHorarioFin());
            listaFechas.add(events.get(i).getFecha());
            listaUbicaciones.add(events.get(i).getUbicacion());
            listaLatitudes.add(events.get(i).getLatitud());
            listaLongitudes.add(events.get(i).getLontitud());
        }
    }

    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_evento, null, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull EventoViewHolder holder, int position) {
        holder.asignarDatos(listaNombres.get(position), listaDescripciones.get(position), listaInicioEvento.get(position), listaFinEvento.get(position), listaFechas.get(position), listaUbicaciones.get(position), listaLatitudes.get(position), listaLongitudes.get(position));
    }

    @Override
    public int getItemCount() { return listaNombres.size();}
    public class EventoViewHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        TextView nombreTV, fecha_horarioTV, descripcionTV;
        Button btnVerDescripcion;

        EventoViewHolder (@NonNull View base){
            super(base);
            icono= (ImageView) base.findViewById(R.id.eventImage);
            nombreTV = (TextView) base.findViewById(R.id.nombreEvento);
            fecha_horarioTV = (TextView) base.findViewById(R.id.fecha_horario);
            descripcionTV = (TextView) base.findViewById(R.id.descripcion_pop_up);
            btnVerDescripcion = (Button) base.findViewById(R.id.verMas);
        }

        public void asignarDatos(String nombreEvento, String descripcion, String inicioEvento, String finEvento, String fecha, String ubicacion, String latitud, String longitud){
            nombreTV.setText(nombreEvento);
            fecha_horarioTV.setText(fecha + " - " + inicioEvento + "hs a "+finEvento+"hs");

            btnVerDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowPopup(view, descripcion, ubicacion, latitud, longitud);
                }
            });
        }

        public void ShowPopup(View v, String descripcion, String ubicacion, String latitud, String longitud) {
            TextView txt_cerrar, descripcionPopup, nombreORganizadorPopUp, ubicacionPopUp;

            myDialog.setContentView(R.layout.pop_up_descripcion);

            txt_cerrar = (TextView) myDialog.findViewById(R.id.txt_cerrar);
            descripcionPopup = (TextView) myDialog.findViewById(R.id.descripcion_pop_up);
            ubicacionPopUp = (TextView) myDialog.findViewById(R.id.ubicacionPopUP);

            descripcionPopup.setText("Descripción: "+descripcion);
            ubicacionPopUp.setText("Ubicación: "+ubicacion);

            txt_cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            final Intent ubicacionMapa = new Intent(context, MapsActivityPopUp.class);
            ubicacionMapa.putExtra("latitud", latitud);
            ubicacionMapa.putExtra("longitud", longitud);
            ubicacionMapa.putExtra("ubicación", ubicacion);

            ubicacionPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(context, ubicacionMapa, Bundle.EMPTY);
                 }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }



}

