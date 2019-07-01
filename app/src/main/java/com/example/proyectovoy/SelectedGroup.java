package com.example.proyectovoy;

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
import java.util.ArrayList;
import java.util.List;

public class SelectedGroup extends Fragment {

    public String Nombre;
    public String Descripcion;
    public int idGrupo;

    TextView NombreGrupo;
    TextView DescripcionGrupo;
    ListView ListaIntegrantes;
    ImageView FotoGrupo;
    Grupos SelectedGroup;
    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    Bundle DatosRecibidos;

    View vistadevuelve;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_group, grupo, false);
        Log.d("onclick", "entra3");

        DatosRecibidos = getArguments();
        NombreGrupo = vistadevuelve.findViewById(R.id.NombreGrupo);
        DescripcionGrupo = vistadevuelve.findViewById(R.id.DescripcionGrupo);
        ListaIntegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
        FotoGrupo = vistadevuelve.findViewById(R.id.ImagenGrupo);
        Log.d("onclick", "entra4");

        NombreGrupo.setText(Nombre);
        DescripcionGrupo.setText(Descripcion);
        Log.d("onclick", "entra5");
        tareaAsincronica miTarea = new tareaAsincronica();


        miTarea.execute();
        SelectedGroup = new Grupos(idGrupo, Nombre, Descripcion);
        NombreGrupo.setText(SelectedGroup.Nombre);
        DescripcionGrupo.setText(SelectedGroup.Descripcion);

        return vistadevuelve;
    }

    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.34:2073/api/Grupos/MiembrosGrupo/" + idGrupo);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI3", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI3", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    Log.d("AccesoAPI3", "conexioion ok seguimos");

                    ProcessJSONLeido(lectorrespuesta);
                    Log.d("AccesoAPI3", "conexion ok daaale");


                } else {
                    Log.d("AccesoAPI3", "Error en la conexion");
                }
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
                DatosLista.add(ListaDeUsuarios.get(i).Nombre);

            }
            ArrayAdapter<String> miembrosAdapter;
            miembrosAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DatosLista);
            ListView ListaMiembros;
            ListaMiembros = vistadevuelve.findViewById(R.id.ListaIntegrantes);
            ListaMiembros.setAdapter(miembrosAdapter);
        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();
        JsonArray objetojson;
        objetojson = parseador.parse(streamLeido).getAsJsonArray();
        Usuarios user;
        user = new Usuarios();
        for(int i = 0; i < objetojson.size(); i++){
            user.IdUsuario = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsInt();
            user.Nombre = ((JsonObject)objetojson.get(i)).get("Nombre").getAsString();
            user.Mail = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsString();
            user.NombreUsuario = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsString();
            user.Contra = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsString();
            user.NroTel = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsInt();
            user.Edad = ((JsonObject)objetojson.get(i)).get("IdUsuario").getAsInt();
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
