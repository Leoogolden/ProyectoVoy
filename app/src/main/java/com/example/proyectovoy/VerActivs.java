package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class VerActivs extends Fragment {
    View vistadevuelve;
    Bundle usuariologeado = new Bundle();

    Usuarios user = new Usuarios();

    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_mis_activs, grupo, false);


        usuariologeado = getArguments();
        user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));
        Log.d("keloke", user.Nombre + " " + user.IdUsuario);
        Button crearactiv = vistadevuelve.findViewById(R.id.BtnCrearActividad);
        crearactiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CrearActiv nuevaactiv = new CrearActiv();
                nuevaactiv.setArguments(usuariologeado);
                ManejadorDeFragments = getFragmentManager();
                Transacciones = ManejadorDeFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsHome, nuevaactiv);
                Transacciones.commit();
            }
        });
        return vistadevuelve;
    }

}
