package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Blocos {
    private String codigo;
    private float carga;
    private Object dataCreate;
    private float altura,largura,comprimento;
    private float espessura_longitudinal, espessura_transvessal;

    public Object getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Object dataCreate) {
        this.dataCreate = dataCreate;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public float getEspessura_longitudinal() {
        return espessura_longitudinal;
    }

    public void setEspessura_longitudinal(float espessura_longitudinal) {
        this.espessura_longitudinal = espessura_longitudinal;
    }

    public float getEspessura_transvessal() {
        return espessura_transvessal;
    }

    public void setEspessura_transvessal(float espessura_transvessal) {
        this.espessura_transvessal = espessura_transvessal;
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


    public Blocos() {
    }


}
