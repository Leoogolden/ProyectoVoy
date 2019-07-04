package com.example.proyectovoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
    }

    public void CheckLogIn(View view) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(IniciarSesion.this, Home.class);
        startActivity(ActividadDestino);
    }

    public void ForgPass(View view) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(IniciarSesion.this, RecupContra.class);
        startActivity(ActividadDestino);
    }

    public void ToReg(View view) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(IniciarSesion.this, Registro.class);
        startActivity(ActividadDestino);
    }
}
