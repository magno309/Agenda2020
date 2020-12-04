package com.example.agenda;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorImagen extends RecyclerView.Adapter<AdaptadorImagen.Viewholder> {

    private LayoutInflater inflador;
    private List<String> listaPaths; //listaDescripciones;
    private Context context;

    public AdaptadorImagen(Context context, List<String> listaPaths, List<String> listaDescripciones){
        this.inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.listaPaths =listaPaths;
        //this.listaDescripciones = listaDescripciones;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.imagen_view, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(listaPaths.get(position)));
        //holder.imageView.setImageURI(listaPaths.get(position));
        Log.println(Log.INFO, "rutas", "Bind: "+listaPaths.get(position));
        //holder.descripcion.setText(listaDescripciones.get(position));
        /*holder.layoutImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaPaths.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        LinearLayout layoutImagen;
        ImageView imageView;
        //EditText descripcion;

        public Viewholder(View itemView) {
            super(itemView);
            layoutImagen = itemView.findViewById(R.id.layout_imagen);
            imageView = itemView.findViewById(R.id.imgViewImagen);
            //descripcion = itemView.findViewById(R.id.txtDescripcionImagen);
        }
    }
}
