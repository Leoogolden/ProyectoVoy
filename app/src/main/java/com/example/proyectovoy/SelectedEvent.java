package com.example.proyectovoy;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    Boolean esmiembro = false;

    Button ConfirmarAsistencia;
    Button UnirseAlGrupo;
    int idactividad;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_event, grupo, false);

        VerificarMiembro a = new VerificarMiembro();
        a.execute();
        ConfirmarAsistencia = vistadevuelve.findViewById(R.id.BotonConfirmar);
        UnirseAlGrupo = vistadevuelve.findViewById(R.id.UnirseAlGrupo);
        UnirseAlGrupo.setVisibility(View.GONE);

        DatosRecibidos = getArguments();
        GrupoElegido = DatosRecibidos.getBundle("grupaso");
        String Nombre = GrupoElegido.getString("Nombre");
        String Descripcion = GrupoElegido.getString("Descripcion");
        Log.d("onclick", "entra4");
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
    }

    private class VerificarMiembro extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa");
                URL rutatlantica2 = new URL(R.string.IP + "Grupos/Estaono/" + user.IdUsuario + "/" + grupaso.IdGrupo);
                Log.d("qonda", "aa " + rutatlantica2);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica2.openConnection();
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcesaMiembro(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI6", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI6", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("qonda", "es admin " + esmiembro.toString());
            if (esmiembro) {
                UnirseAlGrupo.setVisibility(View.VISIBLE);
            }
        }
    }

    public void ProcesaMiembro(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        esmiembro = parseador.parse(streamLeido).getAsBoolean();

    }


}
