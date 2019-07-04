package com.example.proyectovoy;

public class InvitacionesGrupos {

    String QuienInvita;
    String Grupo;
    int idInv;


    public InvitacionesGrupos(int idInvitacion, String quienInvita, String grupo) {
        QuienInvita = quienInvita;
        Grupo = grupo;
        idInv = idInvitacion;
    }

    public InvitacionesGrupos() {
        QuienInvita = "";
        Grupo = "";
        idInv = 0;
    }

    public int getIdInv() {
        return idInv;
    }

    public void setIdInv(int idInv) {
        this.idInv = idInv;
    }

    public String getQuienInvita() {
        return QuienInvita;
    }

    public void setQuienInvita(String quienInvita) {
        QuienInvita = quienInvita;
    }

    public String getGrupo() {
        return Grupo;
    }

    public void setGrupo(String grupo) {
        Grupo = grupo;
    }
}
