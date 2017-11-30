package com.lacrose.lc.lclacrose.Model;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public  class PavimentoLotes {


    private String id;
    private int codigo;
    private long idade;
    private long notaFiscal;
    private String lote;
    private int FPK;
    private long data;
    private long dataFab;
    private String fabricante;
    private String obs;
    private HashMap<String, Integer> dimenssoes;
    private long dataCreate;
    private boolean isValid;
    private String createdBy;
    private String tipo;




    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getIdade() {
        return idade;
    }

    public void setIdade(long idade) {
        this.idade = idade;
    }

    public long getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(long notaFiscal) {
        this.notaFiscal = notaFiscal;
    }



    public int getFPK() {
        return FPK;
    }

    public void setFPK(int FPK) {
        this.FPK = FPK;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public long getDataFab() {
        return dataFab;
    }

    public void setDataFab(long dataFab) {
        this.dataFab = dataFab;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public HashMap<String, Integer> getDimenssoes() {
        return dimenssoes;
    }

    public void setDimenssoes(HashMap<String, Integer> dimenssoes) {
        this.dimenssoes = dimenssoes;
    }

    public PavimentoLotes() {
    }

    public long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
