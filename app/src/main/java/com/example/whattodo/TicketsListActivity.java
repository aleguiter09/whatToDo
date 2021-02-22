package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.example.whattodo.model.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TicketsListActivity extends AppCompatActivity {

    Toolbar toolbarTicketList;
    RecyclerView recycler;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    Context context;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        context = this;

        toolbarTicketList = findViewById(R.id.toolbarTicketList);
        setSupportActionBar(toolbarTicketList);

        recycler = (RecyclerView) findViewById(R.id.recyclerTicket);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        getTicketsFromFirebase();
    }

    private void getTicketsFromFirebase() {
        databaseReference.child("Tickets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot ds: snapshot.getChildren()) {
                        String idUsuario = ds.child("idParticipante").getValue().toString();
                        if(idUsuario.equals(firebaseAuth.getCurrentUser().getUid())) {
                            String nombreEvento = ds.child("evento").getValue().toString();
                            String nombreParticipante = ds.child("participante").getValue().toString();
                            Evento e = new Evento();
                            e.setNombre(nombreEvento);
                            Participante p = new Participante();
                            p.setNombre(nombreParticipante);
                            Ticket t = new Ticket(p, e);
                            tickets.add(t);
                        }
                    }

                    TicketRecyclerAdapter adapter = new TicketRecyclerAdapter(tickets, settings, context);
                    recycler.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}