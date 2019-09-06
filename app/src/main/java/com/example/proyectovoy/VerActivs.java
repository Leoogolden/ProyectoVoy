package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VerActivs extends Fragment {
    View vistadevuelve;
Bundle usuariologeado = new Bundle();
    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_mis_activs, grupo, false);



        usuariologeado = getArguments();
        Usuarios user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));
        Log.d("keloke",user.Nombre+ " "+ user.IdUsuario);

        return vistadevuelve;
    }

}
