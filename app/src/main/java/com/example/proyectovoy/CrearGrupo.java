package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CrearGrupo extends Fragment {


    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        View vistadevuelve;
        vistadevuelve = inflador.inflate(R.layout.fragment_crear_grupo, grupo, false);




        return vistadevuelve;
    }
}
