package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class PerfilOrganizadorActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_organizador);

        viewPager = findViewById(R.id.view_pager);
        setUpViewPager(viewPager, this);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setUpViewPager(ViewPager viewPager, Context context) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventosOrganizadorFragmento(), "Eventos");
        adapter.addFragment(new SobreOrganizadorFragmento(), "Opiniones");
        viewPager.setAdapter(adapter);
    }
}