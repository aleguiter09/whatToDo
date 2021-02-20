package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.whattodo.model.Database;
import com.example.whattodo.model.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NearestEventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    Spinner spinner;
    private LatLng myLocation;
    Database databaseClass;
    ArrayList<Evento> eventos = new ArrayList<Evento>();
    private ArrayList<Marker> temporalRealTime = new ArrayList<>();
    private ArrayList<Marker> realTime = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_event);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseClass = new Database();
        context = this;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap=googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        //obtener mi ubicacion
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        myLocation = new LatLng(latitude, longitude);


        //Agregamos marcadores de todos los eventos obtenidos de la bdd

        /*ArrayList<MarkerOptions> listaMarcadores = new ArrayList<MarkerOptions>();
        for(int n=0; n<eventos.size(); n++) {
            System.out.println("NOMBRE :  " +eventos.get(n).getNombre());
            MarkerOptions m1 = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            listaMarcadores.add(m1);
        }

        for(int i=0; i<eventos.size(); i++){
            System.out.println(eventos.get(i).getNombre());
            double lat = Double.parseDouble(eventos.get(i).getLatitud());
            double lon = Double.parseDouble(eventos.get(i).getLontitud());
            LatLng event = new LatLng(lat,lon);
            listaMarcadores.get(i).title(eventos.get(i).getNombre());
            listaMarcadores.get(i).snippet(eventos.get(i).getFecha());
            listaMarcadores.get(i).position(event);
            myMap.addMarker(listaMarcadores.get(i));

        }*/

        databaseClass.mReadDataOnce("Events", new OnGetDataListener() {
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
            public void onSuccess(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //Evento ev = snapshot1.getValue(Evento.class);

                    String nombreEvento = snapshot1.child("nombreEvento").getValue().toString();
                    String fechaEvento = snapshot1.child("fechaEvento").getValue().toString();
                    String latitud = snapshot1.child("latitud").getValue().toString();
                    String longitud = snapshot1.child("longitud").getValue().toString();

                    double lat = Double.parseDouble(latitud);
                    double lon = Double.parseDouble(longitud);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(lat,lon))
                            .title(nombreEvento)
                            .snippet(fechaEvento)
                            .icon(BitmapDescriptorFactory .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    temporalRealTime.add(myMap.addMarker(markerOptions));
                }
                realTime.clear();
                realTime.addAll(temporalRealTime);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }


            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

             /*   .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //Evento ev = snapshot1.getValue(Evento.class);

                    String nombreEvento = snapshot1.child("nombreEvento").getValue().toString();
                    String fechaEvento = snapshot.child("fechaEvento").getValue().toString();
                    String latitud = snapshot1.child("latitud").getValue().toString();
                    String longitud = snapshot1.child("longitud").getValue().toString();

                    double lat = Double.parseDouble(latitud);
                    double lon = Double.parseDouble(longitud);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(lat,lon))
                            .title(nombreEvento)
                            .snippet(fechaEvento)
                            .icon(BitmapDescriptorFactory .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    temporalRealTime.add(myMap.addMarker(markerOptions));
                }
                realTime.clear();
                realTime.addAll(temporalRealTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        configurarMapa();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==9999 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            configurarMapa();
            System.out.println("LLEGO MAPA");
        }

    }

    @SuppressLint("MissingPermission")
    public void configurarMapa() {

        spinner = (Spinner) findViewById(R.id.spinner);
        String[] datos = new String[] {"5KM", "10KM", "15KM"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);

        myMap.setMyLocationEnabled(true);
        LatLng utn = new LatLng(-31.6168, -60.6752);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utn, 15));
        myMap.getUiSettings().setZoomControlsEnabled(true);

        //Agregamos marcador de nuestra ubicacion
        MarkerOptions marcador = new MarkerOptions()
                .position(myLocation)
                .title("Mi ubicación")
                .snippet(".........")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        myMap.addMarker(marcador);



        //Seteamos la funcion del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        LatLngBounds LIMIT1 = new LatLngBounds(new LatLng(myLocation.latitude-0.03, myLocation.longitude), new LatLng(myLocation.latitude+0.03, myLocation.longitude));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LIMIT1, 0));

                        break;
                    case 1:
                        LatLngBounds LIMIT2 = new LatLngBounds(new LatLng(myLocation.latitude-0.06, myLocation.longitude), new LatLng(myLocation.latitude+0.06, myLocation.longitude));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LIMIT2, 0));

                        break;
                    case 2:
                        LatLngBounds LIMIT3 = new LatLngBounds(new LatLng(myLocation.latitude-0.09, myLocation.longitude), new LatLng(myLocation.latitude+0.09, myLocation.longitude));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LIMIT3, 0));

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utn, 15));
            }
        });



    }
}




