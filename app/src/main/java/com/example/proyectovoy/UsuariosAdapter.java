package com.example.proyectovoy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends ArrayAdapter<Usuarios> {
    private static final String TAG = "UsuariosAdapter";
    private Context mContext;
    private int mResource;


    public UsuariosAdapter(Context context, int resource, ArrayList<Usuarios> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
//
//        int IdUsuario = getItem(position).getIdUsuario();
//        String Nombre = getItem(position).getNombre();
//        String Mail = getItem(position).getMail();
//        String NombreUsuario = getItem(position).getNombreUsuario();
//        String Contra= getItem(position).getContra();
//        int NroTel= getItem(position).getNroTel();
//        int Edad= getItem(position).getEdad();
//
//
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        convertView = inflater.inflate(mResource, parent, false);
//
//
//
////        TextView NombreInvite = convertView.findViewById(R.id.QuienInvita);
////        TextView Grupo = convertView.findViewById(R.id.QueGrupo);
////        NombreInvite.setText(QuienInvita);
////        Grupo.setText(NomGrupo);
//
//    }
}
