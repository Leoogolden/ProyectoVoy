package com.example.proyectovoy;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MisGrupos extends AppCompatActivity {
    Bundle usuariologeado = new Bundle();


    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);
        usuariologeado = getIntent().getExtras();
        Usuarios user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));
        Log.d("keloke",user.Nombre+ " "+ user.IdUsuario);


        VerGrupos Grupos;
        Grupos = new VerGrupos();
        Grupos.setArguments(usuariologeado);
        ManejadorDeFragments = getFragmentManager();
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, Grupos);
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
                        ActividadDestino.putExtras(usuariologeado);
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(MisGrupos.this, Notificaciones.class);
                        ActividadDestino2.putExtras(usuariologeado);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        break;
                    case R.id.action_perfil:
                        Intent ActividadDestino4;
                        ActividadDestino4 = new Intent(MisGrupos.this, MiPerfil.class);
                        ActividadDestino4.putExtras(usuariologeado);
                        startActivity(ActividadDestino4);
                        break;
                }
                return false;
            }
        });


    }




}




