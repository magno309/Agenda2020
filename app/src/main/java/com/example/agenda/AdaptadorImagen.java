package com.example.agenda;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
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
        String ruta = listaPaths.get(position);
        Log.println(Log.INFO, "rutas", "Bind: "+ruta);
        if(ruta.contains("mp4")){
            holder.imageView.setImageBitmap(ThumbnailUtils.createVideoThumbnail(ruta, MediaStore.Video.Thumbnails.MINI_KIND));
            holder.reproducirVideo.setVisibility(View.VISIBLE);
            holder.reproducirVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayVideo.class);
                    intent.putExtra("rutaVideo", ruta);
                    context.startActivity(intent);
                }
            });
        }
        else{
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(ruta));
            //holder.imageView.setImageURI(listaPaths.get(position));
            //holder.descripcion.setText(listaDescripciones.get(position));
            /*holder.layoutImagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return listaPaths.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        LinearLayout layoutImagen;
        ImageView imageView;
        ImageView reproducirVideo;
        //EditText descripcion;

        public Viewholder(View itemView) {
            super(itemView);
            layoutImagen = itemView.findViewById(R.id.layout_imagen);
            imageView = itemView.findViewById(R.id.imgViewImagen);
            reproducirVideo = itemView.findViewById(R.id.reproducirVideo);
            //descripcion = itemView.findViewById(R.id.txtDescripcionImagen);
        }
    }
}
