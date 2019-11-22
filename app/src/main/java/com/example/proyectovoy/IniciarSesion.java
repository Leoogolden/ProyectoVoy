package com.example.proyectovoy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class IniciarSesion extends AppCompatActivity {


    Usuarios user;
    String Nombreu;
    String pas;
    String IP;
    Boolean entraono = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        Log.d("aver", getString(R.string.IP));
        IP = getString(R.string.IP);
    }


    public void CheckLogIn(View view) {
        EditText nombraso = findViewById(R.id.nombreusuariologin);
        EditText pwd = findViewById(R.id.contrasenialogin);
        Nombreu = nombraso.getText().toString();
        pas = pwd.getText().toString();
        if (Nombreu.matches("") || pas.matches("")) {
            Toast.makeText(IniciarSesion.this, "Alguno de los campos esta vacio, reintentar", Toast.LENGTH_SHORT).show();
        } else {
            tareaAsincronicaLogIn tar = new tareaAsincronicaLogIn();
            tar.execute();
        }

    }

    public void ForgPass(View view) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(IniciarSesion.this, RecupContra.class);
        startActivity(ActividadDestino);
    }

    public void ToReg(View view) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(IniciarSesion.this, Registro.class);
        startActivity(ActividadDestino);
    }

    private class tareaAsincronicaLogIn extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(IP + "Usuario/" + Nombreu + "/" + pas);
                Log.d("AccesoAPI3", rutatlantica.toString());
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI3", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI3", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, StandardCharsets.UTF_8);
                    Log.d("AccesoAPI3", "conexioion ok seguimos");

                    ProcessJSONLeido(lectorrespuesta);
                    Log.d("AccesoAPI3", "conexion ok daaale");
                } else {
                    Log.d("AccesoAPI3", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI3", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (entraono) {
                Bundle UsuarioBundle = new Bundle();
                UsuarioBundle.putInt("IdUsuario", user.IdUsuario);
                UsuarioBundle.putString("Nombre", user.Nombre);
                UsuarioBundle.putString("Mail", user.Mail);
                UsuarioBundle.putString("NombreUsuario", user.NombreUsuario);
                UsuarioBundle.putString("Contra", user.Contra);
                UsuarioBundle.putInt("NroTel", user.NroTel);
                UsuarioBundle.putInt("Edad", user.Edad);
                Log.d("valoruser", "" + UsuarioBundle.getString("IdUsuario"));
                Toast.makeText(IniciarSesion.this, " Logeandose...", Toast.LENGTH_SHORT).show();
                Intent ActividadDestino;
                ActividadDestino = new Intent(IniciarSesion.this, Home.class);
                ActividadDestino.putExtras(UsuarioBundle);
                startActivity(ActividadDestino);
            } else {
                Toast.makeText(IniciarSesion.this, "Contraseña o usuariologeado incorrecto", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();
        JsonObject objetojson;
        objetojson = parseador.parse(streamLeido).getAsJsonObject();

        user = new Usuarios();
        if (objetojson.get("IdUsuario").getAsInt() != -1) {
            entraono = true;
            user.IdUsuario = objetojson.get("IdUsuario").getAsInt();
            user.Nombre = objetojson.get("Nombre").getAsString();
            user.Mail = objetojson.get("Mail").getAsString();
            user.NombreUsuario = objetojson.get("NombreUsuario").getAsString();
            user.Contra = objetojson.get("Contraseña").getAsString();
            user.NroTel = objetojson.get("NroTelefono").getAsInt();
            user.Edad = objetojson.get("Edad").getAsInt();
        } else {
            entraono = false;
        }
        Log.d("HolaHola3", "que ondaa " + objetojson.get("IdUsuario").getAsInt() + "  " + entraono);


    }
}
