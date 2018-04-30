package com.lacrose.lc.lclacrose.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rafae on 13/10/2017.
 */

public  class BlocoLotes {


    public String id = "";
    public boolean boletim_gerado = false;
    public String centro_de_custo = "";
    public String classe = "";
    public Double codigo = null;
    public List<HashMap<String, String>> corpos = new ArrayList<>();
    public String createdBy = "";
    public Double data = null;
    public Double dataCreate = null;
    public Double dataFab = null;
    public HashMap<String, Integer> dimenssoes;
    public String fabricante = "";
    public Double fbk = null;
    public String funcEstrutural = "";
    public Double hora = null;
    public Double idade = null;
    public String is24 = "";
    public boolean isValid = false;
    public String lote = "";
    public String notaFiscal = "";
    public String obraId = "";
    public Double quantidade = null;
    public boolean rompido = false;
    public String tipo = "";

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

    public Double getCodigo() {
        return codigo;
    }

    public void setCodigo(Double codigo) {
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

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public Double getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Double dataCreate) {
        this.dataCreate = dataCreate;
    }

    public Double getDataFab() {
        return dataFab;
    }

    public void setDataFab(Double dataFab) {
        this.dataFab = dataFab;
    }

    public HashMap<String, Integer> getDimenssoes() {
        return dimenssoes;
    }

    public void setDimenssoes(HashMap<String, Integer> dimenssoes) {
        this.dimenssoes = dimenssoes;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getFbk() {
        return fbk;
    }

    public void setFbk(Double fbk) {
        this.fbk = fbk;
    }

    public String getFuncEstrutural() {
        return funcEstrutural;
    }

    public void setFuncEstrutural(String funcEstrutural) {
        this.funcEstrutural = funcEstrutural;
    }

    public Double getHora() {
        return hora;
    }

    public void setHora(Double hora) {
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
}
