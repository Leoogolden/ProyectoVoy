package com.example.proyectovoy;

public class Grupos {
    public int IdGrupo;
    public String Nombre;
    public String Descripcion;

    Grupos(int a, String b, String c) {
        IdGrupo = a;
        Nombre = b;
        Descripcion = c;
    }
    Grupos() {
        IdGrupo = 0;
        Nombre = "";
        Descripcion = "";
    }

}
