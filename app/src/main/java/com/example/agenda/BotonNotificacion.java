package com.example.agenda;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class BotonNotificacion extends BroadcastReceiver {

    TareasDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new TareasDBHelper(context);
        db = dbHelper.getWritableDatabase();
        String Id = intent.getExtras().getString("Id");
        Toast.makeText(context, "Id: " + Id, Toast.LENGTH_LONG).show();
        if(Id != null){
            Cursor c1 = db.query(NotasDB.TareasDatabase.TABLE_NAME, null, NotasDB.NotasDatabase._ID + " = " + Id, null, null, null, null);
            if (c1 != null && c1.getCount() != 0) {
                ContentValues values = new ContentValues();
                values.put(NotasDB.TareasDatabase.COLUMN_NAME_COL6, "false");
                if(db.update(NotasDB.TareasDatabase.TABLE_NAME, values, NotasDB.TareasDatabase._ID + " = " + Id, null)!=-1){
                    Toast.makeText(context, "Nota actualizada correctamente",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, "Algo salió mal en el select", Toast.LENGTH_LONG).show();
            }
        }
    }
}
