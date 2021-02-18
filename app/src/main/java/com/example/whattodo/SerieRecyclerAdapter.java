package com.example.whattodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.example.whattodo.model.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class SerieRecyclerAdapter extends RecyclerView.Adapter<SerieRecyclerAdapter.EventoViewHolder> {
public class SerieRecyclerAdapter extends RecyclerView.Adapter<SerieRecyclerAdapter.EventoViewHolder> {

    ArrayList<String> listaNombres, listaDescripciones, listaInicioEvento, listaFinEvento, listaFechas, listaUbicaciones, listaLatitudes, listaLongitudes;
    Dialog myDialog;
    Context context;
    static final int REQUEST_CODE_MAPS = 111;
        ArrayList<String> listaNombres, listaDescripciones, listaInicioEvento, listaFinEvento, listaFechas, listaUbicaciones;
        ArrayList<Evento> listaEventos;
        Participante participante;
        Dialog myDialog;

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
        DatabaseReference databaseReference;

        Context context;

        public SerieRecyclerAdapter(ArrayList<Evento> events, Dialog dialog, Participante p) {
            participante = p;
            listaEventos = events;
            listaNombres = new ArrayList<String>();
            listaDescripciones = new ArrayList<String>();
            listaInicioEvento = new ArrayList<String>();
            listaFinEvento = new ArrayList<String>();
            listaFechas = new ArrayList<String>();
            listaUbicaciones = new ArrayList<String>();
            myDialog = dialog;
            context = myDialog.getContext();

            databaseReference = FirebaseDatabase.getInstance().getReference();

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
        public void onBindViewHolder (@NonNull EventoViewHolder holder, int position) {
            holder.asignarDatos(listaEventos.get(position), listaNombres.get(position), listaDescripciones.get(position), listaInicioEvento.get(position), listaFinEvento.get(position), listaFechas.get(position), listaUbicaciones.get(position));
        }

    @Override
    public int getItemCount() { return listaNombres.size();}
    public class EventoViewHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        TextView nombreTV, fecha_horarioTV, descripcionTV;
        Button btnVerDescripcion;
        @Override
        public int getItemCount() { return listaNombres.size();}

        public class EventoViewHolder extends RecyclerView.ViewHolder{
            ImageView icono;
            TextView nombreTV, fecha_horarioTV, descripcionTV;
            Button btnVerDescripcion, btnAsistir;

        EventoViewHolder (@NonNull View base){
            super(base);
            icono= (ImageView) base.findViewById(R.id.eventImage);
            nombreTV = (TextView) base.findViewById(R.id.nombreEvento);
            fecha_horarioTV = (TextView) base.findViewById(R.id.fecha_horario);
            descripcionTV = (TextView) base.findViewById(R.id.descripcion_pop_up);
            btnVerDescripcion = (Button) base.findViewById(R.id.verMas);
        }
            EventoViewHolder (@NonNull View base){
                super(base);
                icono = (ImageView) base.findViewById(R.id.eventImage);
                nombreTV = (TextView) base.findViewById(R.id.nombreEvento);
                fecha_horarioTV = (TextView) base.findViewById(R.id.fecha_horario);
                descripcionTV = (TextView) base.findViewById(R.id.descripcion_pop_up);
                btnVerDescripcion = (Button) base.findViewById(R.id.verMas);
                btnAsistir = (Button) base.findViewById(R.id.btnAsistir);
            }

        public void asignarDatos(String nombreEvento, String descripcion, String inicioEvento, String finEvento, String fecha, String ubicacion, String latitud, String longitud){
            nombreTV.setText(nombreEvento);
            fecha_horarioTV.setText(fecha + " - " + inicioEvento + "hs a "+finEvento+"hs");
            public void asignarDatos(Evento evento, String nombreEvento, String descripcion, String inicioEvento, String finEvento, String fecha, String ubicacion){
                nombreTV.setText(nombreEvento);
                fecha_horarioTV.setText(fecha + " - " + inicioEvento + "hs a "+ finEvento + "hs");

            btnVerDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowPopup(view, descripcion, ubicacion, latitud, longitud);
                }
            });
        }
                btnVerDescripcion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowPopup(view, descripcion, ubicacion);
                    }
                });

                btnAsistir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("¿Desea solicitar un ticket para este evento?")
                                .setCancelable(true)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        guardarTicket(evento);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog confirmar = alert.create();
                        confirmar.setTitle("Confirmar asistencia");
                        confirmar.show();
                    }
                });
            }

            public void guardarTicket(Evento evento) {
                Ticket t = new Ticket(participante, evento);

                Map<String, Object> map = new HashMap<>();
                map.put("idParticipante", t.getParticipante().getId());
                map.put("participante", t.getParticipante().getNombre());
                map.put("idEvento", t.getEvento().getIdEvento());
                map.put("evento", t.getEvento().getNombre());

                databaseReference.child("Tickets").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Tu ticket ha sido generado con éxito!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "No se ha logrado generar el ticket, intente nuevamente", Toast.LENGTH_SHORT).show();
                        }
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
            ubicacionPopUp.setText(Html.fromHtml("Ubicación: <u>"+ubicacion+"</u>"));

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
                txt_cerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                ubicacionPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }



}

