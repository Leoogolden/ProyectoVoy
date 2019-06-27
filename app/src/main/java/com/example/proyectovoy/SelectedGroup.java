package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.proyectovoy.Grupos;

import java.util.List;

public class SelectedGroup extends Fragment{

    TextView NombreGrupo;
    TextView DescripcionGrupo;
    ListView ListaIntegrantes;
    ImageView FotoGrupo;
    Grupos SelectedGroup;

    public View OnCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        View vistadevuelve = inflador.inflate(R.layout.selected_group, grupo, false);
        NombreGrupo= vistadevuelve.findViewById(R.id.NombreGrupo);
        DescripcionGrupo = vistadevuelve.findViewById(R.id.DescripcionGrupo);
        ListaIntegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
        FotoGrupo = vistadevuelve.findViewById(R.id.ImagenGrupo);

        SelectedGroup = new Grupos(datos.getInt("id"), datos.getString("Nombre"), datos.getString("Descripcion"));
        NombreGrupo.setText(SelectedGroup.Nombre);
        DescripcionGrupo.setText(SelectedGroup.Descripcion);

        return vistadevuelve;
    }
}
