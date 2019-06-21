package com.example.proyectovoy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.proyectovoy.Grupos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MisGrupos extends AppCompatActivity {

    List<String> listagrupos;
    ArrayList<Grupos> ListaDeGrupos = new ArrayList<Grupos>();
    int[] listviewImage = new int[]{
            R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo
    };

    int idUsr = 2;
    ArrayList<String> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);
        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();
        BottomNavigationView bNavView = (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menunav = bNavView.getMenu();
        MenuItem itemseleccionado = menunav.getItem(2);
        itemseleccionado.setChecked(true);
        bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_home:
                        Intent ActividadDestino;
                        ActividadDestino = new Intent(MisGrupos.this, Home.class);
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(MisGrupos.this, Notificaciones.class);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        break;
                    case R.id.action_perfil:
                        Intent ActividadDestino4;
                        ActividadDestino4 = new Intent(MisGrupos.this, MiPerfil.class);
                        startActivity(ActividadDestino4);
                        break;
                }
                return false;
            }
        });

    }



    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://10.152.2.21:2073/api/Grupos/" + idUsr);
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
            for (int i = 0; i < lenght; i++) {
                Log.d("HolaHola", "ueso, que pasoa3" + ListaDeGrupos.get(i).Nombre);
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("listview_title", ListaDeGrupos.get(i).Nombre);
                hm.put("listview_image", Integer.toString(listviewImage[i]));
                Log.d("HolaHola", "ueso, que pasoa4");
                aList.add(hm);
            }
            Log.d("HolaHola", "ueso, que pasoa5");
            String[] from = {"listview_image", "listview_title"};
            int[] to = {R.id.listview_image, R.id.listview_item_title};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_listagrupos, from, to);
            ListView androidListView = (ListView) findViewById(R.id.ListaGrupos);
            Log.d("Holahola", "Llegue chicos");
            androidListView.setAdapter(simpleAdapter);
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
//                Log.d("uoso", ""+ asd);
                ListaDeGrupos.add(asd);
                JSONleido.endObject();
            }
            JSONleido.endArray();


        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }

    }






    public void ClickedBtn() {
        Intent asd1;
        asd1 = new Intent(this, InfoGrupo.class);
        startActivity(asd1);

    }

}


