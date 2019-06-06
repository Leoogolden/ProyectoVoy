package com.example.proyectovoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RecupContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recup_contra);
    }
    public void CheckNuevCon(View view) {
        EditText Cont1;
        Cont1 = (EditText) findViewById(R.id.contra1);
        EditText Cont2;
        Cont2 = (EditText) findViewById(R.id.contra2);
        if(Cont1.getText().toString().compareTo(Cont2.getText().toString()) == 0){
            Log.d("recupcont","EstaOk");
            Intent ActividadDestino;
            ActividadDestino = new Intent(RecupContra.this, IniciarSesion.class);
            startActivity(ActividadDestino);
        }
    }
}
