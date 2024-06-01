package com.example.calificaciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class baseDatos extends SQLiteOpenHelper {

    public static abstract class Datostabla implements BaseColumns {
        public static final String NAME_TABLE = "grupo";
        public static final String COLUMN_MATRICULA = "matricula";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_MATERIA = "materia";
        public static final String COLUMN_CARRERA = "carrera";
        public static final String COLUMN_PARCIAL1 = "parcial1";
        public static final String COLUMN_PARCIAL2 = "parcial2";
        public static final String COLUMN_PARCIAL3 = "parcial3";

        private static final String CREATE_TABLE = "CREATE TABLE " + NAME_TABLE + " (" +
                COLUMN_MATRICULA + " TEXT PRIMARY KEY, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_MATERIA + " TEXT, " +
                COLUMN_CARRERA + " TEXT, " +
                COLUMN_PARCIAL1 + " TEXT, " +
                COLUMN_PARCIAL2 + " TEXT, " +
                COLUMN_PARCIAL3 + " TEXT);";

        private static final String SQL_DELETE = "DROP TABLE IF EXISTS " + NAME_TABLE;
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Calificaciones.db";

    public baseDatos(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Datostabla.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Datostabla.SQL_DELETE);
        onCreate(db);
    }
}
