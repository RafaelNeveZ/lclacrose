package com.lacrose.lc.lclacrose.Model;

/**
 * Created by rafae on 07/10/2017.
 */

public class Concreteira {
    public String id ="";
    public  String createdBy = "";
    public  Double dataCreate = null;
    public  boolean isValid = false;
    public  String nome = "";
    public  String sigla = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Double getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Double dataCreate) {
        this.dataCreate = dataCreate;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
