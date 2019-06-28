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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerGrupos extends Fragment {

    //declaramos fotos provisorias pa probar
    int[] listviewImage = new int[]{
            R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo
    };

    int idUsr = 2;
    //listapaelsheison
    ArrayList<Grupos> ListaDeGrupos = new ArrayList<Grupos>();
    View vistadevuelve;

    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    Button CrearGrupo;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.lista_de_grupos, grupo, false);

        CrearGrupo = vistadevuelve.findViewById(R.id.CrearGrupo);
        CrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment creargrupo;
                creargrupo = new CrearGrupo();
                ManejadorDeFragments = getFragmentManager();
                Transacciones = ManejadorDeFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragments, creargrupo);
                Transacciones.commit();


            }
        });

        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();

        ListView androidListView = (ListView) vistadevuelve.findViewById(R.id.ListaGrupos);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle pasaje;
                pasaje = new Bundle();
                pasaje.putInt("idGrupo", ListaDeGrupos.get(position).IdGrupo);
                pasaje.putString("Nombre", ListaDeGrupos.get(position).Nombre);
                pasaje.putString("Descripcion", ListaDeGrupos.get(position).Descripcion);
                Fragment ingreso;
                ingreso = new SelectedGroup();
                ManejadorDeFragments = getFragmentManager();
                Transacciones = ManejadorDeFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragments, ingreso);
                Transacciones.commit();
            }
        });


        return vistadevuelve;
    }

    //la buena async task
    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.80:2073/api/Grupos/" + idUsr);
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
            int lenght = ListaDeGrupos.size();
            for (int i = 0; i < lenght-1; i++) {
                Log.d("HolaHola", "ueso, que pasoa3" + ListaDeGrupos.get(i).Nombre);
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("listview_title", ListaDeGrupos.get(i).Nombre);
                hm.put("listview_image", Integer.toString(listviewImage[i]));
                Log.d("HolaHola", "ueso, que pasoa4");
                aList.add(hm);
                Log.d("holahola", hm.toString());
            }
            Log.d("HolaHola", "ueso, que pasoa5");
            String[] from = {"listview_image", "listview_title"};
            int[] to = {R.id.listview_image, R.id.listview_item_title};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.activity_listagrupos, from, to);
            ListView androidListView = (ListView) vistadevuelve.findViewById(R.id.ListaGrupos);
            Log.d("Holahola", "Llegue chicos");
            androidListView.setAdapter(simpleAdapter);
        }
    }

    //selected item lleva a info en otro fragment
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Bundle pasaje;
        pasaje = new Bundle();
        pasaje.putInt("idGrupo", ListaDeGrupos.get(position).IdGrupo);
        pasaje.putString("Nombre", ListaDeGrupos.get(position).Nombre);
        pasaje.putString("Descripcion", ListaDeGrupos.get(position).Descripcion);
        Fragment ingreso;
        ingreso = new SelectedGroup();
        ManejadorDeFragments = getFragmentManager();
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragments, ingreso);
        Transacciones.commit();

    }

    //guardamos las cosas en un array list tipo string (ListaDeGrupos)
    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginArray();
            while (JSONleido.hasNext()) {
                Log.d("uoso", "1");
                JSONleido.beginObject();
                Log.d("uoso", "2");
                int idGrupo = 0;
                String nombre = "", descripcion = "";
                while (JSONleido.hasNext()) {
                    String NomeDuElemento = JSONleido.nextName();
                    Log.d("uoso", "3");
                    if (NomeDuElemento.equals("IdGrupo")) {
                        Log.d("uoso", "4");
                        idGrupo = JSONleido.nextInt();
                    } else if (NomeDuElemento.equals("Nombre")) {
                        Log.d("uoso", "5");
                        nombre = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("Descripcion")) {
                        Log.d("uoso", "6");
                        descripcion = JSONleido.nextString();
                    }
                }
                Grupos asd = new Grupos(idGrupo, nombre, descripcion);
               Log.d("uoso", ""+ asd);
                ListaDeGrupos.add(asd);
                JSONleido.endObject();
            }
            JSONleido.endArray();
        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }
    }


}
