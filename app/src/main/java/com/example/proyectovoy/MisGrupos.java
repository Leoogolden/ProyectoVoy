package com.example.proyectovoy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MisGrupos extends AppCompatActivity {
    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4"
    };
    int[] listviewImage = new int[]{
            R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);
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
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }
        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_listagrupos, from, to);
        ListView androidListView = (ListView) findViewById(R.id.ListaGrupos);
        Log.d("Holahola", "Llegue chicos");
        androidListView.setAdapter(simpleAdapter);
    }
}

