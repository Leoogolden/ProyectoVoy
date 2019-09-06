package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrearActiv extends Fragment {
    View vistadevuelve;
    Bundle usuariologeado = new Bundle();

    Usuarios user = new Usuarios();

    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_crear_activ, grupo, false);


        usuariologeado = getArguments();
        user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));
        Log.d("keloke", user.Nombre + " " + user.IdUsuario);

        Button siguiente = vistadevuelve.findViewById(R.id.ConfirmarDatosEvento);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return vistadevuelve;
    }

    private class tareaCrarActiv extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
//                Log.d("AccesoAPI6", "aaaa" + NombreGrupo + " " + DescripcionGrupo + " " + idUsr);
//                URL rutatlantica = new URL(IP + "Grupos/CrearGrupo/" + NombreGrupo + "/" + DescripcionGrupo + "/" + idUsr);
//                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
//                conexion.setRequestMethod("POST");
//                conexion.setRequestProperty("Content-Type", "application/json");
//                conexion.setRequestProperty("charset", "utf-8");
//                Log.d("AccesoAPI6", "Me conecto");
//                if (conexion.getResponseCode() == 200) {
//                    Log.d("AccesoAPI6", "conexion ok");
//                    InputStream cuerporesspuesta = conexion.getInputStream();
//                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
//                    ProcessJSONLeido(lectorrespuesta);
//                } else {
//                    Log.d("AccesoAPI6", "Error en la conexion " + conexion.getResponseCode());
//                }
//                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI6", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Grupos
            super.onPostExecute(aVoid);



            InvitarAlGrupo AgregarUsuarios;
            AgregarUsuarios = new InvitarAlGrupo();
//            AgregarUsuarios.setArguments(elgrandote);
            ManejadorDeFragments = getFragmentManager();
            Transacciones = ManejadorDeFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, AgregarUsuarios);
            Transacciones.commit();
        }
    }
    public void ProcessJSONLeido(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

//        idgru = parseador.parse(streamLeido).getAsInt();

    }

}
