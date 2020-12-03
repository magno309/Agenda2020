package com.example.agenda;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorRecordatorios extends RecyclerView.Adapter<AdaptadorRecordatorios.Viewholder> {


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    public void onBindViewHolder(@NonNull AdaptadorRecordatorios holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
