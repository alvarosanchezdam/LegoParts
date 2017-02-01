package com.example.dam.legoparts;

/**
 * Created by DAM on 26/1/17.
 */

public class Part {
    private int cantidad;
    private String nombre;
    private String imgUrl;

    public Part() {
    }

    public Part(int cantidad, String nombre, String imgUrl) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.imgUrl = imgUrl;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
