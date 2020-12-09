package com.example.agenda;

import java.time.LocalDateTime;
import java.util.List;

public class Tareas {
    private int TareaId;
    private String Nombre;
    private String Descripcion;
    private String UriFoto;
    private String UriVideo;
    private String UriVoz;
    private boolean Activo;

    public Tareas() {}

    public Tareas(int tareaId, String nombre, String descripcion, String uriFoto, String uriVideo, String uriVoz, boolean Activo) {
        TareaId = tareaId;
        Nombre = nombre;
        Descripcion = descripcion;
        UriFoto = uriFoto;
        UriVideo = uriVideo;
        UriVoz = uriVoz;
        Activo = Activo;
    }

    public int getTareaId() {
        return TareaId;
    }

    public void setTareaId(int tareaId) {
        TareaId = tareaId;
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

    public boolean EsActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }
}
