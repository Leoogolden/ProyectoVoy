package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class SelectedEventHome extends Fragment {
    View vistadevuelve;

    Bundle usuariologeado;
    Usuarios user = new Usuarios();
    Actividades activ = new Actividades();
    Boolean esadmin = false;
    Grupos grupaso = new Grupos();
    Bundle DatosRecibidos;
    Bundle GrupoElegido;
    Bundle Activ;

    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;

    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    Boolean esmiembro = false;
    Boolean yaparticipa = false;
    Button ConfirmarAsistencia;

    int idactividad;
    ViewGroup a;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_event, grupo, false);
        a = grupo;

        ConfirmarAsistencia = vistadevuelve.findViewById(R.id.BotonConfirmar);

        DatosRecibidos = getArguments();
        GrupoElegido = DatosRecibidos.getBundle("grupardo");
        Log.d("kovacho", GrupoElegido.toString());
        String Nombre = GrupoElegido.getString("Nombre");
        String Descripcion = GrupoElegido.getString("Descripcion");
        int IdGrupo = GrupoElegido.getInt("idGrupo");
        Log.d("kova", DatosRecibidos.toString());
        Log.d("onclick", "entra4" + GrupoElegido);
        grupaso.Nombre = Nombre;
        grupaso.Descripcion = Descripcion;
        grupaso.IdGrupo = IdGrupo;

        usuariologeado = DatosRecibidos.getBundle("usuariologeado");
        Log.d("qonda", DatosRecibidos.toString());
        user.setContra(usuariologeado.getString("Contra"));
        user.setEdad(usuariologeado.getInt("Edad"));
        user.setIdUsuario(usuariologeado.getInt("IdUsuario"));
        user.setMail(usuariologeado.getString("Mail"));
        user.setNombre(usuariologeado.getString("Nombre"));
        user.setNroTel(usuariologeado.getInt("NroTel"));
        user.setNombreUsuario(usuariologeado.getString("NombreUsuario"));
        user.setIdUsuario(usuariologeado.getInt("IdUsuario"));


        Activ = DatosRecibidos.getBundle("Activ");
        activ.setIdActiv(Activ.getInt("IdAct"));
        activ.setNombreActiv(Activ.getString("Nombre"));
        activ.setDescActiv(Activ.getString("Desc"));
        activ.setNombreCalle(Activ.getString("NombreCalle"));
        activ.setNumeroCalle(Activ.getInt("NroCalle"));
        activ.setEdMax(Activ.getInt("EdadMax"));
        activ.setEdMin(Activ.getInt("EdadMin"));
        activ.setLimPer(Activ.getInt("LimPer"));
        activ.setFechaActiv((Date) Activ.getSerializable("FechaAct"));
        Log.d("kova", Activ.toString());
        Log.d("kova", "" + Activ.getInt("IdAct"));

        TextView titulo = vistadevuelve.findViewById(R.id.tituloactiv);
        titulo.setText(activ.NombreActiv);
        TextView Desc = vistadevuelve.findViewById(R.id.descripcionactiv);
        Desc.setText(activ.DescActiv);
        TextView Direc = vistadevuelve.findViewById(R.id.DireccionActividad);
        Direc.setText(activ.NombreCalle + " " + activ.NumeroCalle);
        TextView Fecha = vistadevuelve.findViewById(R.id.FechaActivPr);
        Fecha.setText(activ.FechaActiv.toString());
        Log.d("kova", "" + user.IdUsuario + " : " + grupaso.IdGrupo + " : " + activ.IdActiv);

        VerificarMiembro a = new VerificarMiembro();
        a.execute();
        TraerMiembrosActiv lol = new TraerMiembrosActiv();
        lol.execute();
        tareaEstaEnGrupo estaono = new tareaEstaEnGrupo();
        estaono.execute();

