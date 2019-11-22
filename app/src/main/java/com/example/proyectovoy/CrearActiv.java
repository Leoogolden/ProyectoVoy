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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearActiv extends Fragment {
    View vistadevuelve;
    Bundle usuariologeado = new Bundle();
    Bundle GrupoElegido = new Bundle();
    Usuarios user = new Usuarios();
    Grupos grupaso = new Grupos();
    EditText NombreActiv;
    EditText DescActiv;
    EditText NombreCalle;
    EditText NumeroCalle;
    EditText EdadMinim;
    EditText EdadMaxim;
    EditText LimPerson;
    DatePicker FechaDeActiv;

    int idgru;
    int idactiv;

    String NombreActividad;
    String DescActividad;
    String NombreDeCalle;
    int NumeroDeCalle;
    int EdadMinima;
    int EdadMaxima;
    int LimPersona;
    String FechaConvertida;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");


    Date FechaDeActividad;


    //declaracion de fragments
    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_crear_activ, grupo, false);

        NombreActiv = vistadevuelve.findViewById(R.id.NombreDelEvento);
        DescActiv = vistadevuelve.findViewById(R.id.DescActiv);
        NombreCalle = vistadevuelve.findViewById(R.id.NombreDeCalle);
        NumeroCalle = vistadevuelve.findViewById(R.id.NumCalle);
        EdadMinim = vistadevuelve.findViewById(R.id.EdadMin);
        EdadMaxim = vistadevuelve.findViewById(R.id.EdadMax);
        LimPerson = vistadevuelve.findViewById(R.id.LimPer);
        FechaDeActiv = vistadevuelve.findViewById(R.id.FechaActiv);

        Bundle bundleaso = getArguments();
        usuariologeado = bundleaso.getBundle("usuariologeado");
        user = new Usuarios(usuariologeado.getInt("IdUsuario"), usuariologeado.getString("Nombre"), usuariologeado.getString("Mail"), usuariologeado.getString("NombreUsuario"), usuariologeado.getString("Contra"), usuariologeado.getInt("NroTel"), usuariologeado.getInt("Edad"));


        Log.d("keloke", user.Nombre + " " + user.IdUsuario);
        GrupoElegido = bundleaso.getBundle("grupardo");
        String Nombre = GrupoElegido.getString("Nombre");
        String Descripcion = GrupoElegido.getString("Descripcion");
        int idGrupo = GrupoElegido.getInt("idGrupo");
        Log.d("keloke", "aaa " + idGrupo);
        grupaso.IdGrupo = idGrupo;
        grupaso.Nombre = Nombre;
        grupaso.Descripcion = Descripcion;

        Button siguiente = vistadevuelve.findViewById(R.id.ConfirmarDatosEvento);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NombreActividad = NombreActiv.getText().toString();
                DescActividad = DescActiv.getText().toString();
                NombreDeCalle = NombreCalle.getText().toString();


                if (NombreActividad.matches("") || DescActividad.matches("") || NombreDeCalle.matches("") || NumeroCalle.getText().toString().matches("") || EdadMinim.getText().toString().matches("") || EdadMaxim.getText().toString().matches("") || LimPerson.getText().toString().matches("") || FechaDeActiv.toString().matches("")) {
                    Toast.makeText(getActivity(), "Alguno de los campos esta vacio, reintentar", Toast.LENGTH_SHORT).show();

                } else {
                    NumeroDeCalle = Integer.parseInt(NumeroCalle.getText().toString());
                    EdadMinima = Integer.parseInt(EdadMinim.getText().toString());
                    EdadMaxima = Integer.parseInt(EdadMaxim.getText().toString());
                    LimPersona = Integer.parseInt(LimPerson.getText().toString());
                    int mes = FechaDeActiv.getMonth() + 1;
                    FechaConvertida = "" + FechaDeActiv.getDayOfMonth() + "-" + mes + "-" + FechaDeActiv.getYear();

                    tareaCrarActiv asd = new tareaCrarActiv();
                    asd.execute();
                }
//                try {
//                    FechaDeActividad = sdf.parse(fecha);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Log.d("sape", fecha);
//                try {
//                    Log.d("sape", "" + sdf.parse(fecha));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                SimpleDateFormat sdf=new SimpleDateFormat("dd/M/yyyy");
//                FechaConvertida=sdf.format(FechaDeActividad.getTime());
//                Log.d("sape", FechaConvertida);

                //Log.d("keloke", sdf.format(Date.parse(FechaDeActiv.getDayOfMonth() + "-" + FechaDeActiv.getMonth() + "-" + FechaDeActiv.getYear())));

            }
        });


        return vistadevuelve;
    }

    private class tareaCrarActiv extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa234");
                URL rutatlantica = new URL(getString(R.string.IP) + "ActivsGrupo/CrearActiv/" + NombreActividad + "/" + DescActividad + "/" + EdadMinima + "/" + EdadMaxima + "/" + LimPersona + "/" + NombreDeCalle + "/" + NumeroDeCalle + "/" + grupaso.IdGrupo + "/" + user.IdUsuario);
                Log.d("AccesoAPI6", "vaaa " + rutatlantica.toString());
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
                    Log.d("AccesoAPI8", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI9", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Grupos
            super.onPostExecute(aVoid);

            Bundle bundel = new Bundle();
            bundel.putBundle("usuariologeado", usuariologeado);
            bundel.putBundle("grupardo", GrupoElegido);
            SelectedGroup AgregarUsuarios;
            AgregarUsuarios = new SelectedGroup();
            AgregarUsuarios.setArguments(bundel);
            ManejadorDeFragments = getFragmentManager();
            Transacciones = ManejadorDeFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, AgregarUsuarios);
            Transacciones.commit();
        }
    }

    public void ProcessJSONLeido(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        idactiv = parseador.parse(streamLeido).getAsInt();
        Log.d("keloke idactiv", "" + idactiv);
    }

}
