package com.example.whattodo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whattodo.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SobreOrganizadorFragmento extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    DatabaseReference databaseReference;
    RecyclerView recycler;
    PuntuacionAdapter adapter;
    ArrayList<String> listaNombres = new ArrayList<String>();
    ArrayList<String> listaPuntuaciones = new ArrayList<String>();
    ArrayList<String> listaComentarios = new ArrayList<String>();
    String idOrganizador;

    public SobreOrganizadorFragmento() {
    }

    public SobreOrganizadorFragmento(String idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public static SobreOrganizadorFragmento newInstance(String param1, String param2) {
        SobreOrganizadorFragmento fragment = new SobreOrganizadorFragmento();
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

        View vista = inflater.inflate(R.layout.fragment_sobre_organizador_fragmento, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        generarCardViews(vista);

        return vista;
    }

    private void generarCardViews(View vista) {
        databaseReference.child("Puntuacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                            String nombreUsuario = ds.child("nombreUsuario").getValue().toString();
                            String puntuacion = ds.child("puntuacion").getValue().toString();
                            String comentario = ds.child("comentario").getValue().toString();

                            listaNombres.add(nombreUsuario);
                            listaPuntuaciones.add(puntuacion);
                            listaComentarios.add(comentario);
                    }
                }

                recycler = (RecyclerView) vista.findViewById(R.id.recyclerPuntuacionPerfil);
                recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new PuntuacionAdapter(listaNombres, listaPuntuaciones, listaComentarios);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}