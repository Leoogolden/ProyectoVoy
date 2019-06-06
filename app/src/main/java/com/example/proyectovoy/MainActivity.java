package com.example.proyectovoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void InSec (View view){
        Intent ActividadDestino;
        ActividadDestino = new Intent(MainActivity.this, IniciarSesion.class);
        startActivity(ActividadDestino);
    }
}
