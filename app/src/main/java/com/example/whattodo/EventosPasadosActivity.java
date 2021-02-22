package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.example.whattodo.model.Database;
import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.example.whattodo.model.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventosPasadosActivity extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    ArrayList<String> idEventos = new ArrayList<String>();
    Database databaseClass;
    FirebaseAuth firebaseAuth;
    Context context;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_pasados);

        getSupportActionBar().setTitle("Eventos pasados");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        recycler = (RecyclerView) findViewById(R.id.recyclerEventosPasados);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        databaseClass = new Database();
        firebaseAuth = FirebaseAuth.getInstance();

        id = firebaseAuth.getCurrentUser().getUid();

        getIdEventos();
        getEventsFromFirebase();

    }

    public void getIdEventos(){
        databaseClass.mReadDataOnce("Tickets", new OnGetDataListener() {
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
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.child("idParticipante").getValue().toString().equals(id)){
                            String idEvento = ds.child("idEvento").getValue().toString();

                            idEventos.add(idEvento);
                        }
                    }
                }
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {}
        });
    }

    public void getEventsFromFirebase(){
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
            public void onSuccess(DataSnapshot snapshot) throws ParseException {
                if (snapshot.exists()) {

                    for(int i=0; i<idEventos.size(); i++){

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            if(idEventos.get(i).equals(ds.getKey().toString()) && yaAsisitio(ds.child("fechaEvento").getValue().toString())){

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
                        }
                    }

                    System.out.println("EVENTOSSS: "+eventos);
                    SerieRecyclerAdapter adapter = new SerieRecyclerAdapter(eventos, new Dialog(context), null);
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

    public boolean yaAsisitio(String fechaEvento) throws ParseException {

        boolean retorno = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");

        Calendar calendarHoy = Calendar.getInstance();

        String dia = String.valueOf(calendarHoy.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(calendarHoy.get(Calendar.MONTH));
        String anio = String.valueOf(calendarHoy.get(Calendar.YEAR));

        Date hoy = dateFormat.parse(dia + "/" + mes + "/" + anio);
        Date dateEvento = dateFormat.parse(fechaEvento);
        System.out.println("Date-1: " + dateFormat.format(hoy));
        System.out.println("Date-2: " + dateFormat.format(dateEvento));

        if(hoy.before(dateEvento))  retorno = false;
        else retorno = true;

        return retorno;
    }

}