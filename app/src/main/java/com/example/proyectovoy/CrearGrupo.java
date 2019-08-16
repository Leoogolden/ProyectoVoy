package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrearGrupo extends Fragment implements View.OnClickListener {
    String IP;
    int idUsr;
    int idgru;
    String NombreGrupo = "";
    String DescripcionGrupo = "";
    Button btn;
    EditText Nom;
    EditText Desc;
    View vistadevuelve;
    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;
    Bundle DatosRecibidos;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        Log.d("entra", "entro5");
        vistadevuelve = inflador.inflate(R.layout.fragment_crear_grupo, grupo, false);
        IP = getString(R.string.IP);
        DatosRecibidos = getArguments();
        idUsr = DatosRecibidos.getInt("IdUsuario");
        Nom = vistadevuelve.findViewById(R.id.txtNombre);
        Desc = vistadevuelve.findViewById(R.id.txtDescripcion);
        btn = vistadevuelve.findViewById(R.id.btnCrearGrupo);
        btn.setOnClickListener(this);


        return vistadevuelve;
    }

    @Override
    public void onClick(View v) {

        NombreGrupo = Nom.getText().toString();
        DescripcionGrupo = Desc.getText().toString();
        Log.d("AccesoAPI6", NombreGrupo + " " + DescripcionGrupo + " " + idUsr);
        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();


        Log.d("AccesoAPI6", "funco papa");
    }

    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa" + NombreGrupo + " " + DescripcionGrupo + " " + idUsr);
                URL rutatlantica = new URL(IP + "Grupos/CrearGrupo/" + NombreGrupo + "/" + DescripcionGrupo + "/" + idUsr);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI6", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI6", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Grupos
            super.onPostExecute(aVoid);

            Bundle elgrandote = new Bundle();
            elgrandote.putInt("idgru", idgru);


            elgrandote.putBundle("usuario", DatosRecibidos);
            InvitarAlGrupo AgregarUsuarios;
            AgregarUsuarios = new InvitarAlGrupo();
            AgregarUsuarios.setArguments(elgrandote);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, AgregarUsuarios);
            Transacciones.commit();
        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        idgru = parseador.parse(streamLeido).getAsInt();

    }
}
