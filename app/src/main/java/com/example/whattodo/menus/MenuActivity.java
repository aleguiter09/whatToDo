package com.example.whattodo.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.whattodo.LoginActivity;
import com.example.whattodo.OnGetDataListener;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.model.Database;
import com.example.whattodo.model.Evento;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayout;
    NavigationView menuNavigationView;
    Toolbar homeToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //Firebase
    Database databaseClass;
    //Adapter
    RecyclerView recycler;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseClass = new Database();

        context = this;

        homeToolbar = findViewById(R.id.homeToolbar);
        menuDrawerLayout = findViewById(R.id.menuDrawerLayout);
        menuNavigationView = findViewById(R.id.menuNavigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayout, homeToolbar,0 , 0);
        menuDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationView.setNavigationItemSelectedListener(this);

        recycler = (RecyclerView) findViewById(R.id.recyclerEventHome);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getEventsFromFirebase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createAccountOption:
                Intent registrarse = new Intent(this, RegisterActivity.class);
                startActivity(registrarse);
                return true;
            case R.id.loginOption:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            case R.id.finish:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getEventsFromFirebase() {
        databaseClass.mReadDataOnce("Events", new OnGetDataListener() {
            ProgressDialog mProgressDialog = null;
            @Override
            public void onStart() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Cargando..");
                    mProgressDialog.setIndeterminate(true);
                }
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot snapshot) {
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

                    SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(context), null, false);
                    recycler.setAdapter(adapter);
                }
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {}
        });
    }
}