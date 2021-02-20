package com.example.whattodo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whattodo.model.Ticket;
import java.util.ArrayList;

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.TicketViewHolder> {

    ArrayList<String> listaNombres;

    public TicketRecyclerAdapter(ArrayList<Ticket> tickets) {
        listaNombres = new ArrayList<String>();

        for(int i=0; i<tickets.size(); i++) {
            listaNombres.add(tickets.get(i).getEvento().getNombre());
        }
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.asignarDatos(listaNombres.get(position));
    }

    @Override
    public int getItemCount() {
        return listaNombres.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView nombre;
        Button btnVerEvento;

        public TicketViewHolder(@NonNull View base) {
            super(base);
            icono = (ImageView) base.findViewById(R.id.imageEventTicket);
            nombre = (TextView) base.findViewById(R.id.eventName);
            btnVerEvento = (Button) base.findViewById(R.id.verEventoBtn);
        }

        public void asignarDatos(String nombreEvento) {
            nombre.setText(nombreEvento);
            btnVerEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Button", "Apretaste ver evento");
                }
            });
        }
    }
}
