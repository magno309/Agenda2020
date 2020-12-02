package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TareasDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tareas.db";

    private static final String CREATE_TAREAS_TABLE = "CREATE TABLE " + NotasDB.TareasDatabase.TABLE_NAME +
            "( " + NotasDB.TareasDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NotasDB.TareasDatabase.COLUMN_NAME_COL1 + " text," +
            NotasDB.TareasDatabase.COLUMN_NAME_COL2 + " text," +
            NotasDB.TareasDatabase.COLUMN_NAME_COL3 + " text," +
            NotasDB.TareasDatabase.COLUMN_NAME_COL4 + " text," +
            NotasDB.TareasDatabase.COLUMN_NAME_COL5 + " text)";

    private static final String DELETE_NOTAS_TABLE = "DROP TABLE IF EXISTS " + NotasDB.TareasDatabase.TABLE_NAME;

    public TareasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAREAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_NOTAS_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
