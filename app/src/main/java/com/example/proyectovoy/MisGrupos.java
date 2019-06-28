package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MisGrupos extends AppCompatActivity {


    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    //declaramos fotos provisorias pa probar

    int[] listviewImage = new int[]{
            R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo
    };
    //declaramos un id del usuario para probar los grupos
    int idUsr = 2;
    ArrayList<String> results = new ArrayList<>();
    //listapaelsheison


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);


        //navigation bar
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




}




