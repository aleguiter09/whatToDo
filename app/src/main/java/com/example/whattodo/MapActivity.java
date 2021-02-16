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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Marker> marcadores;
    private LatLng ubicacion;
    Button confirmar_ubicacion;
    TextView direccionTV;
    Context context;
    String direccionGuardar, latitudGuardar, longitudGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        marcadores = new ArrayList<Marker>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        confirmar_ubicacion = (Button) findViewById(R.id.confirmar_ubicacion);
        direccionTV = (TextView) findViewById(R.id.DireccionTV);

        context = this;

        confirmar_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("dirección", direccionGuardar);
                intent.putExtra("latitud", latitudGuardar);
                intent.putExtra("longitud", longitudGuardar);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

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
        LatLng utn = new LatLng(-31.6168, -60.6752);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utn, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                obtenerDireccion(marker.getPosition().latitude, marker.getPosition().longitude);

                ubicacion = marker.getPosition();
                return false;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                obtenerDireccion(marker.getPosition().latitude, marker.getPosition().longitude);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                obtenerDireccion(marker.getPosition().latitude, marker.getPosition().longitude);
                ubicacion = marker.getPosition();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (!marcadores.isEmpty()) {
                    marcadores.get(0).remove();
                    marcadores.clear();
                }

                obtenerDireccion(latLng.latitude, latLng.longitude);

                ubicacion = latLng;
                MarkerOptions marcador = new MarkerOptions()
                        .alpha(0.5f)
                        .position(latLng)
                        .draggable(true)
                        .title("Mi ubicación")
                        .snippet("Subtitulo")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker M = mMap.addMarker(marcador);
                marcadores.add(M);

            }
        });

    }

    public void obtenerDireccion(Double latitud, Double longitud){
        Geocoder geocoder;
        List<Address> direccion = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            direccion = geocoder.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = direccion.get(0).getAddressLine(0);

        direccionTV.setText("Dirección: "+address);
        direccionGuardar = address;
        latitudGuardar = String.valueOf(latitud);
        longitudGuardar = String.valueOf(longitud);
    }

}
