package com.lacrose.lc.lacrose.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 07/10/2017.
 */

public class Users {
    public String id ="";
    public  Double celular1 = null;
    public  Double celular2 = null;
    public  String centro_de_custo = "";
    public String email ="";
    public String endereco ="";
    public String func ="";
    public boolean isValid = false;
    public String nome ="";
    public List<String> obras = new ArrayList<>();
    //public String password ="";
    public  Double rg = null;
    public String sobreNome ="";
    public  Double telefone = null;
    public String createdBy="";
    public Double dataCreate=null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCelular1() {
        return celular1;
    }

    public void setCelular1(Double celular1) {
        this.celular1 = celular1;
    }

    public Double getCelular2() {
        return celular2;
    }

    public void setCelular2(Double celular2) {
        this.celular2 = celular2;
    }

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
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

    public List<String> getObras() {
        return obras;
    }

    public void setObras(List<String> obras) {
        this.obras = obras;
    }

    public Double getRg() {
        return rg;
    }

    public void setRg(Double rg) {
        this.rg = rg;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public Double getTelefone() {
        return telefone;
    }

    public void setTelefone(Double telefone) {
        this.telefone = telefone;
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
}
