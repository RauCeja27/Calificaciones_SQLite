package com.example.calificaciones;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etMatricula, etNombre, etMateria, etCarrera, etParcial01, etParcial02, etParcial03;
    Button btnInsertar, btnBuscar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMatricula = findViewById(R.id.etMatricula);
        etNombre = findViewById(R.id.etNombre);
        etMateria = findViewById(R.id.etMateria);
        etCarrera = findViewById(R.id.etCarrera);
        etParcial01 = findViewById(R.id.etParcial01);
        etParcial02 = findViewById(R.id.etParcial02);
        etParcial03 = findViewById(R.id.etParcial03);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        baseDatos Ayudabd = new baseDatos(getApplicationContext());

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = Ayudabd.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(baseDatos.Datostabla.COLUMN_MATRICULA, etMatricula.getText().toString());
                valores.put(baseDatos.Datostabla.COLUMN_NOMBRE, etNombre.getText().toString());
                valores.put(baseDatos.Datostabla.COLUMN_MATERIA, etMateria.getText().toString());
                valores.put(baseDatos.Datostabla.COLUMN_CARRERA, etCarrera.getText().toString());

                long idGuardado = db.insert(baseDatos.Datostabla.NAME_TABLE, null, valores);
                Toast.makeText(getApplicationContext(), "Se guardó el dato " + idGuardado, Toast.LENGTH_LONG).show();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = Ayudabd.getReadableDatabase();
                String[] datos = {etMatricula.getText().toString()};
                Cursor c = db.query(baseDatos.Datostabla.NAME_TABLE, null, baseDatos.Datostabla.COLUMN_MATRICULA + "=?", datos, null, null, null);
                if (c.moveToFirst()) {
                    etNombre.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_NOMBRE)));
                    etMateria.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_MATERIA)));
                    etCarrera.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_CARRERA)));
                    etParcial01.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_PARCIAL1)));
                    etParcial02.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_PARCIAL2)));
                    etParcial03.setText(c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_PARCIAL3)));
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontró el alumno", Toast.LENGTH_LONG).show();
                }
                c.close();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = Ayudabd.getWritableDatabase();
                ContentValues valores = new ContentValues();
                String matricula = etMatricula.getText().toString();
                Cursor c = db.query(baseDatos.Datostabla.NAME_TABLE, null, baseDatos.Datostabla.COLUMN_MATRICULA + "=?", new String[]{matricula}, null, null, null);

                if (c.moveToFirst()) {
                    if (etParcial01.getText().toString().isEmpty() && etParcial02.getText().toString().isEmpty() && etParcial03.getText().toString().isEmpty()) {
                        valores.put(baseDatos.Datostabla.COLUMN_NOMBRE, etNombre.getText().toString());
                        valores.put(baseDatos.Datostabla.COLUMN_MATERIA, etMateria.getText().toString());
                        valores.put(baseDatos.Datostabla.COLUMN_CARRERA, etCarrera.getText().toString());
                    } else {
                        if (!etParcial01.getText().toString().isEmpty()) {
                            valores.put(baseDatos.Datostabla.COLUMN_PARCIAL1, etParcial01.getText().toString());
                        }
                        if (!etParcial02.getText().toString().isEmpty()) {
                            if (c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_PARCIAL1)) != null) {
                                valores.put(baseDatos.Datostabla.COLUMN_PARCIAL2, etParcial02.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "Debe registrar primero la calificación del primer parcial", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        if (!etParcial03.getText().toString().isEmpty()) {
                            if (c.getString(c.getColumnIndex(baseDatos.Datostabla.COLUMN_PARCIAL2)) != null) {
                                valores.put(baseDatos.Datostabla.COLUMN_PARCIAL3, etParcial03.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "Debe registrar primero la calificación del segundo parcial", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                    int count = db.update(baseDatos.Datostabla.NAME_TABLE, valores, baseDatos.Datostabla.COLUMN_MATRICULA + "=?", new String[]{matricula});
                    Toast.makeText(getApplicationContext(), "Actualizado " + count, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontró el alumno", Toast.LENGTH_LONG).show();
                }
                c.close();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = Ayudabd.getWritableDatabase();
                String[] datos = {etMatricula.getText().toString()};
                int count = db.delete(baseDatos.Datostabla.NAME_TABLE, baseDatos.Datostabla.COLUMN_MATRICULA + "=?", datos);
                Toast.makeText(getApplicationContext(), "Eliminado " + count, Toast.LENGTH_LONG).show();
            }
        });
    }
}