//        for (Usuarios aux : ListaDeUsuarios
//        ) {Log.d("kovacho", "en el foreach papa" );
//            if (user.IdUsuario == aux.IdUsuario){
//                Log.d("kovacho", "ya esta en la activ" );
//                yaparticipa = true;
//                ConfirmarAsistencia.setText("NO IR");
//            }
//
//        }

        ConfirmarAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yaparticipa) {

                    tareaBajaActiv a = new tareaBajaActiv();
                    a.execute();

                } else {

                    if (esmiembro) {

                        Log.d("vachito", "es miembro");
                        tareaAsistencia j = new tareaAsistencia();
                        j.execute();
                    } else {
                        SolicitudAlGrupo a = new SolicitudAlGrupo();
                        a.execute();
                        Log.d("vachito", "no es miembro");
                    }
                }
            }
        });


        return vistadevuelve;
    }

    private class VerificarMiembro extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa " + getString(R.string.IP));
                URL rutatlantica2 = new URL(getString(R.string.IP) + "Grupos/Estaono/" + user.IdUsuario + "/" + grupaso.IdGrupo);
                Log.d("AccesoAPI6", "aa " + rutatlantica2.toString());
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
            Log.d("qonda", "esta en el grupo " + esmiembro.toString());
            if (esmiembro) {

            } else {
                ConfirmarAsistencia.setText("Unirse al grupo");
            }
        }
    }

    public void ProcesaMiembro(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        esmiembro = parseador.parse(streamLeido).getAsBoolean();

    }

    private class TraerMiembrosActiv extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica2 = new URL(getString(R.string.IP) + "ActivsGrupo/TraerMiembrosActv/" + activ.IdActiv);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica2.openConnection();
                Log.d("AccesoAPI3", "Me conecto" + rutatlantica2.toString());
                Log.d("qonda", "miembros activ " + rutatlantica2.toString());
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI3", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, StandardCharsets.UTF_8);
                    Log.d("AccesoAPI3", "conexioion ok seguimos");

                    ProcesaMiembrosActiv(lectorrespuesta);
                    Log.d("AccesoAPI3", "conexion ok daaale");
                } else {
                    Log.d("AccesoAPI3", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI3", "Hubo un error al conectarme: " + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola3", "ueso, que pasoa");


            UsuariosDelGrupoListAdapter miAdaptador;
            miAdaptador = new UsuariosDelGrupoListAdapter(getActivity(), R.layout.lista_usuariosgrupo, ListaDeUsuarios);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.listadeconf);
            ListaMiembros.setAdapter(miAdaptador);
            int lenght = ListaDeUsuarios.size();
            Log.d("wow", "son " + lenght);
            Log.d("wow", "avergaston " + ListaDeUsuarios.toString());
            for (int i = 0; i < lenght; i++) {
                int idus = ListaDeUsuarios.get(i).IdUsuario;
                if (user.IdUsuario == idus) {
                    ConfirmarAsistencia.setText("De Baja");
                    Log.d("wow", "ya participa!");
                    yaparticipa = true;

                    ConfirmarAsistencia.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.boton3));
                    ConfirmarAsistencia.setTextColor(R.drawable.txtboton3);
                }

            }
        }
    }

    private class tareaAsistencia extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa234");
                URL rutatlantica = new URL(getString(R.string.IP) + "ActivsGrupo/ConfAsis/" + grupaso.IdGrupo + "/" + activ.IdActiv + "/" + user.IdUsuario);
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
                    ProcessJSONConf(lectorrespuesta);
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
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Ahora participas del evento!", Toast.LENGTH_SHORT).show();
            SelectedEventHome Evento;
            Evento = new SelectedEventHome();
            Evento.setArguments(DatosRecibidos);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsHome, Evento);
            Transacciones.commit();
