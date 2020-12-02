package com.example.agenda;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NotasFragment extends Fragment implements ListenerNotaI {

    NotasDBHelper dbHelper;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorNotas adaptadorNotas;
    MainActivity mainActivity;
    SQLiteDatabase db;
    List<Notas> listaNotas;

    public NotasFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Notas.
     */
    // TODO: Rename and change types and number of parameters
    public static NotasFragment newInstance(/*String param1, String param2*/) {
        NotasFragment fragment = new NotasFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public void notaRecyclerViewClicked(Notas nota, int position) {
        //Log.println(Log.INFO,"baseDeDatosNotass","idNota: "+nota.getNotaId()+" nombre: "+nota.getNombre() + " position:"+position);
        Intent intent = new Intent(getActivity(), AgregarNotaActivity.class);
        intent.putExtra("idNota", nota.getNotaId());
        getActivity().startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvNotas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        dbHelper = new NotasDBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        //dbHelper.onUpgrade(db,0,1); //elimina bd
        listaNotas = new ArrayList<Notas>();
        //Recordar ordenar por fecha desc
        Cursor c1 = db.query(NotasDB.NotasDatabase.TABLE_NAME, null, null, null, null, null, null);
        if (c1 != null && c1.getCount() != 0) {
            listaNotas.clear();
            while (c1.moveToNext()) {
                Notas nota = new Notas();
                nota.setNotaId(c1.getInt(c1.getColumnIndex(NotasDB.NotasDatabase._ID)));
                nota.setNombre(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL1)));
                nota.setDescripcion(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL2)));
                nota.setUriFoto(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL3)));
                nota.setUriVideo(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL4)));
                nota.setUriVoz(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL5)));
                nota.setFechaHora(c1.getString(c1.getColumnIndex(NotasDB.NotasDatabase.COLUMN_NAME_COL6)));
                //Log.println(Log.INFO,"baseDeDatosNotass",nota.getNotaId()+" "+nota.getNombre());
                listaNotas.add(nota);
            }
        }
        c1.close();
        adaptadorNotas = new AdaptadorNotas(getActivity(), listaNotas, this);
        recyclerView.setAdapter(adaptadorNotas);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }
}