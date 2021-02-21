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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whattodo.CreateEventActivity;
import com.example.whattodo.LoginActivity;
import com.example.whattodo.NearestEventActivity;
import com.example.whattodo.OnGetDataListener;
import com.example.whattodo.R;
import com.example.whattodo.RegisterActivity;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.model.Database;
import com.example.whattodo.model.Evento;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.TicketsListActivity;
import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
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
    Database databaseClass;
    //Adapter
    RecyclerView recycler;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    Context context;
    //Header
    View header;
    TextView headerText;
    //PopUp - filtros
    TextView filtros;
    Dialog myDialog1;
    ArrayList<String> datosOrg, mailsOrg, orgKeys;
    //Strgin nombre
    String nombreUsuario, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_participant);
        myDialog1 = new Dialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseClass = new Database();

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

        filtros = (TextView) findViewById(R.id.filter_text);

        filtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ShowPopup(view);
            }
        });

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

    public void ShowPopup(View v) {
        TextView txt_cerrar;
        Spinner fechas, organizadores;

        myDialog1.setContentView(R.layout.pop_up_filter);
        txt_cerrar =(TextView) myDialog1.findViewById(R.id.txt_cerrar);
        fechas = (Spinner) myDialog1.findViewById(R.id.spinner_fecha);
        organizadores = (Spinner) myDialog1.findViewById(R.id.spinner_org);

        String[] datosF = new String[] {"Ascendente", "Descendente"};
        ArrayAdapter<String> adaptadorF = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datosF);
        adaptadorF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fechas.setAdapter(adaptadorF);

        //Obtenemos los organizadores de la bdd

        databaseClass.mReadDataOnce("Users", new OnGetDataListener() {
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
                datosOrg = new ArrayList<>();
                mailsOrg= new ArrayList<>();
                orgKeys= new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    String esAsistente = snapshot1.child("esAsistente").getValue().toString();
                    if (esAsistente.equals("false")) {
                        String nombreOrg = snapshot1.child("name").getValue().toString();
                        String email = snapshot1.child("email").getValue().toString();
                        String key = snapshot1.getKey();
                        datosOrg.add(nombreOrg);
                        mailsOrg.add(email);
                        orgKeys.add(key);

                    }
                }
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                //extraemos de las listas los organizadores que no tengan eventos
                ArrayList<String> idsOrgEventos = new ArrayList<>();
                for(int m=0;m<eventos.size();m++){
                    idsOrgEventos.add(eventos.get(m).getIdOrganizador());
                }

                for (int n=0;n<orgKeys.size();n++){
                    if(!idsOrgEventos.contains(orgKeys.get(n))){ //ver,  puede haber error de logica
                        datosOrg.remove(n);
                        mailsOrg.remove(n);
                        orgKeys.remove(n);
                    }
                }
               String [] datosO = datosOrg.toArray(new String[datosOrg.size()]);
                ArrayAdapter<String> adaptadorO = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, datosO);
                adaptadorO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                organizadores.setAdapter(adaptadorO);

            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


        txt_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog1.dismiss();
            }
        });
        myDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog1.show();

        //Seteamos el comportamiento de los spinners
        String as= "Ascendente";
        String des="Descendente";
        fechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                /*if (i==0) {

                }
                Participante p = new Participante();
                p.setNombre(nombreUsuario);
                p.setId(idUsuario);
                SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(context), p);
                recycler.setAdapter(adapter);

                myDialog1.dismiss();
            */
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        organizadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String keyOrg = orgKeys.get(i);


                ArrayList<Evento> eventosFiltrados = new ArrayList<>();

                for (int w=0; w<eventos.size(); w++){
                    if(eventos.get(w).getIdOrganizador().equals(keyOrg)){
                        eventosFiltrados.add(eventos.get(w));
                    }
                }


                Participante p = new Participante();
                p.setNombre(nombreUsuario);
                p.setId(idUsuario);
                SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventosFiltrados, new Dialog(context), p);
                recycler.setAdapter(adapter);

                //myDialog1.dismiss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}