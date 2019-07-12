package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MisGrupos extends AppCompatActivity {
    Bundle usuario = new Bundle(getIntent().getExtras());
    Usuarios user = new Usuarios(usuario.getInt("IdUSuario"), usuario.getString("Nombre"), usuario.getString("Mail"), usuario.getString("NombreUsuario"), usuario.getString("Contra"), usuario.getInt("NroTel"), usuario.getInt("Edad"));


    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);
        Fragment verGrupos;
        verGrupos = new VerGrupos();
        ManejadorDeFragments = getFragmentManager();
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, verGrupos);
        Transacciones.commit();

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
                        ActividadDestino.putExtras(usuario);
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(MisGrupos.this, Notificaciones.class);
                        ActividadDestino2.putExtras(usuario);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        break;
                    case R.id.action_perfil:
                        Intent ActividadDestino4;
                        ActividadDestino4 = new Intent(MisGrupos.this, MiPerfil.class);
                        ActividadDestino4.putExtras(usuario);
                        startActivity(ActividadDestino4);
                        break;
                }
                return false;
            }
        });


    }




}




