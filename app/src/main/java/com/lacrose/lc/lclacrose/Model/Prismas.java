package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Prismas {
    private String codigo;
    private float carga;
    private String tipo;
    private long data;
    private HashMap <String, Object> timestamp;


    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getCarga() {
        return carga;
    }

    public void setCarga(float carga) {
        this.carga = carga;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



    public Prismas() {
    }

    public  void setTimeStamp(){
        if(timestamp == null)
            timestamp = new HashMap<>();

        timestamp.put("timeStamp", ServerValue.TIMESTAMP);
    }
}