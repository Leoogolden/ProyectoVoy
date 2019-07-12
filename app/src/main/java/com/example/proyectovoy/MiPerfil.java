package com.example.proyectovoy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MiPerfil extends AppCompatActivity {
    Bundle usuario = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        usuario = getIntent().getExtras();
        Usuarios user = new Usuarios(usuario.getInt("IdUsuario"), usuario.getString("Nombre"), usuario.getString("Mail"), usuario.getString("NombreUsuario"), usuario.getString("Contra"), usuario.getInt("NroTel"), usuario.getInt("Edad"));


        BottomNavigationView bNavView = (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menunav = bNavView.getMenu();
        MenuItem itemseleccionado = menunav.getItem(3);
        itemseleccionado.setChecked(true);
        bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_home:
                        Intent ActividadDestino;
                        ActividadDestino = new Intent(MiPerfil.this, Home.class);
                        ActividadDestino.putExtras(usuario);
                        startActivity(ActividadDestino);
                        break;
                    case R.id.action_notificaciones:
                        Intent ActividadDestino2;
                        ActividadDestino2 = new Intent(MiPerfil.this, Notificaciones.class);
                        ActividadDestino2.putExtras(usuario);
                        startActivity(ActividadDestino2);
                        break;
                    case R.id.action_grupos:
                        Intent ActividadDestino3;
                        ActividadDestino3 = new Intent(MiPerfil.this, MisGrupos.class);
                        ActividadDestino3.putExtras(usuario);
                        startActivity(ActividadDestino3);
                        break;
                    case R.id.action_perfil:
                        break;
                }
                return false;
            }
        });
    }
}
