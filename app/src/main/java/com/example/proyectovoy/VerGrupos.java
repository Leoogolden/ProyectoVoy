package com.example.proyectovoy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerGrupos extends Fragment implements View.OnClickListener {
    String IP;
    //declaramos fotos provisorias pa probar
    int[] listviewImage = new int[]{
            R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo, R.drawable.ejemploperfilgrupo
    };
    Bundle usuariologeado;
    Usuarios user = new Usuarios();
    //listapaelsheison
    ArrayList<Grupos> ListaDeGrupos = new ArrayList<Grupos>();
    View vistadevuelve;

    FragmentManager ManejadorDeFragments;
    FragmentTransaction Transacciones;

    Button CrearGrupo;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        vistadevuelve = inflador.inflate(R.layout.fragment_lista_de_grupos, grupo, false);
        IP = getString(R.string.IP);

        usuariologeado = getArguments();
        user.setContra(usuariologeado.getString("Contra"));
        user.setEdad(usuariologeado.getInt("Edad"));
        user.setIdUsuario(usuariologeado.getInt("IdUsuario"));
        user.setMail(usuariologeado.getString("Mail"));
        user.setNombre(usuariologeado.getString("Nombre"));
        user.setNroTel(usuariologeado.getInt("NroTel"));
        user.setNombreUsuario(usuariologeado.getString("NombreUsuario"));


        Log.d("keloke",""+user.IdUsuario);
        CrearGrupo = vistadevuelve.findViewById(R.id.CrearGrupo);
        CrearGrupo.setOnClickListener(this);

        ActualizarGrupos miTarea = new ActualizarGrupos();
        miTarea.execute();

        ListView androidListView = (ListView) vistadevuelve.findViewById(R.id.ListaGrupos);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onclick", "entra");

                Bundle pasaje;
                pasaje = new Bundle();
                pasaje.putInt("idGrupo", ListaDeGrupos.get(position).IdGrupo);
                pasaje.putString("Nombre", ListaDeGrupos.get(position).Nombre);
                pasaje.putString("Descripcion", ListaDeGrupos.get(position).Descripcion);
                Log.d("onclick", "entra2" + ListaDeGrupos.get(position).Nombre);
                usuariologeado.putBundle("grupaso", pasaje);
                Fragment ingreso;
                ingreso = new SelectedGroup();
                Log.d("qonda", usuariologeado.toString());
                ingreso.setArguments(usuariologeado);
                ManejadorDeFragments = getFragmentManager();
                Transacciones = ManejadorDeFragments.beginTransaction();
                Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, ingreso);
                Transacciones.commit();
            }
        });


        return vistadevuelve;
    }

    //CREAR GRUPO
    @Override
    public void onClick(View v) {
        Log.d("entra", "entro");

        Fragment creargrupo = new CrearGrupo();
        creargrupo.setArguments(usuariologeado);
        ManejadorDeFragments = getFragmentManager();
        Log.d("entra", "entro2");
        Transacciones = ManejadorDeFragments.beginTransaction();
        Transacciones.replace(R.id.AlojadorDeFragmentsGrupos, creargrupo);
        Log.d("entra", "entro3");
        Transacciones.commit();
    }

    //la buena async task
    public class ActualizarGrupos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL(IP +"Grupos/" + user.IdUsuario);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI", "conexion ok" + user.IdUsuario);
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
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
            Log.d("HolaHola", "ueso, que pasoa");
            List<HashMap<String, String>> aList;
            aList = new ArrayList<HashMap<String, String>>();
            Log.d("HolaHola", "ueso, que pasoa2");
            int lenght = ListaDeGrupos.size();
            for (int i = 0; i < lenght; i++) {
                Log.d("HolaHola", "ueso, que pasoa3" + ListaDeGrupos.get(i).Nombre);
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("listview_title", ListaDeGrupos.get(i).Nombre);
                hm.put("listview_image", Integer.toString(listviewImage[1]));
                Log.d("HolaHola", "ueso, que pasoa4");
                aList.add(hm);
                Log.d("holahola", hm.toString());
            }
            Log.d("HolaHola", "ueso, que pasoa5");
            String[] from = {"listview_image", "listview_title"};
            int[] to = {R.id.listview_image, R.id.listview_item_title};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.lista_grupos, from, to);
            ListView androidListView = (ListView) vistadevuelve.findViewById(R.id.ListaGrupos);
            Log.d("Holahola", "Llegue chicos");
            androidListView.setAdapter(simpleAdapter);
        }
    }

    //guardamos las cosas en un array list tipo string (ListaDeGrupos)
    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginArray();
            while (JSONleido.hasNext()) {
                Log.d("uoso", "1");
                JSONleido.beginObject();
                Log.d("uoso", "2");
                int idGrupo = 0;
                String nombre = "", descripcion = "";
                while (JSONleido.hasNext()) {
                    String NomeDuElemento = JSONleido.nextName();
                    Log.d("uoso", "3");
                    if (NomeDuElemento.equals("IdGrupo")) {
                        Log.d("uoso", "4");
                        idGrupo = JSONleido.nextInt();
                    } else if (NomeDuElemento.equals("Nombre")) {
                        Log.d("uoso", "5");
                        nombre = JSONleido.nextString();
                    } else if (NomeDuElemento.equals("Descripcion")) {
                        Log.d("uoso", "6");
                        descripcion = JSONleido.nextString();
                    }
                }
                Grupos asd = new Grupos(idGrupo, nombre, descripcion);
                Log.d("uoso", "" + asd);
                ListaDeGrupos.add(asd);
                JSONleido.endObject();
            }
            JSONleido.endArray();
        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }
    }


}
