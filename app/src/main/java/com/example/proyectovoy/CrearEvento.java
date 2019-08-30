package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CrearEvento extends Fragment {
View vistadevuelve;
    public View onCreateView (LayoutInflater inflador, ViewGroup grupo, Bundle datos){

        vistadevuelve = inflador.inflate(R.layout.fragment_crear_evento, grupo, false);

return vistadevuelve;
    }
}
