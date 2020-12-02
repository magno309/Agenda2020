package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.Viewholder> {

    private LayoutInflater inflador;
    private List<Tareas> listaTareas;
    private View.OnClickListener onClickListener;
    private TareasDBHelper dbHelper;
    private SQLiteDatabase db;
    Context context;

    public AdaptadorTareas (Context context, List<Tareas> listaTareas){
        this.inflador = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaTareas = listaTareas;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.tarea_cardview, parent, false);
        v.setOnClickListener(this.onClickListener);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTareas.Viewholder holder, int position) {
        Tareas tarea = listaTareas.get(position);
        holder.nombre.setText(tarea.getNombre());
        holder.descripcion.setText(tarea.getDescripcion());
        holder.fechaHora.setText(tarea.getFechaHora());
        holder.eliminarTarea.setImageResource(R.drawable.ic_delete);
        holder.eliminarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Tareas tarea = listaTareas.get(position);
                final int idTarea = tarea.getTareaId();
                dbHelper = new TareasDBHelper(context);
                db = dbHelper.getWritableDatabase();
                db.delete(NotasDB.TareasDatabase.TABLE_NAME, NotasDB.TareasDatabase._ID + " = " + idTarea,null);
                notifyItemRangeChanged(position, listaTareas.size());
                listaTareas.remove(position);
                notifyItemRemoved(position);
                db.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public View separador1;
        public TextView descripcion;
        public View separador2;
        public TextView fechaHora;
        public ImageView eliminarTarea;

        public Viewholder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreTareaCV);
            separador1 = itemView.findViewById(R.id.separador1T);
            descripcion = itemView.findViewById(R.id.txt_Descripcion_Tarea);
            separador2 = itemView.findViewById(R.id.separador2T);
            fechaHora = itemView.findViewById(R.id.txt_FechaYHora_Tarea);
            eliminarTarea = itemView.findViewById(R.id.btn_eliminar_tarea);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
