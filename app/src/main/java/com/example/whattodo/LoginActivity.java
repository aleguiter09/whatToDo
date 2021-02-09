package com.example.whattodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btnIniciarSesion;

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
    }

    private void iniciarSesion() {
        //Intent iniciarMainScreen = new Intent(this, MainScreenActivity.class);
        //startActivity(iniciarMainScreen);
        Log.i("Click btn", "Has apretado Iniciar Sesion");
    }

    public void onClickRegistrarse(View view) {
        Intent iniciarRegistro = new Intent(this, RegisterActivity.class);
        startActivity(iniciarRegistro);
        Log.i("Click", "Has apretado el text view Registrarse");
    }
}