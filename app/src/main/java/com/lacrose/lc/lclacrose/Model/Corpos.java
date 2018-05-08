package com.lacrose.lc.lclacrose.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rafae on 13/10/2017.
 */

public class Corpos {
    public String id = "";

    public String centro_de_custo = "";
    public List<String> alertas = new ArrayList<>();
    public String createdBy = "";
    public String contraProva = "";
    public Long dataCreate = null;
    public boolean isValid = false;
    public String loteId = "";
    public String obraId = "";
    public String tipo = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }

    public List<String> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<String> alertas) {
        this.alertas = alertas;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getContraProva() {
        return contraProva;
    }

    public void setContraProva(String contraProva) {
        this.contraProva = contraProva;
    }

    public Long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
