package com.example.proyectovoy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InvitacionesGruposListAdapter extends ArrayAdapter<InvitacionesGrupos> {

    private static final String TAG = "InvitacionesGruposListAdapter";
    private Context mContext;
    private int mResource;


    public InvitacionesGruposListAdapter(Context context, int resource, ArrayList<InvitacionesGrupos> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String QuienInvita = getItem(position).getQuienInvita();
        String NomGrupo = getItem(position).getGrupo();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView NombreInvite = convertView.findViewById(R.id.QuienInvita);
        TextView Grupo = convertView.findViewById(R.id.QueGrupo);
        NombreInvite.setText(QuienInvita);
        Grupo.setText(NomGrupo);

        return convertView;
    }
}
