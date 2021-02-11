package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    Toolbar registerToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerToolbar = findViewById(R.id.toolbarRegister);
        setSupportActionBar(registerToolbar);

    }

    public void onClick(View view) {
        Intent iniciarSesion = new Intent(this, LoginActivity.class);
        startActivity(iniciarSesion);
        Log.i("Click", "Iniciar sesion");
    }
}