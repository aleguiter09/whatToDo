package com.example.whattodo;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapsActivityPopUp extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Context context;
    Double latitud, longitud;
    String direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pop_up);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_pop_up);
        mapFragment.getMapAsync(this);

        context = this;

        Bundle extras = getIntent().getExtras();

        latitud = Double.valueOf(extras.getString("latitud"));
        longitud = Double.valueOf(extras.getString("longitud"));
        direccion = extras.getString("dirección");

        getSupportActionBar().setTitle("Mapa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        configurarMapa();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            configurarMapa();
            System.out.println("LLEGO MAPA");
        }

    }

    @SuppressLint("MissingPermission")
    public void configurarMapa() {
        mMap.setMyLocationEnabled(true);
        LatLng posicion = new LatLng(latitud, longitud);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.addMarker(new MarkerOptions().position(posicion).title(direccion));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
    }


}
