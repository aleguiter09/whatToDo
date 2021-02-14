package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Toolbar registerToolbar;
    EditText name, password, email;
    Button btnRegistrarse;
    RadioGroup radioGroup;
    RadioButton asistente, organizador;
    Context context;
    String name_str;
    String password_str;
    String email_str;
    Boolean asistente_bool;
    Boolean organizador_bool;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        registerToolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(registerToolbar);

        name = (EditText) findViewById(R.id.editTextName);
        password = (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextEmailAddress);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        asistente = (RadioButton) findViewById(R.id.asistente);
        organizador = (RadioButton) findViewById(R.id.organizador);

        context = this;

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_str = name.getText().toString();
                password_str = password.getText().toString();
                email_str = email.getText().toString();
                asistente_bool = asistente.isChecked();
                organizador_bool = organizador.isChecked();

                if(!name_str.isEmpty() && !password_str.isEmpty() && !email_str.isEmpty() && (asistente_bool || organizador_bool)){

                    if(password_str.length() >= 6) registrarUsuario();
                    else password.setError("Contraseña debe tener al menos 6 caracteres");

                }else{
                    name.setError("Nombre es un campo obligatorio");
                    password.setError("Contraseña es un campo obligatorio");
                    email.setError("E-mail es un campo obligatorio");
                    if(asistente_bool && organizador_bool){
                        Toast.makeText(context, "Debe seleccionar si es asistente u organizador", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    public void onClick(View view) {
        Intent iniciarSesion = new Intent(this, LoginActivity.class);
        startActivity(iniciarSesion);
        Log.i("Click", "Iniciar sesion");
    }

    private void registrarUsuario(){

        firebaseAuth.createUserWithEmailAndPassword(email_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name_str);
                    map.put("password", password_str);
                    map.put("email", email_str);
                    map.put("esAsistente", asistente_bool);

                    String id = firebaseAuth.getCurrentUser().getUid();

                    databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                name.setText(null);
                                password.setText(null);
                                email.setText(null);
                                radioGroup.clearCheck();
                                Toast.makeText(context, "El usuario ha sido registrado con éxito!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else Toast.makeText(context, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }


}