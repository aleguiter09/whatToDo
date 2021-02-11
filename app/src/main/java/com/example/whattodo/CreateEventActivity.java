package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class CreateEventActivity extends AppCompatActivity {

    Toolbar createEventToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventToolbar = findViewById(R.id.createEventToolbar);
        setSupportActionBar(createEventToolbar);
    }


}