package com.example.whattodo.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.whattodo.CreateEventActivity;
import com.example.whattodo.LoginActivity;
import com.example.whattodo.NearestEventActivity;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.model.Evento;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.TicketsListActivity;
import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuParticipantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayoutParticipant;
    NavigationView menuNavigationViewParticipant;
    Toolbar participantToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    //Adapter
    RecyclerView recycler;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    Context context;
    //Header
    View header;
    TextView headerText;



    //Strgin nombre
    String nombreUsuario, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_participant);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        context = this;

        participantToolbar = findViewById(R.id.toolbarParticipant);
        menuDrawerLayoutParticipant = findViewById(R.id.menuDrawerLayoutParticipant);
        menuNavigationViewParticipant = findViewById(R.id.menuNavigationViewParticipant);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayoutParticipant, participantToolbar, 0, 0);
        menuDrawerLayoutParticipant.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationViewParticipant.setNavigationItemSelectedListener(this);

        header = menuNavigationViewParticipant.getHeaderView(0);
        headerText = (TextView) header.findViewById(R.id.headerText);

        getUserInfo();

        recycler = (RecyclerView) findViewById(R.id.recyclerEventParticipant);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getEventsFromFirebase();
        String valor = getIntent().getStringExtra("usuario");
        headerText.setText("Hola, " + valor + "!");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_participant, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscarEventos:
                return true;
            case R.id.entradas:
                Intent verTickets = new Intent(MenuParticipantActivity.this, TicketsListActivity.class);
                startActivity(verTickets);
                return true;
            case R.id.eventosPasados:
                return true;
            case R.id.eventosCercanos:
                Intent nearEvent = new Intent(this, NearestEventActivity.class);
                startActivity(nearEvent);
                return true;
            case R.id.configuracion:
                return true;
            case R.id.cerrarSesion:
                firebaseAuth.signOut();
                Intent volverAlInicio = new Intent(MenuParticipantActivity.this, MenuActivity.class);
                startActivity(volverAlInicio);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getEventsFromFirebase(){
        databaseReference.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String nombreEvento = ds.child("nombreEvento").getValue().toString();
                        String descripcion = ds.child("descripcion").getValue().toString();
                        String inicioEvento = ds.child("inicioEvento").getValue().toString();
                        String finEvento = ds.child("finEvento").getValue().toString();
                        String fechaEvento = ds.child("fechaEvento").getValue().toString();
                        String ubicacion = ds.child("ubicacion").getValue().toString();
                        String latitud = ds.child("latitud").getValue().toString();
                        String longitud = ds.child("longitud").getValue().toString();
                        String idOrganizador = ds.child("idOrganizador").getValue().toString();
                        String idEvento = ds.getKey().toString();

                        Evento e = new Evento(nombreEvento, descripcion, inicioEvento, finEvento, fechaEvento, idOrganizador, ubicacion, latitud, longitud);
                        e.setIdEvento(idEvento);
                        eventos.add(e);
                    }

                    Participante p = new Participante();
                    p.setNombre(nombreUsuario);
                    p.setId(idUsuario);

                    SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(context), p);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void getUserInfo() {
        String id = firebaseAuth.getCurrentUser().getUid();
        idUsuario = id;
        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    nombreUsuario = (String) snapshot.child("name").getValue();
                    headerText.setText("Hola, " + nombreUsuario +"!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

}