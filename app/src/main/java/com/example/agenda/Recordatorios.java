package com.example.agenda;

public class Recordatorios {

    private int RecordatorioId;
    private String Fecha;
    private String Hora;
    private int TareaId;

    public Recordatorios(int recordatorioId, String fecha, String hora, int tareaId) {
        RecordatorioId = recordatorioId;
        Fecha = fecha;
        Hora = hora;
        TareaId = tareaId;
    }

    public Recordatorios(String fecha, String hora) {
        Fecha = fecha;
        Hora = hora;
    }

    public int getRecordatorioId() {
        return RecordatorioId;
    }

    public void setRecordatorioId(int recordatorioId) {
        RecordatorioId = recordatorioId;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public int getTareaId() {
        return TareaId;
    }

    public void setTareaId(int tareaId) {
        TareaId = tareaId;
    }
}
