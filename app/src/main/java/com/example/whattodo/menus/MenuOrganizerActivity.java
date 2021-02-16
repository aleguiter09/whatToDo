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

import com.example.whattodo.CreateEventActivity;
import com.example.whattodo.LoginActivity;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.model.Evento;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MenuOrganizerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayoutOrganizer;
    NavigationView menuNavigationViewOrganizer;
    Toolbar organizerToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    SerieRecyclerAdapter adapter;
    RecyclerView recycler;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_organizer);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        context = this;

        organizerToolbar = findViewById(R.id.toolbarOrganizer);
        menuDrawerLayoutOrganizer = findViewById(R.id.menuDrawerLayoutOrganizer);
        menuNavigationViewOrganizer = findViewById(R.id.menuNavigationViewOrganizer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayoutOrganizer, organizerToolbar, 0, 0);
        menuDrawerLayoutOrganizer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationViewOrganizer.setNavigationItemSelectedListener(this);


        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getEventsFromFirebase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_organizer, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crearEvento:
                Intent createEvent = new Intent(this, CreateEventActivity.class);
                startActivity(createEvent);
                return true;
            case R.id.eventosActuales:
                return true;
            case R.id.eventosPasadosOrganizer:
                return true;
            case R.id.configuracionOrganizer:
                return true;
            case R.id.cerrarSesion:
                firebaseAuth.signOut();
                Intent volverAlInicio = new Intent(MenuOrganizerActivity.this, LoginActivity.class);
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


                        Evento e = new Evento(nombreEvento, descripcion, inicioEvento, finEvento, fechaEvento, idOrganizador, ubicacion, latitud, longitud);
                        eventos.add(e);
                    }

                    SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(context));
                    recycler.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}