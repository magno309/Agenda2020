package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AgregarNotaActivity extends AppCompatActivity {

    private boolean notaGuardada = false;

    //Base de datos
    NotasDBHelper dbHelper;
    String nombre, descripcion, fechaHora;
    SQLiteDatabase db;

    //Componentes de la UI
    private EditText txtNombre, txtDescrpcion;
    private Button btnAgregarNota;
    private ImageButton btnCamaraNota, btnVideoNota, btnVoice;

    //Multimedia
    private RecyclerView rvMultimedia;
    private boolean esImagen=false;

    //Imagenes
    private ImageView imageViewTemp;
    private List<String> imgsPaths, imgsCapturadasPaths; //imgsDescripciones;
    private String pathImagenes, rutaFotoCapturada; // descImagenes, pathImgSeleccionada;
    private AdaptadorImagen adaptadorImagen;
    static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    static final int REQUEST_CODE_SELECT_IMAGE = 2;
    static final int REQUEST_CODE_WRITE_STORAGE_PERMISSION = 3;
    static final int REQUEST_CODE_IMAGE_CAPTURE = 4;

    //Videos
    static final int REQUEST_CODE_SELECT_VIDEO = 5;

    //Audio
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 6;
    private List<String> audiosCapturadosPaths;
    private static String rutaAudioCapturado;
    private boolean grabando=false;
    private MediaRecorder recorder = null;

    //private boolean permissionToRecordAccepted = false;
    //private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE_PERMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(esImagen) seleccionarImagen();
                    else seleccionarVideo();
                }
                break;
            case REQUEST_CODE_WRITE_STORAGE_PERMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    tomarCaptura();
                }
                break;
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(grabando==false){
                        tomarAudio();
                        btnVoice.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop));
                        grabando=true;
                    }
                }
                break;
        }
        //if (!permissionToRecordAccepted ) finish();
    }

    private void seleccionarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    private void tomarCaptura(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = crearArchivoDeImagen();
            } catch (IOException ex) {
                Toast.makeText(AgregarNotaActivity.this, "Error al crear el archivo de img: "+ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "com.example.agenda",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
        }
    }

    private void tomarAudio(){
        File audioFile = null;
        try{
            audioFile = crearArchivoDeAudio();
        }catch (IOException ex){
            Toast.makeText(AgregarNotaActivity.this, "Error al crear el archivo de audio: "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        if(audioFile != null){
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(rutaAudioCapturado);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e("audio", "prepare() failed");
            }
            recorder.start();
        }
    }

    private File crearArchivoDeImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        rutaFotoCapturada = image.getAbsolutePath();
        imgsCapturadasPaths.add(rutaFotoCapturada);
        return image;
    }

    private File crearArchivoDeAudio() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "AUDIO_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File audio = File.createTempFile(
                imageFileName,  /* prefix */
                ".3gp",         /* suffix */
                storageDir      /* directory */
        );
        rutaAudioCapturado = audio.getAbsolutePath();
        audiosCapturadosPaths.add(rutaAudioCapturado);
        return audio;
    }

    private void seleccionarVideo(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri imgSelUri = data.getData();
                if(imgSelUri != null){
                    try
                    {
                        //InputStream inputStream = getContentResolver().openInputStream(imgSelUri);
                        //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //imageViewTemp.setImageBitmap(bitmap);
                        //pathImgSeleccionada=getPathFromUri(imgSelUri);
                        //imageViewTemp.setImageBitmap(BitmapFactory.decodeFile(pathImgSeleccionada));

                        //Log.println(Log.INFO, "ruta", "UriTemp: "+imgSelUri);
                        //Log.println(Log.INFO, "ruta", "GetRutaUriTemp: "+imgSelUri.getPath());
                        //Log.println(Log.INFO, "ruta", "RutaUriTemp: "+pathImgSeleccionada);

                        imgsPaths.add(getPathFromUri(imgSelUri));
                        adaptadorImagen.notifyItemInserted(imgsPaths.size()-1);
                        //Log.println(Log.INFO, "ruta", "Uri: "+imgSelUri.getPath());

                        /*Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(imageBitmap);*/
                    }catch (Exception ex){
                        Toast.makeText(this, "Error al abrir imagen: "+ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        else if(requestCode == REQUEST_CODE_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if(rutaFotoCapturada != null && !rutaFotoCapturada.isEmpty()){
                imgsPaths.add(rutaFotoCapturada);
                adaptadorImagen.notifyItemInserted(imgsPaths.size()-1);
            }
            //Log.println(Log.INFO, "rutas", "ruta: "+rutaFotoCapturada);
            //Uri uri = Uri.fromFile(new File(rutaFotoCapturada));
            //Log.println(Log.INFO, "rutas", "uri: "+uri.toString());
        }
        else if(requestCode == REQUEST_CODE_SELECT_VIDEO && resultCode == RESULT_OK){
            if(data != null){
                Uri videoUri = data.getData();
                if(videoUri != null){
                    try {
                        //Log.println(Log.INFO, "rutas", "ruta: "+getPathFromUri(videoUri));
                        imgsPaths.add(getPathFromUri(videoUri));
                        adaptadorImagen.notifyItemInserted(imgsPaths.size()-1);
                       /* FragmentManager fragmentManager = getSupportFragmentManager();
                        PlayVideoFragment playVideoFragment = new PlayVideoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(playVideoFragment.ARG_RUTA_VIDEO, getPathFromUri(videoUri));
                        playVideoFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.actAgregarNota,playVideoFragment).commit();*/

                        /*Intent intent = new Intent(this, PlayVideo.class);
                        intent.putExtra("rutaVideo", getPathFromUri(videoUri));
                        startActivity(intent);*/

                        //imageViewTemp.setImageBitmap(ThumbnailUtils.createVideoThumbnail(getPathFromUri(videoUri), MediaStore.Video.Thumbnails.MINI_KIND));

                    }catch (Exception ex){
                        Log.e("error",ex.getMessage());
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri uri){
        String filePath;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor == null){
            filePath=uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    private void pararGrabacion() {
        recorder.stop();
        recorder.release();
        recorder = null;
        imgsPaths.add(rutaAudioCapturado);
        adaptadorImagen.notifyItemInserted(imgsPaths.size()-1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        //pathImgSeleccionada="";
        pathImagenes="";
        imgsPaths=new ArrayList<>();
        imgsCapturadasPaths=new ArrayList<>();
        audiosCapturadosPaths=new ArrayList<>();
        //imgsDescripciones=new ArrayList<>();
        dbHelper = new NotasDBHelper(this);
        db = dbHelper.getWritableDatabase();
        txtNombre = findViewById(R.id.txtNombreNota);
        txtDescrpcion = findViewById(R.id.txtDescripcionNota);
        btnAgregarNota = (Button) findViewById(R.id.btnAgregarNota);
        btnCamaraNota = findViewById(R.id.btnCameraNotas);
        btnCamaraNota.setOnClickListener(btnCameraNotaOnClick());
        btnVideoNota = findViewById(R.id.btnVideoNotas);
        btnVideoNota.setOnClickListener(btnVideoNotaOnClick());
        btnVoice = findViewById(R.id.btnVoiceNotas);
        btnVoice.setOnClickListener(btnAudioNotaOnClick());
        //imageViewTemp = findViewById(R.id.imgViewTemp);
        rvMultimedia = findViewById(R.id.rvMultimediaNotas);
        rvMultimedia.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        int idNota = this.getIntent().getIntExtra("idNota",-1);
        //Log.println(Log.INFO,"baseDeDatosNotass","idRecibido: "+idNota);
        //Toast.makeText(this, "ID: "+idNota, Toast.LENGTH_SHORT).show();
        if(idNota==-1){
            adaptadorImagen = new AdaptadorImagen(this,imgsPaths,null);
            rvMultimedia.setAdapter(adaptadorImagen);
            btnAgregarNota.setOnClickListener(view -> {
                notaGuardada = true;
                nombre = txtNombre.getText().toString();
                descripcion = txtDescrpcion.getText().toString();
                if(!imgsPaths.isEmpty()){
                    boolean uno = imgsPaths.size()==1;
                    for(String s:imgsPaths){
                        pathImagenes+=s;
                        if(!uno) pathImagenes+=",";
                    }

                    //Log.println(Log.INFO, "rutas", "Obte: "+pathImagenes);
                }
                ContentValues values = new ContentValues();
                values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL1, nombre);
                values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL2, descripcion);
                values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL3, pathImagenes);
                //values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL3, pathImgSeleccionada);
                values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL6, Calendar.getInstance().getTime().toString());
                //values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL7, descImagenes);
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
            btnAgregarNota.setText("ACTUALIZAR NOTA");
            Cursor c1 = db.query(NotasDB.NotasDatabase.TABLE_NAME, null, NotasDB.NotasDatabase._ID + " = " + idNota, null, null, null, null);
            if (c1 != null && c1.getCount() != 0) {
                while (c1.moveToNext()) {
                    txtNombre.setText(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL1)));
                    txtDescrpcion.setText(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL2)));
                    pathImagenes = c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL3));
                    //pathImgSeleccionada = c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL3));
                    //descImagenes = c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL7));
                    if(pathImagenes!=null&&!pathImagenes.isEmpty()){
                       String[] arr = pathImagenes.split(",");
                        for(int i=0; i<arr.length; i++){
                            imgsPaths.add(arr[i]);
                            //Log.println(Log.INFO, "rutas", "Add: "+arr[i]);
                        }

                        /*arr = descImagenes.split(",");
                        for(String s:arr){
                            imgsDescripciones.add(s);
                        }*/

                        //imageViewTemp.setImageBitmap(BitmapFactory.decodeFile(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL3))));
                        //imageViewTemp.setVisibility(View.VISIBLE);
                    }
                }

                adaptadorImagen = new AdaptadorImagen(this,imgsPaths,null);
                rvMultimedia.setAdapter(adaptadorImagen);

                btnAgregarNota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notaGuardada = true;
                        nombre = txtNombre.getText().toString();
                        descripcion = txtDescrpcion.getText().toString();
                        if(!imgsPaths.isEmpty()){
                            boolean uno = imgsPaths.size()==1;
                            pathImagenes="";
                            for(String s:imgsPaths){
                                pathImagenes+=s;
                                if(!uno) pathImagenes+=",";
                            }

                            //Log.println(Log.INFO, "rutas", "Obte: "+pathImagenes);
                        }
                        ContentValues values = new ContentValues();
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL1, nombre);
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL2, descripcion);
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL3, pathImagenes);
                        //values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL3, pathImgSeleccionada);
                        values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL6, Calendar.getInstance().getTime().toString());
                        //values.put(NotasDB.NotasDatabase.COLUMN_NAME_COL7, descImagenes);
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

    private View.OnClickListener btnCameraNotaOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] opciones = {"Seleccionar imagen del sistema","Tomar una foto", "Cancelar" };
                AlertDialog.Builder builder = new AlertDialog.Builder(AgregarNotaActivity.this);
                builder.setTitle("Selecciona de dónde quieres insertar una imagen");
                builder.setItems(opciones, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opciones[i].equals("Seleccionar imagen del sistema")){
                            esImagen=true;
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(AgregarNotaActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                            }
                            else{
                                seleccionarImagen();
                            }
                        }
                        else if(opciones[i].equals("Tomar una foto")){
                            esImagen=true;
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(AgregarNotaActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE_PERMISSION);
                            }
                            else{
                                tomarCaptura();
                            }
                        }
                        else{
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    private View.OnClickListener btnVideoNotaOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] opciones = {"Seleccionar video del sistema","Grabar un video", "Cancelar" };
                AlertDialog.Builder builder = new AlertDialog.Builder(AgregarNotaActivity.this);
                builder.setTitle("Selecciona de dónde quieres insertar un video");
                builder.setItems(opciones, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opciones[i].equals("Seleccionar video del sistema")){
                            esImagen=false;
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(AgregarNotaActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                            }
                            else{
                                seleccionarVideo();
                            }
                        }
                        else if(opciones[i].equals("Grabar un video")){
                            esImagen=false;
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(AgregarNotaActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE_PERMISSION);
                            }
                            else{
                                tomarCaptura();
                            }
                        }
                        else{
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    private View.OnClickListener btnAudioNotaOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int icon;
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AgregarNotaActivity.this,
                            new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
                }
                else{
                    if(grabando==false){
                        icon=R.drawable.ic_stop;
                        tomarAudio();
                        grabando=true;
                    }
                    else{
                        icon=R.drawable.mic_2_64x64;
                        pararGrabacion();
                        grabando=false;
                    }
                    btnVoice.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),icon));
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        db.close();
        if(!notaGuardada){
            for (String s:imgsCapturadasPaths) {
                new File(s).delete();
            }
            for (String s:audiosCapturadosPaths){
                new File(s).delete();
            }
        }
        super.onDestroy();
    }
}