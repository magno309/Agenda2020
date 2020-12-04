package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AgregarTareaActivity extends AppCompatActivity {

    NotasDBHelper dbHelper;
    SQLiteDatabase db;
    EditText txtNombre, txtDescripcion, btnHora, btnFecha;
    ImageButton btnVoice, btnFoto, btnVideo ,btnRecordatorio;
    Button btnAgregarTarea;
    RecyclerView recyclerView;
    AdaptadorRecordatorios adaptadorRecordatorios;
    List<Recordatorios> listaRecordatorios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        txtNombre = (EditText) findViewById(R.id.txtNombreTarea);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcionTarea);

        btnVoice = (ImageButton) findViewById(R.id.btnVoiceTareas);
        btnFoto = (ImageButton) findViewById(R.id.btnFotoTareas);
        btnVideo = (ImageButton) findViewById(R.id.btnVideoTareas);
        btnRecordatorio = (ImageButton) findViewById(R.id.btnAgregarRecordatorio);

        btnHora = (EditText) findViewById(R.id.txtHoraTareas);
        btnFecha = (EditText) findViewById(R.id.txtFechaTareas);

        btnAgregarTarea = (Button) findViewById(R.id.btnAgregarTarea);

        listaRecordatorios = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rvListaRecordatorios);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adaptadorRecordatorios = new AdaptadorRecordatorios(this, listaRecordatorios);
        recyclerView.setAdapter(adaptadorRecordatorios);

        btnFecha.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        btnHora.setOnClickListener(view -> {
            showTimePickerDialog();
        });

        btnAgregarTarea.setOnClickListener(view -> {
            if(true/*Aquí va el update, creo*/){
                String nombre = txtNombre.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String fechaVencimiento = btnFecha.getText().toString();
                String horaVencimiento = btnHora.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(NotasDB.TareasDatabase.COLUMN_NAME_COL1, nombre);
                contentValues.put(NotasDB.TareasDatabase.COLUMN_NAME_COL2, descripcion);
                contentValues.put(NotasDB.TareasDatabase.COLUMN_NAME_COL6, fechaVencimiento);
                contentValues.put(NotasDB.TareasDatabase.COLUMN_NAME_COL7, horaVencimiento);
                long rowID = db.insert(NotasDB.TareasDatabase.TABLE_NAME, null, contentValues);
                if (rowID != -1) {
                    Toast.makeText(this, "Tarea registrada exitosamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, "Error al registrar la nota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRecordatorio.setOnClickListener(view -> {
            if(!btnFecha.getText().toString().equals("") || !btnHora.getText().toString().equals("")){
                listaRecordatorios.add(new Recordatorios(btnFecha.getText().toString(), btnHora.getText().toString()));
                adaptadorRecordatorios.notifyItemInserted(listaRecordatorios.size()-1);
                btnFecha.setText("");
                btnHora.setText("");
            }
        });
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int minuto = c.get(Calendar.MINUTE);
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                btnHora.setText(" " + horaFormateada + ":" + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        final int mes = c.get(Calendar.MONTH);
        final int dia = c.get(Calendar.DAY_OF_MONTH);
        final int anio = c.get(Calendar.YEAR);
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                btnFecha.setText(" " + diaFormateado + "/" + mesFormateado + "/" + year);
            }
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

}