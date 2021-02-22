package com.example.whattodo.menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.whattodo.Notificaciones.Utils;
import com.example.whattodo.R;

import java.util.Calendar;

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