package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.agenda.NotasDB.NotasDatabase;

public class NotasDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notas.db";
    private static final String CREATE_NOTAS_TABLE = "CREATE TABLE " + NotasDatabase.TABLE_NAME +
            "( " + NotasDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NotasDatabase.COLUMN_NAME_COL1 + " text," +
            NotasDatabase.COLUMN_NAME_COL2 + " text," +
            NotasDatabase.COLUMN_NAME_COL3 + " text," +
            NotasDatabase.COLUMN_NAME_COL4 + " text," +
            NotasDatabase.COLUMN_NAME_COL5 + " text," +
            NotasDatabase.COLUMN_NAME_COL6 + " text)";
    private static final String DELETE_NOTAS_TABLE = "DROP TABLE IF EXISTS " + NotasDatabase.TABLE_NAME;
    public NotasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersio, int newVersion) {
        db.execSQL(DELETE_NOTAS_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
