package com.example.proyectovoy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;

public class Registro extends AppCompatActivity {

    String IP;
    String usuarionombre = "", contrasenha = "", nombraso = "", stringmail = "";
    int EdadReal = 0, nrotel = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        IP = getString(R.string.IP);
    }

    public void ConfirmarRegistro(View vista) {
        EditText username = findViewById(R.id.NombreUsuario);
        EditText contra = findViewById(R.id.Contrasen);
        EditText mail = findViewById(R.id.Mail);
        EditText nombretext = findViewById(R.id.Nombre);
        EditText numtel = findViewById(R.id.NumTel);
        EditText edad = findViewById(R.id.Edad);

        usuarionombre = username.getText().toString();
        contrasenha = contra.getText().toString();
        stringmail = mail.getText().toString();
        nombraso = nombretext.getText().toString();
        nrotel = Integer.parseInt(numtel.getText().toString());
        EdadReal = Integer.parseInt(edad.getText().toString());
        tareaAsincronicaRegistro r = new tareaAsincronicaRegistro();
        r.execute();
        Intent ActividadDestino;
        ActividadDestino = new Intent(Registro.this, IniciarSesion.class);
        startActivity(ActividadDestino);

    }

    private class tareaAsincronicaRegistro extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL rutatlantica = new URL(IP +"usuariologeado/Register/" + usuarionombre + "/" + contrasenha + "/" + stringmail + "/" + nombraso + "/" +nrotel + "/" +EdadReal);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI7", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI7", "conexion ok");
//                    InputStream cuerporesspuesta = conexion.getInputStream();
//                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
//                    ProcessJSONLeido(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI7", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI7", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }
    }
}
