package com.example.whattodo.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.whattodo.R;

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