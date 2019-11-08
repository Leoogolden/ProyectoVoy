package com.example.proyectovoy;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class VerPerfil extends Fragment {
    View vistadevuelve;
    Bundle usuario = new Bundle();

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_miperfil, grupo, false);
        usuario = getArguments();
        Log.d("chillaono", usuario.toString());
        Usuarios user = new Usuarios(usuario.getInt("IdUsuario"), usuario.getString("Nombre"), usuario.getString("Mail"), usuario.getString("NombreUsuario"), usuario.getString("Contra"), usuario.getInt("NroTel"), usuario.getInt("Edad"));
        Log.d("chillaono", user.NombreUsuario +"  "+ user.Nombre);
        TextView Nombre = vistadevuelve.findViewById(R.id.NombreMiPerfil);
        Nombre.setText(user.Nombre);
        TextView NombreUsuario = vistadevuelve.findViewById(R.id.NombreUsuarioMiPerfil);
        NombreUsuario.setText(user.NombreUsuario);
        TextView Mail = vistadevuelve.findViewById(R.id.MailMiPerfil);
        Mail.setText(user.Mail);
        TextView Edad = vistadevuelve.findViewById(R.id.EdadMiPerfil);
        Edad.setText(String.valueOf(user.Edad));
        TextView NroTel = vistadevuelve.findViewById(R.id.NroTelMiPerfil);
        NroTel.setText(String.valueOf(user.NroTel));
        return vistadevuelve;
    }
}
