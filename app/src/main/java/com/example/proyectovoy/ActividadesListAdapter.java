package com.example.proyectovoy;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class ActividadesListAdapter extends ArrayAdapter<Actividades> {


    private static final String TAG = "ActividadesListAdapter";
    private Context mContext;
    private int mResource;

    public ActividadesListAdapter(Context context, int resource, List<Actividades> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final String NombreE = getItem(position).getNombreActiv();
        final Date Fecha = getItem(position).getFechaActiv();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView NombreAc = convertView.findViewById(R.id.NombreActivLista);

        NombreAc.setText(NombreE);
//        TextView Fechita = convertView.findViewById(R.id.FechaActivLista);
//        Fechita.setText(Fecha.toString());

        return convertView;
    }
}
