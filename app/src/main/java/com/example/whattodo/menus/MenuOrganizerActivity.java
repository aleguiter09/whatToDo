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

import java.util.ArrayList;
import java.util.Date;

public class MenuOrganizerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayoutOrganizer;
    NavigationView menuNavigationViewOrganizer;
    Toolbar organizerToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    SerieRecyclerAdapter adapter;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_organizer);

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
        // INGRESO DE DATOS - OBTENER DE LA BDD LOS EVENTOS YA CARGADOS PARA MOSTRAR
        Date inicio = new Date(2020, 06, 11);

        Evento e1 = new Evento("EL FANTASMA DE LA OPERA","Obra de teatro basada en hechos reales.","22:00",inicio);
        Evento e2 = new Evento("TECNOMATE","Tecnologia de todo tipo.","20:00",inicio);
        Evento e3 = new Evento("CIENCIA EN NUESTRO HOGAR","Ciencia dia a dia","23:00",inicio);
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        eventos.add(e1);
        eventos.add(e2);
        eventos.add(e3);

        //INGRESO DE DATOS

        SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(this));
        recycler.setAdapter(adapter);
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
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            case R.id.eventosPasadosOrganizer:
                finishAffinity();
                return true;
            case R.id.configuracionOrganizer:
                finishAffinity();
                return true;
            case R.id.cerrarSesion:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}