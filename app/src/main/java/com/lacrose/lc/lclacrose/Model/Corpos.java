package com.lacrose.lc.lclacrose.Model;

import java.util.Date;

/**
 * Created by rafae on 13/10/2017.
 */

public class Corpos {
    private String codigo;
    private float carga;
    private String tipo;
    private Date data;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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



    public Corpos() {
    }
}
