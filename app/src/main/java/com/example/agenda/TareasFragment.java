package com.example.agenda;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    TareasDBHelper dbHelper;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public static AdaptadorTareas adaptadorTareas;
    MainActivity mainActivity;
    SQLiteDatabase db;
    public static List<Tareas> listaTareas;

    public TareasFragment() {
        // Required empty public constructor
    }

    public static TareasFragment newInstance() {
        TareasFragment fragment = new TareasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tareas, container, false);
        recyclerView = view.findViewById(R.id.rvTareas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        dbHelper = new TareasDBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        listaTareas = new ArrayList<>();

        Cursor cursor = db.query(NotasDB.TareasDatabase.TABLE_NAME, null, null, null, null, null, null);
        if(cursor != null && cursor.getCount() != 0){
            listaTareas.clear();
            while(cursor.moveToNext()){
                Tareas tarea = new Tareas();
                tarea.setTareaId(cursor.getInt(cursor.getColumnIndex(NotasDB.TareasDatabase._ID)));
                tarea.setNombre(cursor.getString(cursor.getColumnIndex(NotasDB.TareasDatabase.COLUMN_NAME_COL1)));
                tarea.setDescripcion(cursor.getString(cursor.getColumnIndex(NotasDB.TareasDatabase.COLUMN_NAME_COL2)));
                if(cursor.getString(cursor.getColumnIndex(NotasDB.TareasDatabase.COLUMN_NAME_COL6)).equals("true")){
                    tarea.setActivo(true);
                }else{
                    tarea.setActivo(false);
                }
                /*tarea.setUriFoto(cursor.getString(cursor.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL3)));
                tarea.setUriVideo(cursor.getString(cursor.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL4)));
                tarea.setUriVoz(cursor.getString(cursor.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL5)));*/
                listaTareas.add(tarea);
            }
            cursor.close();
            adaptadorTareas = new AdaptadorTareas(getActivity(), listaTareas);
            adaptadorTareas.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AgregarTareaActivity.class);
                intent.putExtra("IdTarea", recyclerView.getChildAdapterPosition(v));
                getActivity().startActivity(intent);
            });
        }
        recyclerView.setAdapter(adaptadorTareas);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }
}