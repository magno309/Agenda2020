package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AgregarNotaActivity extends AppCompatActivity {

    Button btnAgregarNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        btnAgregarNota = (Button) findViewById(R.id.btnAgregarNota);

        btnAgregarNota.setOnClickListener(view -> {

        });
    }
}