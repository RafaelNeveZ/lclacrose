package com.lacrose.lc.lclacrose.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rafae on 13/10/2017.
 */

public  class PavimentoLotes {


    public String id = "";
    public boolean boletim_gerado = false;
    public String centro_de_custo = "";
    public String classe = "";
    public String codigo = "";
    public List<HashMap<String, String>> corpos = new ArrayList<>();
    public String createdBy = "";
    public Long data = null;
    public Long dataCreate = null;
    public Long dataFab = null;
    public HashMap<String, Integer> dimenssion;
    public String fabricante = "";
    public Double fpk = null;
    public Long hora = null;
    public Double idade = null;
    public String is24 = "";
    public boolean isValid = false;
    public String lote = "";
    public String notaFiscal = "";
    public String obraId = "";
    public Double quantidade = null;
    public boolean rompido = false;
    public String tipo = "";
    public String obs = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getBoletim_gerado() {
        return boletim_gerado;
    }

    public void setBoletim_gerado(boolean boletim_gerado) {
        this.boletim_gerado = boletim_gerado;
    }

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<HashMap<String, String>> getCorpos() {
        return corpos;
    }

    public void setCorpos(List<HashMap<String, String>> corpos) {
        this.corpos = corpos;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public Long getDataFab() {
        return dataFab;
    }

    public void setDataFab(Long dataFab) {
        this.dataFab = dataFab;
    }

    public HashMap<String, Integer> getDimenssion() {
        return dimenssion;
    }

    public void setDimenssion(HashMap<String, Integer> dimenssion) {
        this.dimenssion = dimenssion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getFpk() {
        return fpk;
    }

    public void setFpk(Double fpk) {
        this.fpk = fpk;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }

    public Double getIdade() {
        return idade;
    }

    public void setIdade(Double idade) {
        this.idade = idade;
    }

    public String getIs24() {
        return is24;
    }

    public void setIs24(String is24) {
        this.is24 = is24;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraId) {
        this.obraId = obraId;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public boolean getRompido() {
        return rompido;
    }

    public void setRompido(boolean rompido) {
        this.rompido = rompido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}

