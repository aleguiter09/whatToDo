package com.example.whattodo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.whattodo.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventosOrganizadorFragmento extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    DatabaseReference databaseReference;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    RecyclerView recycler;
    FirebaseAuth firebaseAuth;
    SerieRecyclerAdapter adapter;

    public EventosOrganizadorFragmento() {
    }

    public static EventosOrganizadorFragmento newInstance(String param1, String param2) {
        EventosOrganizadorFragmento fragment = new EventosOrganizadorFragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_eventos_organizador_fragmento, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getEventosOrganizador(vista);

        return vista;
    }

    public void getEventosOrganizador(View vista){
        String id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        if(ds.child("idOrganizador").getValue().toString().equals(id)){
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

                System.out.println(id);
                System.out.println("SIZEEEE " +eventos.size());

                recycler = (RecyclerView) vista.findViewById(R.id.recyclerEventosPerfil);
                recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new SerieRecyclerAdapter(eventos, new Dialog(getContext()), getContext());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}