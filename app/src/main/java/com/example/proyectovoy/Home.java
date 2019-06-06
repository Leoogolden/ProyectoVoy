package com.example.proyectovoy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
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
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(Home.this, Notificaciones.class);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        Intent ActividadDestino3;
                        ActividadDestino3 = new Intent(Home.this, MisGrupos.class);
                        startActivity(ActividadDestino3);
                        break;
                    case R.id.action_perfil:
                        Intent ActividadDestino4;
                        ActividadDestino4 = new Intent(Home.this, MiPerfil.class);
                        startActivity(ActividadDestino4);
                        break;
                }

                return false;
            }
        });
    }
}
