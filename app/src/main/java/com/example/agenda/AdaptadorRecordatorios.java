package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorRecordatorios extends RecyclerView.Adapter<AdaptadorRecordatorios.Viewholder> {

    private LayoutInflater inflador;
    private List<Recordatorios> listaRecordatorios;
    private TareasDBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public AdaptadorRecordatorios(Context contexto, List<Recordatorios> listaRecordatorios){
        this.inflador = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = contexto;
        this.listaRecordatorios = listaRecordatorios;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflador.inflate(R.layout.recordatorio_vista, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Recordatorios recordatorios = listaRecordatorios.get(position);
        holder.Fecha.setText(recordatorios.getFecha());
        holder.Hora.setText(recordatorios.getHora());
    }

    @Override
    public int getItemCount() {
        return listaRecordatorios.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        TextView Fecha;
        TextView Hora;

        public Viewholder(View itemView) {
            super(itemView);
            Fecha = itemView.findViewById(R.id.txtFechaRecordatoio);
            Hora = itemView.findViewById(R.id.txtHoraRecordatorio);
        }
    }
}
