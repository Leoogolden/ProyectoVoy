package com.example.proyectovoy;

public class Usuarios {

    int IdUsuario;
    String Nombre;
    String Mail;
    String NombreUsuario;
    String Contra;
    int NroTel;
    int Edad;


    public Usuarios(int idUsuario, String nombre, String mail, String nombreUsuario, String contra, int nroTel, int edad) {
        IdUsuario = idUsuario;
        Nombre = nombre;
        Mail = mail;
        NombreUsuario = nombreUsuario;
        Contra = contra;
        NroTel = nroTel;
        Edad = edad;
    }

    public Usuarios(){
        IdUsuario = 0;
        Nombre = "";
        Mail = "";
        NombreUsuario = "";
        Contra = "";
        NroTel = 0;
        Edad = 0;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return Contra;
    }

    public void setContra(String contra) {
        Contra = contra;
    }

    public int getNroTel() {
        return NroTel;
    }

    public void setNroTel(int nroTel) {
        NroTel = nroTel;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }
}
