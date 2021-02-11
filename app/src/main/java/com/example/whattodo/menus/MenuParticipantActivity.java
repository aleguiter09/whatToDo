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

import com.example.whattodo.LoginActivity;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.google.android.material.navigation.NavigationView;

public class MenuParticipantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayoutParticipant;
    NavigationView menuNavigationViewParticipant;
    Toolbar participantToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_participant);

        participantToolbar = findViewById(R.id.toolbarParticipant);
        menuDrawerLayoutParticipant = findViewById(R.id.menuDrawerLayoutParticipant);
        menuNavigationViewParticipant = findViewById(R.id.menuNavigationViewParticipant);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayoutParticipant, participantToolbar, 0, 0);
        menuDrawerLayoutParticipant.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationViewParticipant.setNavigationItemSelectedListener(this);
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
                Intent registrarse = new Intent(this, RegisterActivity.class);
                startActivity(registrarse);
                return true;
            case R.id.entradas:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            case R.id.eventosPasados:
                finishAffinity();
                return true;
            case R.id.configuracion:
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