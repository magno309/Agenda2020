package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorNotas extends RecyclerView.Adapter<AdaptadorNotas.Viewholder> {

    private LayoutInflater inflador;
    private List<Notas> listaNotas;
    private View.OnClickListener onClickListener;
    private NotasDBHelper dbHelper;
    private SQLiteDatabase db;
    Context context;

    public AdaptadorNotas(Context contexto, List<Notas> listaNotas){
        this.inflador = (LayoutInflater)
                contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaNotas = listaNotas;
        this.context = contexto;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.nota_cardview, parent, false);
        v.setOnClickListener(this.onClickListener);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Notas nota = listaNotas.get(position);
        holder.nombre.setText(nota.getNombre());
        holder.descripcion.setText(nota.getDescripcion());
        holder.fechaHora.setText(nota.getFechaHora());
        holder.eliminarNota.setImageResource(R.drawable.ic_delete);
        holder.eliminarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Notas nota = listaNotas.get(position);
                final int idNota = nota.getNotaId();
                dbHelper = new NotasDBHelper(context);
                db = dbHelper.getWritableDatabase();
                db.delete(NotasDB.NotasDatabase.TABLE_NAME, NotasDB.NotasDatabase._ID + " = " + idNota,null);
                notifyItemRangeChanged(position, listaNotas.size());
                listaNotas.remove(position);
                notifyItemRemoved(position);
                db.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public View separador1;
        public TextView descripcion;
        public View separador2;
        public TextView fechaHora;
        public ImageView eliminarNota;

        public Viewholder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txt_Nombre_Nota);
            separador1 = itemView.findViewById(R.id.separador1);
            descripcion = itemView.findViewById(R.id.txt_Descripción_Nota);
            separador2 = itemView.findViewById(R.id.separador2);
            fechaHora = itemView.findViewById(R.id.txt_FechaYHora_Nota);
            eliminarNota = itemView.findViewById(R.id.btn_eliminar_nota);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
