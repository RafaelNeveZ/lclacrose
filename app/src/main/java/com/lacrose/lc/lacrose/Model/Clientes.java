package com.lacrose.lc.lacrose.Model;

/**
 * Created by rafae on 07/10/2017.
 */

public class Clientes {
    public String id ="";
    public  Double cnpj = null;
    public  String createdBy = "";
    public  Double dataCreate = null;
    public  String email = "";
    public  boolean isValid = false;
    public  String nome = "";
    public  Double telefone1 = null;
    public  Double telefone2 = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCnpj() {
        return cnpj;
    }

    public void setCnpj(Double cnpj) {
        this.cnpj = cnpj;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Double getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(Double telefone1) {
        this.telefone1 = telefone1;
    }

    public Double getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(Double telefone2) {
        this.telefone2 = telefone2;
    }
}
