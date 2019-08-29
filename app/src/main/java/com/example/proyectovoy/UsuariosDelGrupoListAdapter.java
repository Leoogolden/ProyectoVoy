package com.example.proyectovoy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsuariosDelGrupoListAdapter extends ArrayAdapter<Usuarios> {

    private static final String TAG = "UsuariosDelGrupoListAdapter";
    private Context mContext;
    private int mResource;


    public UsuariosDelGrupoListAdapter(Context context, int resource, List<Usuarios> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final String NombreUsuario = getItem(position).getNombreUsuario();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView NombreInvite = convertView.findViewById(R.id.NombreUsuarioGrupo);

        NombreInvite.setText(NombreUsuario);
        Button boton = convertView.findViewById(R.id.BotonSelectedGroup);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, NombreUsuario);
                }
            }
        });

        return convertView;
    }


}
