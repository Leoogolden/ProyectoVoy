package com.example.proyectovoy;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SelectedGroup extends Fragment {


    TextView NombreGrupo;
    TextView DescripcionGrupo;
    ListView ListaIntegrantes;
    ImageView FotoGrupo;
    Grupos SelectedGroup;
    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    int idGrupo;
    View vistadevuelve;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_group, grupo, false);
        Log.d("onclick", "entra3");
        Bundle DatosRecibidos = getArguments();
        String Nombre = DatosRecibidos.getString("Nombre");
        String Descripcion = DatosRecibidos.getString("Descripcion");
        Log.d("onclick", "entra4");
        idGrupo = DatosRecibidos.getInt("idGrupo");

        NombreGrupo = vistadevuelve.findViewById(R.id.NombreGrupo);
        DescripcionGrupo = vistadevuelve.findViewById(R.id.DescripcionGrupo);
        ListaIntegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
        FotoGrupo = vistadevuelve.findViewById(R.id.ImagenGrupo);
        Log.d("onclick", "entra5");

        NombreGrupo.setText(Nombre);
        DescripcionGrupo.setText(Descripcion);
        Log.d("onclick", "entra5");
        SelectedGroup = new Grupos(idGrupo, Nombre, Descripcion);
        NombreGrupo.setText(SelectedGroup.Nombre);
        DescripcionGrupo.setText(SelectedGroup.Descripcion);
        tareaAsincronica miTarea = new tareaAsincronica();


        miTarea.execute();


        return vistadevuelve;
    }

    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.22:2073/api/Grupos/MiembrosGrupo/" + idGrupo);
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
            Log.d("HolaHola3", "ueso, que pasoa");


            ArrayList<String> DatosLista = new ArrayList<String>();
            int lenght = ListaDeUsuarios.size();
            for (int i = 0; i < lenght - 1; i++) {
                Log.d("HolaHola3", "ueso, que pasoa3" + ListaDeUsuarios.get(i).Nombre);
                DatosLista.add(ListaDeUsuarios.get(i).NombreUsuario);
            }
            ArrayAdapter<String> miAdaptador;
            miAdaptador = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, DatosLista);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.ListaIntegrantes);
            ListaMiembros.setAdapter(miAdaptador);


//            ArrayAdapter<String> miembrosAdapter;
//            miembrosAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DatosLista);
//            ListView ListaMiembros;
//            ListaMiembros = vistadevuelve.findViewById(R.id.ListaIntegrantes);
//            ListaMiembros.setAdapter(miembrosAdapter);
        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();
        JsonArray objetojson;
        objetojson = parseador.parse(streamLeido).getAsJsonArray();

        for (int i = 0; i < objetojson.size(); i++) {
            Usuarios user;
            user = new Usuarios();
            JsonObject objPersona;
            objPersona = objetojson.get(i).getAsJsonObject();
            user.IdUsuario = objPersona.get("IdUsuario").getAsInt();
            user.Nombre = objPersona.get("Nombre").getAsString();
            user.Mail = objPersona.get("Mail").getAsString();
            user.NombreUsuario = objPersona.get("NombreUsuario").getAsString();
            user.Contra = objPersona.get("Contraseña").getAsString();
            user.NroTel = objPersona.get("NroTelefono").getAsInt();
            user.Edad = objPersona.get("Edad").getAsInt();
            Log.d("HolaHola3", "que ondaa " + user.NombreUsuario);
            ListaDeUsuarios.add(user);
        }
//        JsonReader JSONleido = new JsonReader(streamLeido);
//        try {
//            JSONleido.beginArray();
//            while (JSONleido.hasNext()) {
//                Log.d("uoso3", "1");
//                JSONleido.beginObject();
//                Log.d("uoso3", "2");
//
//                while (JSONleido.hasNext()) {
//                    String NomeDuElemento = JSONleido.nextName();
//                    Log.d("asd", NomeDuElemento);
//                    Log.d("uoso3", "3");
//                    if (NomeDuElemento.equals("IdUsuario")) {
//                        Log.d("uoso3", "4");
//                        user.IdUsuario = JSONleido.nextInt();
//                    } else if (NomeDuElemento.equals("Nombre")) {
//                        Log.d("uoso3", "5");
//                        user.Nombre = JSONleido.nextString();
//                    } else if (NomeDuElemento.equals("Mail")) {
//                        Log.d("uoso3", "6");
//                        user.Mail = JSONleido.nextString();
//                    } else if (NomeDuElemento.equals("NombreUsuario")) {
//                        Log.d("uoso3", "7");
//                        user.NombreUsuario = JSONleido.nextString();
//                    } else if (NomeDuElemento.equals("Contraseña")) {
//                        Log.d("uoso3", "8");
//                        user.Contra = JSONleido.nextString();
//                    } else if (NomeDuElemento.equals("NroTelefono")) {
//                        Log.d("uoso3", "9");
//                        user.NroTel = JSONleido.nextInt();
//                    } else if (NomeDuElemento.equals("Edad")) {
//                        Log.d("uoso3", "10");
//                        user.Edad = JSONleido.nextInt();
//                    }
//                }
//
//                Log.d("uoso3", "" + user);
//                ListaDeUsuarios.add(user);
//                JSONleido.endObject();
//            }
//            JSONleido.endArray();
//        } catch (Exception error) {
//            Log.d("LecturaJSON3", "" + error);
//        }
    }
}
