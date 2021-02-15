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
import android.widget.Toast;

import com.example.whattodo.menus.MenuOrganizerActivity;
import com.example.whattodo.menus.MenuParticipantActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    Toolbar loginToolbar;
    EditText usuario, password;
    String usuario_str, password_str;
    Context context;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    boolean esAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         context = this;
         firebaseAuth = FirebaseAuth.getInstance();
         final FirebaseDatabase database = FirebaseDatabase.getInstance();
         databaseReference = FirebaseDatabase.getInstance().getReference();

         btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
         usuario = (EditText) findViewById(R.id.editTextName);
         password = (EditText) findViewById(R.id.editTextPassword);

         btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 usuario_str = usuario.getText().toString();
                 password_str = password.getText().toString();

                 if(!usuario_str.isEmpty() && !password_str.isEmpty()){
                     loginUser();
                 }else {
                     Toast.makeText(context, "Debe completar los campos para iniciar sesión", Toast.LENGTH_SHORT).show();
                 }
             }
         });

        loginToolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(loginToolbar);
    }

    private void loginUser(){
        firebaseAuth.signInWithEmailAndPassword(usuario_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) iniciarSesion();
                else{
                    Toast.makeText(context, "No se pudo iniciar sesión, compruebe sus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickRegistrarse(View view) {
        Intent iniciarRegistro = new Intent(this, RegisterActivity.class);
        startActivity(iniciarRegistro);
        Log.i("Click", "Has apretado el text view Registrarse");
    }

    public void iniciarSesion() {
        if(firebaseAuth.getCurrentUser()!=null){
            getUserInfo();
            Log.i("Inicio sesion", "inicio");
            if(esAsistente) {
                Log.i("Inicio sesion", "ES ASISTENTE");
                Intent iniciarHomeAsistente = new Intent(this, MenuParticipantActivity.class);
                startActivity(iniciarHomeAsistente);
            }
            else {
                Log.i("Inicio sesion", "ES ORGANIZADOR");
                Intent iniciarHomeOrganizador = new Intent(this, MenuOrganizerActivity.class);
                startActivity(iniciarHomeOrganizador);
            }
        }
    }

    public void getUserInfo(){
        String id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("SNAPSHOT", snapshot.child("esAsistente").getValue().toString());
                if(snapshot.exists()) esAsistente = (boolean) snapshot.child("esAsistente").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}