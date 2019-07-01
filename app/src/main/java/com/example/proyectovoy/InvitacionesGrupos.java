package com.example.proyectovoy;

public class InvitacionesGrupos {

    private String QuienInvita;
    private String Grupo;


    public InvitacionesGrupos(String quienInvita, String grupo) {
        QuienInvita = quienInvita;
        Grupo = grupo;
    }

    public InvitacionesGrupos() {
        QuienInvita = "";
        Grupo = "";
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