// if(ManejadorFragments. == R.id.AlojadorDeFragmentsGrupos){
//                Transacciones = ManejadorFragments.beginTransaction();
//                Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, Evento);
//                Transacciones.commit();
//            }
//            else{
//                Transacciones = ManejadorFragments.beginTransaction();
//                Transacciones.replace(R.id.AlojadorDeFragmentsHome, Evento);
//                Transacciones.commit();
//
//            }
        }
    }

    public void ProcessJSONConf(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();
    }

    private class SolicitudAlGrupo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(getString(R.string.IP) + "Invitacion/SolicitaUnirse/" + grupaso.IdGrupo + "/" + user.IdUsuario + "/" + activ.IdActiv);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI3", "Me conecto" + rutatlantica.toString());
                Log.d("qonda", "miembros activ " + rutatlantica.toString());
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI3", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, StandardCharsets.UTF_8);
                    Log.d("AccesoAPI3", "conexioion ok seguimos");

                    ProcesaSolicitudGrupo(lectorrespuesta);
                    Log.d("AccesoAPI3", "conexion ok daaale");
                } else {
                    Log.d("AccesoAPI3", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI3", "Hubo un error al conectarme: " + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola3", "ueso, que pasoa");
            SelectedEventHome Evento;
            Evento = new SelectedEventHome();
            Evento.setArguments(DatosRecibidos);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsHome, Evento);
            Transacciones.commit();
            Toast.makeText(getActivity(), "Solicitud Enviada", Toast.LENGTH_SHORT).show();
        }
    }

    public void ProcesaMiembrosActiv(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();
        JsonArray objetojson;
        objetojson = parseador.parse(streamLeido).getAsJsonArray();

        for (int i = 0; i < objetojson.size(); i++) {
            Usuarios aux;
            aux = new Usuarios();
            JsonObject objPersona;
            objPersona = objetojson.get(i).getAsJsonObject();

            aux.IdUsuario = objPersona.get("IdUsuario").getAsInt();

            aux.Nombre = objPersona.get("Nombre").getAsString();
            aux.Mail = objPersona.get("Mail").getAsString();
            aux.NombreUsuario = objPersona.get("NombreUsuario").getAsString();
            aux.Contra = objPersona.get("Contraseña").getAsString();
            aux.NroTel = objPersona.get("NroTelefono").getAsInt();
            aux.Edad = objPersona.get("Edad").getAsInt();
            Log.d("HolaHola3", "que ondaa " + aux.NombreUsuario);
            ListaDeUsuarios.add(aux);
        }
    }

    public void ProcesaSolicitudGrupo(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();

    }

    private class tareaEstaEnGrupo extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d("AccesoAPI6", "aaaa234");
                URL rutatlantica = new URL(getString(R.string.IP) + "Grupos/Pendiente/" + user.IdUsuario + "/" + grupaso.IdGrupo);
                Log.d("AccesoAPI6", "vaaa " + rutatlantica.toString());
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONpendiente(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI8", "Error en la conexion " + conexion.getResponseCode());
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI9", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }
    }

    private class tareaBajaActiv extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa234");
                URL rutatlantica = new URL(getString(R.string.IP) + "ActivsGrupo/BajaDeActiv/" + user.IdUsuario + "/" + activ.IdActiv);
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
                    ProcessJSONBaja(lectorrespuesta);
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
            super.onPostExecute(aVoid);

            Toast.makeText(getActivity(), "Baja Exitosa", Toast.LENGTH_SHORT).show();
            SelectedEventHome Evento;
            Evento = new SelectedEventHome();
            Evento.setArguments(DatosRecibidos);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsHome, Evento);
            Transacciones.commit();
        }
    }

    public void ProcessJSONBaja(InputStreamReader streamLeido) {

        JsonParser parseador;
        parseador = new JsonParser();

    }

    public void ProcessJSONpendiente(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();
        Boolean estaono;
        estaono = Boolean.parseBoolean(parseador.parse(streamLeido).getAsString());

        if (estaono) {
            ConfirmarAsistencia.setText("EN ESPERA");
        }


    }


}
