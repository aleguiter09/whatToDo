package com.example.whattodo.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.whattodo.R;

public class ParticipantHomeActivity extends AppCompatActivity {

    Toolbar participantToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_home);

        participantToolbar = findViewById(R.id.toolbarParticipant);
        setSupportActionBar(participantToolbar);
    }
}