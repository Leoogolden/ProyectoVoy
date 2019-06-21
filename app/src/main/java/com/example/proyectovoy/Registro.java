package com.example.proyectovoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void ConfirmarRegistro(View vista) {



            Intent ActividadDestino;
            ActividadDestino = new Intent(Registro.this, IniciarSesion.class);
            startActivity(ActividadDestino);

    }
}
