package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AgregarNotaActivity extends AppCompatActivity {

    NotasDBHelper dbHelper;
    String nombre, descripcion, fechaHora;
    SQLiteDatabase db;
    private EditText txtNombre, txtDescrpcion;
    private Button btnAgregarNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        dbHelper = new NotasDBHelper(this);
        db = dbHelper.getWritableDatabase();
        txtNombre = findViewById(R.id.txtNombreNota);
        txtDescrpcion = findViewById(R.id.txtDescripcionNota);
        btnAgregarNota = (Button) findViewById(R.id.btnAgregarNota);
        btnAgregarNota.setOnClickListener(view -> {
            nombre = txtNombre.getText().toString();
            descripcion = txtDescrpcion.getText().toString();
            ContentValues values = new ContentValues();
            values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL1, nombre);
            values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL2, descripcion);
            values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL6, Calendar.getInstance().getTime().toString());
            long rowId = db.insert(NotasDB.NotasDatabase.TABLE_NAME, null, values);
            if (rowId != -1) {
                Toast.makeText(this, "Nota registrada exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error al registrar la nota", Toast.LENGTH_SHORT).show();
            }
        });
    }
}