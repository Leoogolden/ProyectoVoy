package com.example.proyectovoy;

public class Usuarios {

    public int IdUsuario;
    public String Nombre;
    public String Mail;
    public String NombreUsuario;
    public String Contra;
    public int NroTel;
    public int Edad;

     Usuarios(int a, String b, String c, String d, String e, int f, int g){

        IdUsuario = a;
        Nombre = b;
        Mail = c;
        NombreUsuario = d;
        Contra = e;
        NroTel = f;
        Edad = g;
    }
    Usuarios(){
        IdUsuario = 0;
        Nombre = "";
        Mail = "";
        NombreUsuario = "";
        Contra = "";
        NroTel = 0;
        Edad = 0;

    }



}
