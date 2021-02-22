package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whattodo.model.Database;
import com.example.whattodo.model.Evento;
import com.example.whattodo.model.Participante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PuntuarOrganizadorActivity extends AppCompatActivity {

    String nombreEvento_str = "", fechaEvento_str = "", inicioEvento_str = "", idOrganizador = "", idUsuario = "", nombreUsuario = "";
    TextView nombreEventoTV, fechaEventoTV;
    RatingBar ratingBarQueTePArecio, ratingBarExperiencia;
    float valorQueTeParecio = 0, valorExperiencia = 0;
    Button btnEnviarOpinion;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText editAgregarAlgoMas;
    Context context;
    Database databaseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_organizador);

        getSupportActionBar().setTitle("Opinar sobre organizador");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseClass = new Database();

        Bundle extras = getIntent().getExtras();

        nombreEvento_str = extras.getString("nombreEvento");
        fechaEvento_str = extras.getString("fechaEvento");
        inicioEvento_str = extras.getString("inicioEvento");
        idOrganizador = extras.getString("idOrganizador");

        nombreEventoTV = (TextView) findViewById(R.id.nombreEventoPuntuacion);
        fechaEventoTV = (TextView) findViewById(R.id.fechaEventoPuntuacion);
        ratingBarQueTePArecio = (RatingBar) findViewById(R.id.ratingBarQueTeParecio);
        ratingBarExperiencia = (RatingBar) findViewById(R.id.ratingBarExperiencia);
        btnEnviarOpinion = (Button) findViewById(R.id.btnEnviarOpinion);
        editAgregarAlgoMas = (EditText) findViewById(R.id.editAgregarAlgoMas);

        editAgregarAlgoMas.setText("");
        nombreEventoTV.setText(nombreEvento_str);
        fechaEventoTV.setText(fechaEvento_str + " - " + inicioEvento_str);

        ratingBarQueTePArecio.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                valorQueTeParecio = rating;
            }
        });

        ratingBarExperiencia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                valorExperiencia = rating;
            }
        });

        idUsuario = firebaseAuth.getCurrentUser().getUid();
        getNombreUsuario();

        btnEnviarOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    guardarOpinion();
                }
            }
        });
    }

    public boolean validarCampos() {
        boolean retorno = true;

        if (valorExperiencia == 0 || valorQueTeParecio == 0) {
            Toast.makeText(this, "Debe puntuar para enviar su opinión!", Toast.LENGTH_SHORT).show();
            retorno = false;
        }

        return retorno;
    }

    public void guardarOpinion() {
        String puntuacion = String.valueOf((valorQueTeParecio + valorExperiencia) / 2);

        Calendar fechaHoy = Calendar.getInstance();
        String dia = String.valueOf(fechaHoy.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(fechaHoy.get(Calendar.MONTH));
        String anio = String.valueOf(fechaHoy.get(Calendar.YEAR));
        String hora = String.valueOf(fechaHoy.get(Calendar.HOUR_OF_DAY));
        String minutos = String.valueOf(fechaHoy.get(Calendar.MINUTE));
        String fechaHoy_str = dia + "/" + mes + "/" + anio + " - " + hora + ":" + minutos + "hs";

        Map<String, Object> map = new HashMap<>();
        map.put("idOrganizador", idOrganizador);
        map.put("nombreEvento", nombreEvento_str);
        map.put("idUsuario", idUsuario);
        map.put("comentario", editAgregarAlgoMas.getText().toString());
        map.put("puntuacion", puntuacion);
        map.put("fechaOpinion", fechaHoy_str);
        map.put("nombreUsuario", nombreUsuario);

        databaseReference.child("Puntuacion").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    editAgregarAlgoMas.setText("");
                    ratingBarExperiencia.setRating(0);
                    ratingBarQueTePArecio.setRating(0);

                    Toast.makeText(context, "El evento ha sido registrado con éxito!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getNombreUsuario() {

        databaseClass.mReadDataOnce("Users", new OnGetDataListener() {
            ProgressDialog mProgressDialog = null;

            @Override
            public void onStart() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Cargando..");
                    mProgressDialog.setIndeterminate(true);
                }
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot snapshot) throws ParseException {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getKey().equals(idUsuario)) {
                            nombreUsuario = ds.child("name").getValue().toString();
                        }
                    }
                }
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
            }
        });
    }


}