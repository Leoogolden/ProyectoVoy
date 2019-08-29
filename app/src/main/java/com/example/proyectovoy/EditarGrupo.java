package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditarGrupo extends Fragment implements View.OnClickListener {

    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;
    View vistadevuelve;
    String IP;
    int idUsr;
    int idgru;
    String NombreGrupo = "";
    String DescripcionGrupo = "";
    Button btn;
    EditText Nom;
    EditText Desc;
    Bundle DatosRecibidos;
    Bundle usuariologeado = new Bundle();
Usuarios user = new Usuarios();
Grupos grupaso;
    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_editar_grupo, grupo, false);
        IP = getString(R.string.IP);



        DatosRecibidos = getArguments();
        Log.d("qonda", DatosRecibidos.toString());
        Bundle GrupoElegido = DatosRecibidos.getBundle("grupaso");
        String Nombre = GrupoElegido.getString("Nombre");
        String Descripcion = GrupoElegido.getString("Descripcion");
        Log.d("onclick", "entra4 " + Nombre);
        int idGrupo = GrupoElegido.getInt("idGrupo");
        grupaso = new Grupos(idGrupo, Nombre, Descripcion);
        usuariologeado = DatosRecibidos.getBundle("usuariologeado");



        user.setNombre(usuariologeado.getString("Nombre"));
        user.setContra(usuariologeado.getString("Contra"));
        user.setEdad(usuariologeado.getInt("Edad"));
        user.setIdUsuario(usuariologeado.getInt("IdUsuario"));
        user.setMail(usuariologeado.getString("Mail"));
        user.setNroTel(usuariologeado.getInt("NroTel"));
        user.setNombreUsuario(usuariologeado.getString("NombreUsuario"));

        Nom = vistadevuelve.findViewById(R.id.txtNombreEG);
        Desc = vistadevuelve.findViewById(R.id.txtDescripcionEG);
        btn = vistadevuelve.findViewById(R.id.btnEditarGrupo);


        Nom.setText(grupaso.Nombre);
        Desc.setText(grupaso.Descripcion);


        btn.setOnClickListener(this);


        return vistadevuelve;
    }

    @Override
    public void onClick(View v) {
        NombreGrupo = Nom.getText().toString();
        DescripcionGrupo = Desc.getText().toString();
        Log.d("AccesoAPI6", NombreGrupo + " " + DescripcionGrupo + " " + idUsr);
        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();

    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa" + NombreGrupo + " " + DescripcionGrupo + " " + idUsr);
                URL rutatlantica = new URL(IP + "Grupos/EditGrupo/" + NombreGrupo + "/" + DescripcionGrupo + "/" + grupaso.IdGrupo + "/"+ user.IdUsuario);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
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


            Toast.makeText(getActivity(), "Has cambiado la informacion correctamente ", Toast.LENGTH_SHORT).show();

            VerGrupos a;
            a = new VerGrupos();
            a.setArguments(usuariologeado);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, a);
            Transacciones.commit();

        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        idgru = parseador.parse(streamLeido).getAsInt();

    }
}
