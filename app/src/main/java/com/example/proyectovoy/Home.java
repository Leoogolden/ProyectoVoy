package com.example.proyectovoy;

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

import java.text.DateFormat;

public class Home extends AppCompatActivity {
    Bundle usuario = new Bundle();

    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usuario = this.getIntent().getExtras();
        Usuarios user = new Usuarios(usuario.getInt("IdUsuario"), usuario.getString("Nombre"), usuario.getString("Mail"), usuario.getString("NombreUsuario"), usuario.getString("Contra"), usuario.getInt("NroTel"), usuario.getInt("Edad"));
        Log.d("Logeadisimo","ahi va "+ user.NombreUsuario);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        VerActivs activs = new VerActivs();
        activs.setArguments(usuario);
        ManejadorDeFragments = getFragmentManager();
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, activs);
        Transacciones.commit();



        BottomNavigationView bNavView = (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menunav = bNavView.getMenu();
        MenuItem itemseleccionado = menunav.getItem(0);
        itemseleccionado.setChecked(true);
        bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_home:
                        Intent ActividadDestino;
                        ActividadDestino = new Intent(Home.this, MisGrupos.class);
                        ActividadDestino.putExtras(usuario);
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(Home.this, Notificaciones.class);
                        ActividadDestino2.putExtras(usuario);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        Intent ActividadDestino3;
                        ActividadDestino3 = new Intent(Home.this, MisGrupos.class);
                        ActividadDestino3.putExtras(usuario);
                        startActivity(ActividadDestino3);
                        break;
                    case R.id.action_perfil:
                        Intent ActividadDestino4;
                        ActividadDestino4 = new Intent(Home.this, MiPerfil.class);
                        ActividadDestino4.putExtras(usuario);
                        startActivity(ActividadDestino4);
                        break;
                }

                return false;
            }
        });
    }



}
