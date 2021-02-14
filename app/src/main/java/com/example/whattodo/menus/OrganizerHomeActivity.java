package com.example.whattodo.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.whattodo.R;
import com.example.whattodo.SerieRecyclerAdapter;
import com.example.whattodo.model.Evento;

import java.util.ArrayList;
import java.util.Date;

public class OrganizerHomeActivity extends AppCompatActivity {

    Toolbar organizerToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_home);

        organizerToolbar = findViewById(R.id.toolbarOrganizer);
        setSupportActionBar(organizerToolbar);


    }
}