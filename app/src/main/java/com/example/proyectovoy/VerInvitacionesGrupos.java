package com.example.proyectovoy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VerInvitacionesGrupos extends Fragment {
    InvitacionesGrupos invita = new InvitacionesGrupos();
    Boolean Aceptaono;
    View vistadevuelve;
    ArrayList<InvitacionesGrupos> ListaDeInvitacionesGrupos = new ArrayList<>();
    int idUsr = 2;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_invitaciones_grupos, grupo, false);


        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();

        ListView listainvitaciones = vistadevuelve.findViewById(R.id.ListaInvitacionGrupo);
        listainvitaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog(ListaDeInvitacionesGrupos.get(position));
            }
        });

        return vistadevuelve;
    }

    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.63:2073/api/Invitacion/" + idUsr);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI2", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI2", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI2", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI2", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola2", "ueso, que pasoa");
            Log.d("HolaHola2", "ueso, que pasoa2");

            InvitacionesGruposListAdapter adapter;
            adapter = new InvitacionesGruposListAdapter(getActivity(), R.layout.lista_invitaciones_grupos, ListaDeInvitacionesGrupos);
            ListView lista = vistadevuelve.findViewById(R.id.ListaInvitacionGrupo);
            lista.setAdapter(adapter);
        }
    }

    public void openDialog(final InvitacionesGrupos grupo) {
        invita = grupo;
        new LovelyStandardDialog(getActivity(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorAccent)
                .setTitle("Invitacion al grupo")
                .setMessage("¿Desea unirse al grupo?")
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNegativeButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNeutralButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setPositiveButton("Aceptar Invitacion", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Aceptaono = true;
                        Log.d("olap", "" + invita.getIdInv());
                        tareaAsincronicaInvitacion asd = new tareaAsincronicaInvitacion();
                        asd.execute();
                        Toast.makeText(getActivity(), "positive clicked" + grupo.getQuienInvita(), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Rechazar Invitacion", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Aceptaono = false;
                        tareaAsincronicaInvitacion asd = new tareaAsincronicaInvitacion();
                        asd.execute();
                    }
                })
                .setNeutralButton("Volver a la lista", null)
                .show();
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {
        Log.d("uoso2", "entra");
        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            Log.d("uoso2", "entra1");

            JSONleido.beginArray();
            Log.d("uoso2", "entra2");

            while (JSONleido.hasNext()) {
                Log.d("uoso2", "1");
                JSONleido.beginObject();
                Log.d("uoso2", "2");
                InvitacionesGrupos Invitaciones;
                Invitaciones = new InvitacionesGrupos();
                while (JSONleido.hasNext()) {
                    String NomeDuElemento = JSONleido.nextName();
                    Log.d("uoso2", "3");
                    if (NomeDuElemento.equals("id")) {
                        Log.d("uoso2", "4");
                        Invitaciones.setIdInv(JSONleido.nextInt());
                    } else if (NomeDuElemento.equals("QuienInvita")) {
                        Log.d("uoso2", "5");
                        Invitaciones.setQuienInvita(JSONleido.nextString());

                    } else if (NomeDuElemento.equals("NombreGrupo")) {
                        Log.d("uoso2", "5");
                        Invitaciones.setGrupo(JSONleido.nextString());
                    }
                }
                Log.d("uoso2", "" + Invitaciones);
                ListaDeInvitacionesGrupos.add(Invitaciones);
                JSONleido.endObject();
            }
            JSONleido.endArray();
        } catch (IOException error) {
            Log.d("LecturaJSON", "" + error);
        }
    }

    private class tareaAsincronicaInvitacion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL rutatlantica = new URL("http://10.152.2.63:2073/api/Invitacion/Aceptar/" + invita.idInv + "/" + Aceptaono);
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
