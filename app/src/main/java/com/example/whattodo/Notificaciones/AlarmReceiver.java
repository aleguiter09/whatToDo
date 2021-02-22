package com.example.whattodo.Notificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String mensaje = extras.getString("mensaje");

        Intent service1 = new Intent(context, NotificationService.class);
        service1.putExtra("mensaje", mensaje);

        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("RECORDATORIO", " ALARM RECEIVED!!!");

    }
}