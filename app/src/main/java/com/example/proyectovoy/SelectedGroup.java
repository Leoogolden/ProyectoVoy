package com.example.proyectovoy;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;

public class SelectedGroup extends Fragment implements UsuariosDelGrupoListAdapter.customButtonListener, View.OnClickListener {

    String IP;
    TextView NombreGrupo;
    TextView DescripcionGrupo;
    ListView ListaIntegrantes;
    ImageView FotoGrupo;
    Grupos SelectedGroup;
    ArrayList<Usuarios> ListaDeUsuarios = new ArrayList<>();
    int idGrupo;
    View vistadevuelve;
    Bundle usuariologeado;
    Usuarios user = new Usuarios();
    Boolean esadmin;

    FragmentManager ManejadorFragments;
    FragmentTransaction Transacciones;
    Bundle DatosRecibidos;
    UsuariosDelGrupoListAdapter.customButtonListener customListner;

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
        ListaIntegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
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
                edtGrup =new EditarGrupo();
                edtGrup.setArguments(DatosRecibidos);
                ManejadorFragments = getFragmentManager();
                Transacciones = ManejadorFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, edtGrup);
                Transacciones.commit();

            }
        });
        ListView listaintegrantes = vistadevuelve.findViewById(R.id.ListaIntegrantes);
        listaintegrantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog(ListaDeUsuarios.get(position));
            }
        });

        VerificarAdmin vamos = new VerificarAdmin();
        vamos.execute();

        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();


        return vistadevuelve;
    }

    public void openDialog(final Usuarios usuarios) {

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
                        Toast.makeText(getActivity(), "Has eliminado a " + usuarios.NombreUsuario + " del grupo", Toast.LENGTH_SHORT).show();


                    }
                })
                .setNeutralButton("Volver a la lista", null)
                .show();
    }

    @Override
    public void onButtonClickListner(int position, String value) {

    }

    @Override
    public void onClick(View v) {
        Bundle cosaspaso = new Bundle();
        cosaspaso.putBundle("usuario", usuariologeado);
        cosaspaso.putInt("idgru", idGrupo);
        InvitarAlGrupo AgregarUsuarios;
        AgregarUsuarios = new InvitarAlGrupo();
        AgregarUsuarios.setArguments(cosaspaso);
        ManejadorFragments = getFragmentManager();
        Transacciones = ManejadorFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, AgregarUsuarios);
        Transacciones.commit();
    }


    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
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

                    ProcessJSONLeido(lectorrespuesta);
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


            List<Usuarios> DatosLista = new ArrayList<Usuarios>();
            int lenght = ListaDeUsuarios.size();

            UsuariosDelGrupoListAdapter miAdaptador;
            miAdaptador = new UsuariosDelGrupoListAdapter(getActivity(), R.layout.lista_usuariosgrupo, ListaDeUsuarios);
            ListView ListaMiembros = vistadevuelve.findViewById(R.id.ListaIntegrantes);
            ListaMiembros.setAdapter(miAdaptador);
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
            Log.d("qonda", "" + esadmin.toString());
        }
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

    public void setCustomButtonListner(UsuariosDelGrupoListAdapter.customButtonListener listener) {
        this.customListner = listener;
    }


    public void EsAdminProcesa(InputStreamReader streamLeido) {
        JsonParser parseador;
        parseador = new JsonParser();

        esadmin = parseador.parse(streamLeido).getAsBoolean();

    }
}
