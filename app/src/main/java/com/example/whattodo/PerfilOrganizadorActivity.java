package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilOrganizadorActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String idOrganizador, nombreOrganizador;
    DatabaseReference databaseReference;
    TextView nombreOrganizadorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_organizador);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();

        idOrganizador = extras.getString("idOrganizador");

        getNombreOrganizador();

        viewPager = findViewById(R.id.view_pager);
        setUpViewPager(viewPager, this);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setUpViewPager(ViewPager viewPager, Context context) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventosOrganizadorFragmento(idOrganizador), "Eventos");
        adapter.addFragment(new SobreOrganizadorFragmento(idOrganizador), "Opiniones");
        viewPager.setAdapter(adapter);
    }

    public void getNombreOrganizador(){
        databaseReference.child("Users").child(idOrganizador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) nombreOrganizador = snapshot.child("name").getValue().toString();

                nombreOrganizadorTV = (TextView) findViewById(R.id.nombreOrganizadorPerfil);
                nombreOrganizadorTV.setText(nombreOrganizador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}