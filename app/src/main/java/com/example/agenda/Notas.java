package com.example.agenda;

public class Notas {
    private int notaId;
    private String Nombre;
    private String Descripcion;
    private String UriFoto;
    private String UriVideo;
    private String UriVoz;

    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUriFoto() {
        return UriFoto;
    }

    public void setUriFoto(String uriFoto) {
        UriFoto = uriFoto;
    }

    public String getUriVideo() {
        return UriVideo;
    }

    public void setUriVideo(String uriVideo) {
        UriVideo = uriVideo;
    }

    public String getUriVoz() {
        return UriVoz;
    }

    public void setUriVoz(String uriVoz) {
        UriVoz = uriVoz;
    }
}
