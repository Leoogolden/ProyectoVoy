package com.example.proyectovoy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class InvitacionGrupoDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        LayoutInflater inflador =getActivity().getLayoutInflater();
        View view =inflador.inflate(R.layout.dialog_invitaciones, null);
        build.setView(view);
           build.setTitle("Invitacion a Grupo");
           build.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   
               }
           });

    }
}
