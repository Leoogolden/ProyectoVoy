package com.example.proyectovoy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.URL;

public class InvitacionGrupoDialog extends AppCompatDialogFragment {
    private InvitacionesGrupos grupo;
    Boolean Aceptaono;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        LayoutInflater inflador = getActivity().getLayoutInflater();
        View view = inflador.inflate(R.layout.dialog_invitaciones, null);
        build.setView(view);
        Log.d("AccesoAPI6", "entra2");
        build.setTitle("Invitacion a Grupo")
                .setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("AccesoAPI6", "entra3");
                        Aceptaono = true;
                        tareaAsincronicaInvitacion acep = new tareaAsincronicaInvitacion();
                        acep.execute();
                    }
                });

        return build.create();
    }

    public void setGrupo(InvitacionesGrupos grupo) {
        this.grupo = grupo;
    }

    public InvitacionesGrupos getGrupo() {
        return grupo;
    }

    public interface InvitacionGrupoDialogListener {
        void applyTexts();
    }

    private class tareaAsincronicaInvitacion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL rutatlantica = new URL("http://10.152.2.63:2073/api/Invitacion/Aceptar" + grupo.idInv+"/"+ Aceptaono);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
//                    InputStream cuerporesspuesta = conexion.getInputStream();
//                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
//                    ProcessJSONLeido(lectorrespuesta);
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
            //Grupos
            super.onPostExecute(aVoid);
        }
    }

}
