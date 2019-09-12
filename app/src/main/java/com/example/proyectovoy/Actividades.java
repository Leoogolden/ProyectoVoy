package com.example.proyectovoy;

import java.util.Date;

public class Actividades {


    int IdActiv;
    String NombreActiv;
    String DescActiv;
    String NombreCalle;
    int NumeroCalle;
    int EdMin;
    int EdMax;
    int LimPer;
    Date FechaActiv;


    public Actividades(int idActiv, String nombreActiv, String descActiv, String nombreCalle, int numeroCalle, int edMin, int edMax, int limPer, Date fechaActiv) {
        IdActiv = idActiv;
        NombreActiv = nombreActiv;
        DescActiv = descActiv;
        NombreCalle = nombreCalle;
        NumeroCalle = numeroCalle;
        EdMin = edMin;
        EdMax = edMax;
        LimPer = limPer;
        FechaActiv = fechaActiv;
    }


    public Actividades() {
        IdActiv = 0;
        NombreActiv = "";
        DescActiv = "";
        NombreCalle = "";
        NumeroCalle = 0;
        EdMin = 0;
        EdMax = 0;
        LimPer = 0;
        FechaActiv = new Date();
    }


    public int getIdActiv() {
        return IdActiv;
    }

    public void setIdActiv(int idActiv) {
        this.IdActiv = idActiv;
    }

    public String getNombreActiv() {
        return NombreActiv;
    }

    public void setNombreActiv(String nombreActiv) {
        NombreActiv = nombreActiv;
    }

    public String getDescActiv() {
        return DescActiv;
    }

    public void setDescActiv(String descActiv) {
        DescActiv = descActiv;
    }

    public String getNombreCalle() {
        return NombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        NombreCalle = nombreCalle;
    }

    public int getNumeroCalle() {
        return NumeroCalle;
    }

    public void setNumeroCalle(int numeroCalle) {
        NumeroCalle = numeroCalle;
    }

    public int getEdMin() {
        return EdMin;
    }

    public void setEdMin(int edMin) {
        EdMin = edMin;
    }

    public int getEdMax() {
        return EdMax;
    }

    public void setEdMax(int edMax) {
        EdMax = edMax;
    }

    public int getLimPer() {
        return LimPer;
    }

    public void setLimPer(int limPer) {
        LimPer = limPer;
    }

    public Date getFechaActiv() {
        return FechaActiv;
    }

    public void setFechaActiv(Date fechaActiv) {
        FechaActiv = fechaActiv;
    }

}
