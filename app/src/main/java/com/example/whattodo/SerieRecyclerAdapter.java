package com.example.whattodo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    public class SerieRecyclerAdapter extends RecyclerView.Adapter<SerieRecyclerAdapter.EventoViewHolder> {

        ArrayList<String> listaNombres, listaHorarios, listaDescripciones, listaFechas;
        Dialog myDialog;


        public SerieRecyclerAdapter(ArrayList<Evento> events, Dialog dialog) {
            listaNombres = new ArrayList<String>();
            listaHorarios =  new ArrayList<String>();
            listaDescripciones = new ArrayList<String>();
            listaFechas = new ArrayList<String>();
            myDialog = dialog;
            for(int i=0; i<events.size(); i++){

                listaNombres.add(events.get(i).getNombre());
                listaHorarios.add(events.get(i).getHorarioInicio());
                listaDescripciones.add(events.get(i).getDescripcion());
                listaFechas.add(events.get(i).getFechaInicio().toString());
            }



        }

        @Override
        public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_evento, null, false);
            return new EventoViewHolder(view);
        }

        @Override
        public void onBindViewHolder (@NonNull EventoViewHolder holder, int position) {
            holder.asignarDatos(listaNombres.get(position), listaHorarios.get(position), listaFechas.get(position), listaDescripciones.get(position));
        }

        @Override
        public int getItemCount() { return listaNombres.size();}
        public class EventoViewHolder extends RecyclerView.ViewHolder{
            ImageView icono;
            TextView nombre, fecha_horario, descripcion;
            Button verDescripcion;

            EventoViewHolder (@NonNull View base){
                super(base);
                this.icono= (ImageView) base.findViewById(R.id.eventImage);
                this.nombre= (TextView) base.findViewById(R.id.nombreEvento);
                this.fecha_horario= (TextView) base.findViewById(R.id.fecha_horario);
                this.descripcion = (TextView) base.findViewById(R.id.descripcion_pop_up);
                this.verDescripcion = (Button) base.findViewById(R.id.verMas);
            }
            public void asignarDatos(String nombr, String hora, String fecha, String descp){
                nombre.setText(nombr);
                fecha_horario.setText(fecha.substring(0,10) + " - " + hora);
                //descripcion.setText(descp);

                verDescripcion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowPopup(view, descp);
                    }
                });
            }

            public void ShowPopup(View v, String descripciones) {
                TextView txt_cerrar, descripcion;
                myDialog.setContentView(R.layout.pop_up_descripcion);
                txt_cerrar =(TextView) myDialog.findViewById(R.id.txt_cerrar);
                descripcion = (TextView) myDialog.findViewById(R.id.descripcion_pop_up);
               descripcion.setText(descripciones);
                txt_cerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        }

    }

