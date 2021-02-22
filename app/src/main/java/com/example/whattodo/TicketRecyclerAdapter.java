package com.example.whattodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whattodo.Notificaciones.Utils;
import com.example.whattodo.menus.MenuParticipantActivity;
import com.example.whattodo.model.Ticket;
import java.util.ArrayList;
import java.util.Calendar;

import static androidx.core.content.ContextCompat.startActivity;

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.TicketViewHolder> {

    ArrayList<String> listaNombres, listaFecha, listaInicioEvento;
    Dialog myDialog;
    int alarmID = 1;
    SharedPreferences settings;
    Context context;

    public TicketRecyclerAdapter(ArrayList<Ticket> tickets, SharedPreferences settings, Context context) {
        listaNombres = new ArrayList<String>();
        listaFecha = new ArrayList<String>();
        listaInicioEvento = new ArrayList<String>();
        this.settings = settings;
        this.context = context;

        for(int i=0; i<tickets.size(); i++) {
            listaNombres.add(tickets.get(i).getEvento().getNombre());
            listaFecha.add(tickets.get(i).getEvento().getFecha());
            listaInicioEvento.add(tickets.get(i).getEvento().getHorarioInicio());
        }
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.asignarDatos(listaNombres.get(position), listaFecha.get(position), listaInicioEvento.get(position));
    }

    @Override
    public int getItemCount() {
        return listaNombres.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView icono, imagenCalendario, imagenReloj;
        TextView nombre, tvCerrar;
        Button btnVerEvento, btnRecordatorio, btnRecordar;
        EditText editFechaRecordatorio, editHoraRecordatorio;

        public TicketViewHolder(@NonNull View base) {
            super(base);
            icono = (ImageView) base.findViewById(R.id.imageEventTicket);
            nombre = (TextView) base.findViewById(R.id.eventName);
            btnVerEvento = (Button) base.findViewById(R.id.verEventoBtn);
            btnRecordatorio = (Button) base.findViewById(R.id.btnRecordatorio);
        }

        public void asignarDatos(String nombreEvento, String fecha, String inicioEvento) {
            nombre.setText(nombreEvento);
            btnVerEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Button", "Apretaste ver evento");
                }
            });

            btnRecordatorio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowPopup(nombreEvento, fecha, inicioEvento);
                }
            });

        }

        public void ShowPopup(String nombreEvento, String fecha, String inicioEvento) {
            myDialog = new Dialog(context);

            myDialog.setContentView(R.layout.pop_up_crear_recordatorio);

            imagenCalendario = (ImageView) myDialog.findViewById(R.id.imagenCalendarioRecordatorio);
            imagenReloj = (ImageView) myDialog.findViewById(R.id.imagenRelojRecordatorio);
            btnRecordar = (Button) myDialog.findViewById(R.id.btnRecordar);
            editFechaRecordatorio = (EditText) myDialog.findViewById(R.id.editFechaRecordatorio);
            editHoraRecordatorio = (EditText) myDialog.findViewById(R.id.editHoraRecordatorio);
            tvCerrar = (TextView) myDialog.findViewById(R.id.txt_cerrar_recordatorio);

            tvCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            Calendar mcurrentTime = Calendar.getInstance();
            int anio = mcurrentTime.get(Calendar.YEAR);
            int mes = mcurrentTime.get(Calendar.MONTH);
            int dia = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            Calendar today = Calendar.getInstance();
            SharedPreferences.Editor edit = settings.edit();

            imagenCalendario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            editFechaRecordatorio.setText(selectedDay+ "/" + (selectedMonth+1) + "/" + selectedYear);

                            today.set(Calendar.YEAR, selectedYear);
                            today.set(Calendar.MONTH, selectedMonth);
                            today.set(Calendar.DAY_OF_MONTH, selectedDay);

                            edit.putString("year", String.valueOf(selectedYear));
                            edit.putString("month", String.valueOf(selectedMonth));
                            edit.putString("day", String.valueOf(selectedDay));

                            //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                            edit.putInt("alarmID", alarmID);
                            edit.putLong("alarmTime", today.getTimeInMillis());

                        }
                    }, dia, mes, anio);

                    datePickerDialog.setTitle("Selecciona la fecha");
                    datePickerDialog.show();

                }
            });

            imagenReloj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String finalHour, finalMinute;

                            finalHour = "" + selectedHour;
                            finalMinute = "" + selectedMinute;
                            if (selectedHour < 10) finalHour = "0" + selectedHour;
                            if (selectedMinute < 10) finalMinute = "0" + selectedMinute;

                            today.set(Calendar.HOUR_OF_DAY, selectedHour);
                            today.set(Calendar.MINUTE, selectedMinute);
                            today.set(Calendar.SECOND, 0);

                            edit.putString("hour", finalHour);
                            edit.putString("minute", finalMinute);

                            //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                            edit.putInt("alarmID", alarmID);
                            edit.putLong("alarmTime", today.getTimeInMillis());

                            editHoraRecordatorio.setText(finalHour + ":" + finalMinute + "hs");

                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Selecciona la hora");
                    mTimePicker.show();
                }
            });

            btnRecordar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editFechaRecordatorio.getText().toString().isEmpty() || editHoraRecordatorio.getText().toString().isEmpty()){
                        Toast.makeText(context, "Debe elegir fecha y hora!", Toast.LENGTH_LONG).show();
                    }

                    if(!editFechaRecordatorio.getText().toString().isEmpty() && !editHoraRecordatorio.getText().toString().isEmpty()){
                        String mensaje = "Recordatorio para asistir al evento "+ nombreEvento + " el día " + fecha + " a las " + inicioEvento + "hs." ;

                        edit.commit();
                        Utils.setAlarm(alarmID, today.getTimeInMillis(), context, mensaje);

                        editFechaRecordatorio.setText(null);
                        editHoraRecordatorio.setText(null);
                        Toast.makeText(context, "Se ha generado su recordatorio!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }

    }
}
