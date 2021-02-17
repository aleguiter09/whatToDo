package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.example.whattodo.model.Ticket;

import java.util.ArrayList;

public class TicketsListActivity extends AppCompatActivity {

    Toolbar toolbarTicketList;
    RecyclerView recycler;
    ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        toolbarTicketList = findViewById(R.id.toolbarTicketList);
        setSupportActionBar(toolbarTicketList);

        recycler = (RecyclerView) findViewById(R.id.recyclerTicket);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Participante p1 = new Participante();
        Participante p2 = new Participante();
        Participante p3 = new Participante();
        Evento e1 = new Evento("Teatro", "", "", "", "", "", "", "", "");
        Evento e2 = new Evento("Cine", "", "", "", "", "", "", "", "");
        Evento e3 = new Evento("Broadway", "", "", "", "", "", "", "", "");
        Ticket t1 = new Ticket(p1, e1);
        Ticket t2 = new Ticket(p2, e2);
        Ticket t3 = new Ticket(p3, e3);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);

        TicketRecyclerAdapter adapter = new TicketRecyclerAdapter(tickets);
        recycler.setAdapter(adapter);
    }
}