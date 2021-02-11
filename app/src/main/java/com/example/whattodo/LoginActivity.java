package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.whattodo.menus.MenuOrganizerActivity;
import com.example.whattodo.menus.MenuParticipantActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    Toolbar loginToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
         btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 iniciarSesion();
             }
         });

        loginToolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(loginToolbar);
    }

    private void iniciarSesion() {
        Intent iniciarHome = new Intent(this, MenuOrganizerActivity.class);
        startActivity(iniciarHome);
        Log.i("Click btn", "Has apretado Iniciar Sesion");
    }

    public void onClickRegistrarse(View view) {
        Intent iniciarRegistro = new Intent(this, RegisterActivity.class);
        startActivity(iniciarRegistro);
        Log.i("Click", "Has apretado el text view Registrarse");
    }
}