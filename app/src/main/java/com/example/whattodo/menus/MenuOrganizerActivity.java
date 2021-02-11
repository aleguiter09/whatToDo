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

import com.example.whattodo.CreateEventActivity;
import com.example.whattodo.LoginActivity;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.google.android.material.navigation.NavigationView;

public class MenuOrganizerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayoutOrganizer;
    NavigationView menuNavigationViewOrganizer;
    Toolbar organizerToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

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