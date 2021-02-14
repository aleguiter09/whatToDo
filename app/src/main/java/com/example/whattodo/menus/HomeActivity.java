package com.example.whattodo.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.whattodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    Toolbar homeToolbar;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Boolean esAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //firebaseAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        homeToolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(homeToolbar);

        //Notificaciones push
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            // Error
                            return;
                        }

                        // FCM token
                        String token = task.getResult();

                        // Imprimirlo en un toast y en logs
                        Log.d("MSSG", token);
                        Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                        //System.out.println("El token es: "+token);
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){
            getUserInfo();
            if(esAsistente){
                Intent iniciarHomeAsistente = new Intent(this, ParticipantHomeActivity.class);
                startActivity(iniciarHomeAsistente);
            }else{
                Intent iniciarHomeOrganizador = new Intent(this, OrganizerHomeActivity.class);
                startActivity(iniciarHomeOrganizador);
            }
        }
    }

    public void getUserInfo(){
        String id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) esAsistente = (Boolean) snapshot.child("esAsistente").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}