package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class VerActivs extends Fragment {
    View vistadevuelve;
    Bundle usuariologeado = new Bundle();
    ArrayList<Actividades> ListaActivs = new ArrayList<>();
    Usuarios user = new Usuarios();
    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;

    Bundle DatosRecibidos;
    Grupos grupo = new Grupos();
    EditText filtro;
Actividades activseleccionada = new Actividades();
    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_mis_activs, grupo, false);
        DatosRecibidos = getArguments();

        TraerActivs a = new TraerActivs();
        a.execute();
        usuariologeado = getArguments();
        user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));
        Log.d("keloke", user.Nombre + " " + user.IdUsuario);
        Button crearactiv = vistadevuelve.findViewById(R.id.BtnCrearActividad);
        filtro = vistadevuelve.findViewById(R.id.ActivsFilter);
        crearactiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CrearActiv nuevaactiv = new CrearActiv();
                nuevaactiv.setArguments(usuariologeado);
                ManejadorFragments = getFragmentManager();
                Transacciones = ManejadorFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsHome, nuevaactiv);
                Transacciones.commit();
            }
        });
        return vistadevuelve;
    }

    public void openDialogActiv(final Actividades activsl, final int pos) {
        new LovelyStandardDialog(getActivity(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorAccent)
                .setTitle("Actividad " + activsl.NombreActiv)
                .setMessage("Descripcion: " + activsl.DescActiv + " en " + activsl.NombreCalle + " al " + activsl.NumeroCalle)
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNegativeButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNeutralButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))

                .setNeutralButton("Ver mas info", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activseleccionada = activsl;

                        TraerGrupo dou = new TraerGrupo();
                        dou.execute();



                        Log.d("kova", "vamos por todo cagon");
                        Log.d("kova", "a los hinchas de river les digo que crean, porque tienen con que creer");
                        Log.d("kova", DatosRecibidos.toString());

                    }
                })

                .setNegativeButton("Volver a la lista", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();

    }

    private class TraerGrupo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(getString(R.string.IP) + "Activs/TraerGrupo/" + activseleccionada.IdActiv);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();

                Log.d("AccesoAPI2", "Me conecto "+rutatlantica.toString());
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI2", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcesarJsonGrupo(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI2", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI2", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Bundle Actividad = new Bundle();
            Actividad.putString("Nombre", activseleccionada.NombreActiv);
            Actividad.putString("Desc", activseleccionada.DescActiv);
            Actividad.putString("NombreCalle", activseleccionada.NombreCalle);
            Actividad.putInt("NroCalle", activseleccionada.NumeroCalle);
            Actividad.putInt("EdadMax", activseleccionada.EdMax);
            Actividad.putInt("EdadMin", activseleccionada.EdMin);
            Actividad.putInt("LimPer", activseleccionada.LimPer);
            Actividad.putInt("IdAct", activseleccionada.IdActiv);
            Actividad.putSerializable("FechaAct", activseleccionada.FechaActiv);
            Bundle grupardo = new Bundle();
            grupardo.putInt("idGrupo", grupo.IdGrupo);
            grupardo.putString("Nombre", grupo.Nombre);
            grupardo.putString("Descripcion", grupo.Descripcion);
            Bundle pasaje = new Bundle();
            pasaje.putBundle("usuariologeado", usuariologeado);
            pasaje.putBundle("Activ", Actividad);
            pasaje.putBundle("grupardo",grupardo);

            SelectedEvent Evento;
            Evento = new SelectedEvent();
            Evento.setArguments(pasaje);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsHome, Evento);
            Transacciones.commit();

        }
    }

    private void ProcesarJsonGrupo(InputStreamReader lectorrespuesta) {
        Log.d("grupoactiv", "pedimos");
        JsonParser parseador;
        parseador = new JsonParser();
        JsonObject objetojson;
        objetojson = parseador.parse(lectorrespuesta).getAsJsonObject();
        int idGru = objetojson.get("IdGrupo").getAsInt();
        Log.d("grupoactiv", "pedimos2");
        String nombre = objetojson.get("Nombre").getAsString();
        String desc = objetojson.get("Descripcion").getAsString();
        Log.d("grupoactiv", "pedimos3");
        Grupos a = new Grupos(idGru, nombre, desc);
        Log.d("grupoactiv", "dou " + a.Nombre);
        grupo = a;
    }

    private class TraerActivs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(getString(R.string.IP) + "Activs");
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI2", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI2", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcesarJsonActivs(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI2", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI2", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final ActividadesListAdapter miAdaptador;
            miAdaptador = new ActividadesListAdapter(getActivity(), R.layout.lista_activs_grupo, ListaActivs);
            final ListView ListaEven = vistadevuelve.findViewById(R.id.ListaActivs);
            Log.d("onPostExecute", "onPostExecute");
            ListaEven.setAdapter(miAdaptador);
            int lenght = ListaActivs.size();
            filtro.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    miAdaptador.getFilter().filter(s);
                    Log.d("filtro", s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ListaEven.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openDialogActiv(ListaActivs.get(position), position);

                }
            });
        }
    }


    private void ProcesarJsonActivs(InputStreamReader lectorrespuesta) throws
            ParseException {
        JsonParser parseador;
        parseador = new JsonParser();
        JsonArray objetojson;
        objetojson = parseador.parse(lectorrespuesta).getAsJsonArray();
        for (int i = 0; i < objetojson.size(); i++) {
            Actividades activ;
            activ = new Actividades();
            JsonObject objPersona;
            objPersona = objetojson.get(i).getAsJsonObject();
            activ.setIdActiv(objPersona.get("IdActiv").getAsInt());
            activ.setNombreActiv(objPersona.get("Nombre").getAsString());
            activ.setDescActiv(objPersona.get("Descripcion").getAsString());
            activ.setEdMin(objPersona.get("EdadMin").getAsInt());
            activ.setEdMax(objPersona.get("EdadMax").getAsInt());
            activ.setLimPer(objPersona.get("LimPer").getAsInt());
            activ.setNombreCalle(objPersona.get("Calle").getAsString());
            activ.setNumeroCalle(objPersona.get("Direccion").getAsInt());
            // activ.setFechaActiv(new SimpleDateFormat("dd/MM/yyyy").parse(objPersona.get("Fecha").getAsString()));
            Log.d("HolaHola3", "que ondaa " + activ.NombreActiv);
            ListaActivs.add(activ);
        }

    }

}
