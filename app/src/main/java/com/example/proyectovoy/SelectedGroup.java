package com.example.proyectovoy;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectedGroup extends Fragment implements View.OnClickListener {

    String IP;
    TextView NombreGrupo;
    TextView DescripcionGrupo;
    ListView ListaIntegrantes;
    ImageView FotoGrupo;
    Grupos SelectedGroup;
    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    ArrayList<Actividades> ListaActivs = new ArrayList<>();
    int idGrupo;
    View vistadevuelve;
    Bundle usuariologeado;
    Usuarios user = new Usuarios();
    Boolean esadmin = false;
    Grupos grupaso = new Grupos();
    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;
    Bundle DatosRecibidos;
    Usuarios uselected = new Usuarios();

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_selected_group, grupo, false);
        Log.d("onclick", "entra3");
        IP = getString(R.string.IP);
        DatosRecibidos = getArguments();
        Bundle GrupoElegido = DatosRecibidos.getBundle("grupaso");
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

        NombreGrupo = vistadevuelve.findViewById(R.id.NombreGrupo);
        DescripcionGrupo = vistadevuelve.findViewById(R.id.DescripcionGrupo);

        FotoGrupo = vistadevuelve.findViewById(R.id.ImagenGrupo);
        Log.d("onclick", "entra5" + idGrupo);

        NombreGrupo.setText(Nombre);
        DescripcionGrupo.setText(Descripcion);
        Log.d("onclick", "entra5");
        SelectedGroup = new Grupos(idGrupo, Nombre, Descripcion);
        NombreGrupo.setText(SelectedGroup.Nombre);
        DescripcionGrupo.setText(SelectedGroup.Descripcion);
        Button a = vistadevuelve.findViewById(R.id.AgregarMiembros);
        a.setOnClickListener(this);

        Button editar = vistadevuelve.findViewById(R.id.EditarGrupo);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarGrupo edtGrup;
                edtGrup = new EditarGrupo();
                edtGrup.setArguments(DatosRecibidos);
                ManejadorFragments = getFragmentManager();
                Transacciones = ManejadorFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, edtGrup);
                Transacciones.commit();

            }
        });
        Button evento = vistadevuelve.findViewById(R.id.CrearEvento);
        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearActiv event;
                event = new CrearActiv();
                event.setArguments(usuariologeado);
                ManejadorFragments = getFragmentManager();
                Transacciones = ManejadorFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, event);
                Transacciones.commit();
            }
        });

        VerificarAdmin vamos = new VerificarAdmin();
        vamos.execute();
        TraerActivs actividadesgrupo = new TraerActivs();
        actividadesgrupo.execute();
        TraerMiembrosDelGrupo miTarea = new TraerMiembrosDelGrupo();
        miTarea.execute();
        Log.d("wow", ListaDeUsuarios.toString());
//        ListView listaactivs = vistadevuelve.findViewById(R.id.ListaActivs);
//        listaactivs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //Te lleva a ver la info de la actividad
//
//            }
//        });


        return vistadevuelve;
    }

    public void openDialog(final Usuarios usuarioseleccionado) {
        uselected = usuarioseleccionado;
        new LovelyStandardDialog(getActivity(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorAccent)
                .setTitle("Eliminar Miembro")
                .setMessage("¿Desea eliminar este usuario?")
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNegativeButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setNeutralButtonColor(ContextCompat.getColor(getActivity(), R.color.sec))
                .setPositiveButton("Eliminar del grupo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (esadmin) {
                            EliminarUsuario elimino = new EliminarUsuario();
                            elimino.execute();
                            Toast.makeText(getActivity(), "Has eliminado a " + usuarioseleccionado.NombreUsuario + " del grupo", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No tienes permisos de Admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hacer Administrador", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (esadmin) {
                            HacerAdmin a = new HacerAdmin();
                            a.execute();
                        } else {
                            Toast.makeText(getActivity(), "No tienes permisos de Admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton("Volver a la lista", null)
                .show();
    }


    @Override
    public void onClick(View v) {

        if (esadmin) {
            Bundle cosaspaso = new Bundle();
            cosaspaso.putBundle("usuario", usuariologeado);
            cosaspaso.putInt("idgru", idGrupo);
            cosaspaso.putBoolean("esadmin", esadmin);
            InvitarAlGrupo AgregarUsuarios;
            AgregarUsuarios = new InvitarAlGrupo();
            AgregarUsuarios.setArguments(cosaspaso);
            ManejadorFragments = getFragmentManager();
            Transacciones = ManejadorFragments.beginTransaction();
            Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, AgregarUsuarios);
            Transacciones.commit();
        } else {
            Toast.makeText(getActivity(), "No tienes permisos de Admin", Toast.LENGTH_SHORT).show();

        }
    }

    private class TraerMiembrosDelGrupo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(IP + "Grupos/MiembrosGrupo/" + idGrupo);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI3", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI3", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, StandardCharsets.UTF_8);
                    Log.d("AccesoAPI3", "conexioion ok seguimos");

                    TraerIntegrantesProcesa(lectorrespuesta);
                    Log.d("AccesoAPI3", "conexion ok daaale");
                } else {
                    Log.d("AccesoAPI3", "Error en la conexion");
                }
                conexion.disconnect();
            } catch (Exception error) {
                Log.d("AccesoAPI3", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HolaHola3", "ueso, que pasoa");


            UsuariosDelGrupoListAdapter miAdaptador;
            miAdaptador = new UsuariosDelGrupoListAdapter(getActivity(), R.layout.lista_usuariosgrupo, ListaDeUsuarios);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.ListaIntegrantes);
            ListaMiembros.setAdapter(miAdaptador);
            int lenght = ListaDeUsuarios.size();
            Log.d("wow", "son " + lenght);
        }
    }

    private class TraerActivs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(IP + "ActivsGrupo/" + idGrupo);
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
            ActividadesListAdapter miAdaptador;
            miAdaptador = new ActividadesListAdapter(getActivity(), R.layout.lista_activs_grupo, ListaActivs);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.ListaEventos);
            ListaMiembros.setAdapter(miAdaptador);
            int lenght = ListaDeUsuarios.size();
        }
    }


    private class VerificarAdmin extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa" + NombreGrupo + " " + DescripcionGrupo);
                URL rutatlantica2 = new URL(IP + "Grupos/EsAdmin/" + user.IdUsuario + "/" + idGrupo);
                Log.d("qonda", "aa " + rutatlantica2);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica2.openConnection();
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    EsAdminProcesa(lectorrespuesta);
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
            Log.d("qonda", "es admin " + esadmin.toString());

            ListView listaintegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
            listaintegrantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    openDialog(ListaDeUsuarios.get(position));


                }
            });
        }
    }


    private class EliminarUsuario extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa " + uselected.NombreUsuario);
                URL rutatlantica = new URL(IP + "Grupos/EliminarUsDelGrupo/" + uselected.IdUsuario + "/" + grupaso.IdGrupo);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
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

    private class HacerAdmin extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI6", "aaaa " + uselected.NombreUsuario);
                URL rutatlantica = new URL(IP + "Grupos/HacerAdminDelGrupo/" + uselected.IdUsuario + "/" + grupaso.IdGrupo);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/json");
                conexion.setRequestProperty("charset", "utf-8");
                Log.d("AccesoAPI6", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI6", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
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

    private void ProcesarJsonActivs(InputStreamReader lectorrespuesta) throws ParseException {
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


    public void TraerIntegrantesProcesa(InputStreamReader streamLeido) {

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

    public void EsAdminProcesa(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        esadmin = parseador.parse(streamLeido).getAsBoolean();

    }


}
