package com.example.proyectovoy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class InvitarAlGrupo extends Fragment implements View.OnClickListener {
    String IP;
    int idusr;
    View vistadevuelve;
    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    int idusrinvitado;
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;
    int idgrupo;
    Button VolveraGrupos;
    Bundle usuariologeado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vistadevuelve = inflater.inflate(R.layout.fragment_invitarusuarios, container, false);
        TraerAmigos a = new TraerAmigos();
        a.execute();
        IP = getString(R.string.IP);
        Bundle DatosRecibidos = getArguments();
        usuariologeado = DatosRecibidos.getBundle("usuario");
        idusr = usuariologeado.getInt("IdUsuario");
        idgrupo = DatosRecibidos.getInt("idgru");

        VolveraGrupos = vistadevuelve.findViewById(R.id.VolverAGrupos);
        VolveraGrupos.setOnClickListener(this);

        return vistadevuelve;
    }

    @Override
    public void onClick(View v) {
        Fragment dou = new VerGrupos();
        dou.setArguments(usuariologeado);
        ManejadorDeFragments = getFragmentManager();
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, dou);
        Transacciones.commit();
    }

    private class TraerAmigos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(IP + "Usuario");
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, StandardCharsets.UTF_8);
                    Log.d("AccesoAPI", "conexioion ok seguimos");
                    ProcessJSONLeido(lectorrespuesta);
                    Log.d("AccesoAPI", "conexion ok daaale");
                } else {
                    Log.d("AccesoAPI", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola3", "ueso, que pasoa");

            ArrayList<String> DatosLista = new ArrayList<String>();
            int lenght = ListaDeUsuarios.size();
            for (int i = 0; i < lenght; i++) {
                Log.d("HolaHola3", "ueso, que pasoa3" + ListaDeUsuarios.get(i).Nombre);
                DatosLista.add(ListaDeUsuarios.get(i).NombreUsuario);
            }
            ArrayAdapter<String> miAdaptador;
            miAdaptador = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, DatosLista);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.ListaUsuarios);
            ListaMiembros.setAdapter(miAdaptador);

            ListaMiembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openDialog(ListaDeUsuarios.get(position));

                    idusrinvitado = ListaDeUsuarios.get(position).IdUsuario;

                }
            });
        }
    }

    public void openDialog(final Usuarios grupo) {

        new LovelyStandardDialog(getActivity(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorAccent)
                .setTitle("Añadir al grupo")
                .setMessage("¿Desea agregar este usuario al grupo?")
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNegativeButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNeutralButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setPositiveButton("Agregar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("queonda", "invita" + idusr + " a " + idusrinvitado + " grupo " + idgrupo);
                        EnviarInvitacion as = new EnviarInvitacion();
                        as.execute();
                        Toast.makeText(getActivity(), "Has invitado a " + ListaDeUsuarios.get(idusrinvitado - 1).getNombreUsuario() + " al grupo.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Volver a la lista", null)
                .show();
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();
        JsonArray objetojson;
        objetojson = parseador.parse(streamLeido).getAsJsonArray();

        for (int i = 0; i < objetojson.size(); i++) {
            Usuarios user;
            user = new Usuarios();
            JsonObject objPersona;
            objPersona = objetojson.get(i).getAsJsonObject();

            user.IdUsuario = objPersona.get("IdUsuario").getAsInt();
            user.Nombre = objPersona.get("Nombre").getAsString();
            user.Mail = objPersona.get("Mail").getAsString();
            user.NombreUsuario = objPersona.get("NombreUsuario").getAsString();
            user.Contra = objPersona.get("Contraseña").getAsString();
            user.NroTel = objPersona.get("NroTelefono").getAsInt();
            user.Edad = objPersona.get("Edad").getAsInt();
            Log.d("HolaHola3", "que ondaa " + user.NombreUsuario);
            ListaDeUsuarios.add(user);
        }

    }

    private class EnviarInvitacion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL rutatlantica = new URL(IP + "Invitacion/Invitar/" + idgrupo + "/" + idusr + "/" + idusrinvitado);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI7", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI7", "conexion ok");

                } else {
                    Log.d("AccesoAPI7", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI7", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }
    }
}
