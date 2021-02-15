package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    Toolbar createEventToolbar;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    Button btnUbicacion, btnCrearEvento;
    EditText nombreEvento, fechaEvento, inicioEvento, finEvento, descripcion;
    String nombreEvento_str, fechaEvento_str, inicioEvento_str, finEvento_str, descripcion_str;
    String nombreOrganizador;
    Context context;
    boolean retorno = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        createEventToolbar = findViewById(R.id.createEventToolbar);
        setSupportActionBar(createEventToolbar);

        btnCrearEvento = (Button) findViewById(R.id.createEventButton);
        btnUbicacion = (Button) findViewById(R.id.locationButton);
        nombreEvento = (EditText) findViewById(R.id.editTextEventName);
        fechaEvento = (EditText) findViewById(R.id.editTextFecha);
        inicioEvento = (EditText) findViewById(R.id.editTextInicio);
        finEvento = (EditText) findViewById(R.id.editTextFin);
        descripcion = (EditText) findViewById(R.id.editTextDesc);

        context = this;

        btnCrearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) guardarEvento();
            }
        });

    }

    public boolean validarCampos(){
        boolean retorno = true;

        nombreEvento_str = nombreEvento.getText().toString();
        fechaEvento_str = fechaEvento.getText().toString();
        inicioEvento_str = inicioEvento.getText().toString();
        finEvento_str = finEvento.getText().toString();
        descripcion_str = descripcion.getText().toString();

        if(nombreEvento_str.isEmpty()){
            nombreEvento.setError("Campo obligatorio");
            retorno = false;
        }/*else if(checkEventExist()){
            nombreEvento.setError("Ese evento ya existe");
            retorno = false;
        }*/

        if(fechaEvento_str.isEmpty()){
            fechaEvento.setError("Campo obligatorio");
            retorno = false;
        }

        if(inicioEvento_str.isEmpty()){
            inicioEvento.setError("Campo obligatorio");
            retorno = false;
        }

        if(finEvento_str.isEmpty()){
            finEvento.setError("Campo obligatorio");
            retorno = false;
        }

        if(descripcion_str.isEmpty()){
            descripcion.setError("Campo obligatorio");
            retorno = false;
        }

        return retorno;
    }

    public void guardarEvento(){

        String id = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) nombreOrganizador = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Map<String, Object> map = new HashMap<>();
        map.put("idOrganizador", id);
        map.put("nombreOrganizador", nombreOrganizador);
        map.put("nombreEvento", nombreEvento_str);
        map.put("fechaEvento", fechaEvento_str);
        map.put("inicioEvento", inicioEvento_str);
        map.put("finEvento", finEvento_str);
        map.put("descripcion", descripcion_str);

        databaseReference.child("Events").child(nombreEvento_str).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    nombreEvento.setText(null);
                    fechaEvento.setText(null);
                    inicioEvento.setText(null);
                    finEvento.setText(null);
                    descripcion.setText(null);
                    Toast.makeText(context, "El evento ha sido registrado con éxito!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean checkEventExist(){
        Query query = databaseReference.child("Events").orderByChild("nombreEvento").equalTo(nombreEvento_str).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) retorno = true;
                else retorno = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return retorno;
    }

}