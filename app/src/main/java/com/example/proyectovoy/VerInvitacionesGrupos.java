package com.example.proyectovoy;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerInvitacionesGrupos extends Fragment {
    View vistadevuelve;
    ArrayList<InvitacionesGrupos> ListaDeInvitacionesGupos = new ArrayList<>();
    int idUsr;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_lista_de_grupos, grupo, false);




        return vistadevuelve;
    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.80:2073/api/Invitacion/" + idUsr);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI", "Error en la conexion");
                }
            } catch (Exception error) {
                Log.d("AccesoAPI", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola", "ueso, que pasoa");
            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
            Log.d("HolaHola", "ueso, que pasoa2");

            InvitacionesGruposListAdapter adapter;
            adapter = new InvitacionesGruposListAdapter(getActivity().getBaseContext(), R.layout.lista_invitaciones_grupos, ListaDeInvitacionesGupos);
            ListView lista = vistadevuelve.findViewById(R.id.ListaInvitacionGrupo);
            lista.setAdapter(adapter);
        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginArray();
            while (JSONleido.hasNext()) {
                Log.d("uoso", "1");
                JSONleido.beginObject();
                Log.d("uoso", "2");
                InvitacionesGrupos Invitaciones;
                Invitaciones = new InvitacionesGrupos();
                while (JSONleido.hasNext()) {
                    String NomeDuElemento = JSONleido.nextName();
                    Log.d("uoso", "3");
                    if (NomeDuElemento.equals("QuienInvita")) {
                        Log.d("uoso", "4");
                        Invitaciones.setQuienInvita(JSONleido.nextString());
                    } else if (NomeDuElemento.equals("NmbreGrupo")) {
                        Log.d("uoso", "5");
                        Invitaciones.setGrupo(JSONleido.nextString());
                    }
                }
                Log.d("uoso", "" + Invitaciones);
                ListaDeInvitacionesGupos.add(Invitaciones);
                JSONleido.endObject();
            }
            JSONleido.endArray();
        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }
    }
}
