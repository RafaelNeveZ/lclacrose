package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Blocos {
    public String id="";
    public Double altura=null;
    public Double carga=null;
    public String centro_de_custo = "";
    public String codigo = "";
    public Double comprimento=null;
    public String createdBy = "";
    public Long dataCreate = null;
    public Double esspesura_Long=null;
    public Double esspesura_Tranv=null;
    public Double largura=null;
    public String loteId = "";
    public String obraId = "";
    public Double resistencia=null;
    public String tipo = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getCarga() {
        return carga;
    }

    public void setCarga(Double carga) {
        this.carga = carga;
    }

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public Double getEsspesura_Long() {
        return esspesura_Long;
    }

    public void setEsspesura_Long(Double esspesura_Long) {
        this.esspesura_Long = esspesura_Long;
    }

    public Double getEsspesura_Tranv() {
        return esspesura_Tranv;
    }

    public void setEsspesura_Tranv(Double esspesura_Tranv) {
        this.esspesura_Tranv = esspesura_Tranv;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public String getLoteId() {
        return loteId;
    }

    public void setLoteId(String loteId) {
        this.loteId = loteId;
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraId) {
        this.obraId = obraId;
    }

    public Double getResistencia() {
        return resistencia;
    }

    public void setResistencia(Double resistencia) {
        this.resistencia = resistencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
