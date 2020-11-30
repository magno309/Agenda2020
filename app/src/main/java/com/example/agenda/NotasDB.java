package com.example.agenda;

import android.provider.BaseColumns;

public final class NotasDB {

    private NotasDB() {}

    public static class NotasDatabase implements BaseColumns{
        public static final String TABLE_NAME = "Notas";
        public static final String COLUMN_NAME_COL1 = "Nombre";
        public static final String COLUMN_NAME_COL2 = "Descripcion";
        public static final String COLUMN_NAME_COL3 = "UriFoto";
        public static final String COLUMN_NAME_COL4 = "UriVideo";
        public static final String COLUMN_NAME_COL5 = "UriVoz";
        public static final String COLUMN_NAME_COL6 = "FechaHora";
    }

    public static class TareasDatabase implements BaseColumns{
        public static final String TABLE_NAME = "Tareas";
        public static final String COLUMN_NAME_COL1 = "Nombre";
        public static final String COLUMN_NAME_COL2 = "Descripcion";
        public static final String COLUMN_NAME_COL3 = "UriFoto";
        public static final String COLUMN_NAME_COL4 = "UriVideo";
        public static final String COLUMN_NAME_COL5 = "UriVoz";
    }

}

