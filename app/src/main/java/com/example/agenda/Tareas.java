package com.example.agenda;

import java.time.LocalDateTime;
import java.util.List;

public class Tareas {
    private int TareaId;
    private String Nombre;
    private String Descripcion;
    private String FechaHora;
    private List<LocalDateTime> recordatorios;
    private String UriFoto;
    private String UriVideo;
    private String UriVoz;

    public Tareas() {}

    public Tareas(int tareaId, String nombre, String descripcion, String fechaHora, List<LocalDateTime> recordatorios, String uriFoto, String uriVideo, String uriVoz) {
        TareaId = tareaId;
        Nombre = nombre;
        Descripcion = descripcion;
        FechaHora = fechaHora;
        this.recordatorios = recordatorios;
        UriFoto = uriFoto;
        UriVideo = uriVideo;
        UriVoz = uriVoz;
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

    public String getFechaHora() {
        return FechaHora;
    }

    public void setFechaHora(String fechaHora) {
        FechaHora = fechaHora;
    }

    public List<LocalDateTime> getRecordatorios() {
        return recordatorios;
    }

    public void setRecordatorios(List<LocalDateTime> recordatorios) {
        this.recordatorios = recordatorios;
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
