package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AgregarNotaActivity extends AppCompatActivity {

    NotasDBHelper dbHelper;
    String nombre, descripcion, fechaHora;
    SQLiteDatabase db;
    private EditText txtNombre, txtDescrpcion;
    private Button btnAgregarNota;
    private ImageButton btnVoice;

    //MediaRecorder
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;


    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private int audiosGrabados = 0;

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        audiosGrabados++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        dbHelper = new NotasDBHelper(this);
        db = dbHelper.getWritableDatabase();
        txtNombre = findViewById(R.id.txtNombreNota);
        txtDescrpcion = findViewById(R.id.txtDescripcionNota);
        btnAgregarNota = (Button) findViewById(R.id.btnAgregarNota);
        int idNota = this.getIntent().getIntExtra("idNota",-1);
        //Log.println(Log.INFO,"baseDeDatosNotass","idRecibido: "+idNota);
        //Toast.makeText(this, "ID: "+idNota, Toast.LENGTH_SHORT).show();
        if(idNota==-1){
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
        else{
            Cursor c1 = db.query(NotasDB.NotasDatabase.TABLE_NAME, null, NotasDB.NotasDatabase._ID + " = " + idNota, null, null, null, null);
            if (c1 != null && c1.getCount() != 0) {
                while (c1.moveToNext()) {
                    txtNombre.setText(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL1)));
                    txtDescrpcion.setText(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL2)));
                }
                btnAgregarNota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nombre = txtNombre.getText().toString();
                        descripcion = txtDescrpcion.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL1, nombre);
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL2, descripcion);
                        if(db.update(NotasDB.NotasDatabase.TABLE_NAME, values, NotasDB.NotasDatabase._ID + " = " + idNota, null)!=-1){
                            Toast.makeText(AgregarNotaActivity.this, "Nota actualizada correctamente",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AgregarNotaActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(AgregarNotaActivity.this, "Error al actualizar la nota", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}