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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.proyectovoy.Grupos;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
        vistadevuelve = inflador.inflate(R.layout.selected_group, grupo, false);


        NombreGrupo = vistadevuelve.findViewById(R.id.NombreGrupo);
        DescripcionGrupo = vistadevuelve.findViewById(R.id.DescripcionGrupo);
        ListaIntegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
        FotoGrupo = vistadevuelve.findViewById(R.id.ImagenGrupo);

        SelectedGroup = new Grupos(datos.getInt("id"), datos.getString("Nombre"), datos.getString("Descripcion"));
        NombreGrupo.setText(SelectedGroup.Nombre);
        DescripcionGrupo.setText(SelectedGroup.Descripcion);

        return vistadevuelve;
    }

    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.80:2073/api/Grupos/MiembrosGrupo/" + idGrupo);
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
            ArrayList<String> DatosLista = new ArrayList<String>();
            int lenght = ListaDeUsuarios.size();
            for (int i = 0; i < lenght - 1; i++) {
                Log.d("HolaHola", "ueso, que pasoa3" + ListaDeUsuarios.get(i).Nombre);
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

        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginArray();
            while (JSONleido.hasNext()) {
                Log.d("uoso", "1");
                JSONleido.beginObject();
                Log.d("uoso", "2");
                Usuarios user;
                user = new Usuarios();
                while (JSONleido.hasNext()) {
                    String NomeDuElemento = JSONleido.nextName();
                    Log.d("uoso", "3");
                    if (NomeDuElemento.equals("IdUsuario")) {
                        Log.d("uoso", "4");
                        user.IdUsuario = JSONleido.nextInt();
                    } else if (NomeDuElemento.equals("Nombre")) {
                        Log.d("uoso", "5");
                        user.Nombre = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("Mail")) {
                        Log.d("uoso", "6");
                        user.Mail = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("NombreUsuario")) {
                        Log.d("uoso", "7");
                        user.NombreUsuario = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("Contraseña")) {
                        Log.d("uoso", "8");
                        user.Contra = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("NroTelefono")) {
                        Log.d("uoso", "9");
                        user.NroTel = JSONleido.nextInt();
                    } else if (NomeDuElemento.equals("Edad")) {
                        Log.d("uoso", "10");
                        user.Edad = JSONleido.nextInt();
                    }
                }

                Log.d("uoso", "" + user);
                ListaDeUsuarios.add(user);
                JSONleido.endObject();
            }
            JSONleido.endArray();
        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }
    }
}
