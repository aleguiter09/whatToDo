package com.example.whattodo.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


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
    FirebaseAuth firebaseAuth;
    View header;
    TextView headerText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_participant);

        firebaseAuth = FirebaseAuth.getInstance();
        participantToolbar = findViewById(R.id.toolbarParticipant);
        menuDrawerLayoutParticipant = findViewById(R.id.menuDrawerLayoutParticipant);
        menuNavigationViewParticipant = findViewById(R.id.menuNavigationViewParticipant);

        header = menuNavigationViewParticipant.getHeaderView(0);
        headerText = (TextView) header.findViewById(R.id.headerText);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayoutParticipant, participantToolbar, 0, 0);
        menuDrawerLayoutParticipant.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationViewParticipant.setNavigationItemSelectedListener(this);

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

}