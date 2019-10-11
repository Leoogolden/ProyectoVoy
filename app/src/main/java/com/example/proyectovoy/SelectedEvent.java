package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

public class SelectedEvent extends Fragment {
    View vistadevuelve;

    Bundle usuariologeado;
    Usuarios user = new Usuarios();
    Actividades activ = new Actividades();
    Boolean esadmin = false;
    Grupos grupaso = new Grupos();
    Bundle DatosRecibidos;
    Bundle GrupoElegido;
Bundle Activ;
    int idGrupo;
    int idactividad;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_event, grupo, false);

        DatosRecibidos = getArguments();
        GrupoElegido = DatosRecibidos.getBundle("grupaso");
        String Nombre = GrupoElegido.getString("Nombre");
        String Descripcion = GrupoElegido.getString("Descripcion");
        Log.d("onclick", "entra4");
        idGrupo = GrupoElegido.getInt("idGrupo");
        grupaso.IdGrupo = idGrupo;
        grupaso.Nombre = Nombre;
        grupaso.Descripcion = Descripcion;

        usuariologeado = DatosRecibidos.getBundle("usuariologeado");
        Log.d("qonda", DatosRecibidos.toString());
        user.setContra(usuariologeado.getString("Contra"));
        user.setEdad(usuariologeado.getInt("Edad"));
        user.setIdUsuario(usuariologeado.getInt("IdUsuario"));
        user.setMail(usuariologeado.getString("Mail"));
        user.setNombre(usuariologeado.getString("Nombre"));
        user.setNroTel(usuariologeado.getInt("NroTel"));
        user.setNombreUsuario(usuariologeado.getString("NombreUsuario"));

        Activ = DatosRecibidos.getBundle("Actividad");
        activ.setIdActiv(Activ.getInt("IdAct"));
        activ.setNombreActiv(Activ.getString("Nombre"));
        activ.setDescActiv(Activ.getString("Desc"));
        activ.setNombreCalle(Activ.getString("NombreCalle"));
        activ.setNumeroCalle(Activ.getInt("NroCalle"));
        activ.setEdMax(Activ.getInt("EdadMax"));
        activ.setEdMin(Activ.getInt("EdadMin"));
        activ.setLimPer(Activ.getInt("LimPer"));
        activ.setFechaActiv((Date) Activ.getSerializable("FechaAct"));


        Button ConfirmarAsistencia = vistadevuelve.findViewById(R.id.BotonConfirmar);
        Button UnirseAlGrupo = vistadevuelve.findViewById(R.id.UnirseAlGrupo);
        UnirseAlGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ConfirmarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return vistadevuelve;
    }}
