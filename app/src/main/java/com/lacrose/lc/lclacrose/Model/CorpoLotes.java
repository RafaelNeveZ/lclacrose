package com.lacrose.lc.lclacrose.Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public  class CorpoLotes {
    //nao iformado = ""

    public String id = "";
    public String alvenaria = "";
    public boolean boletim_gerado = false;
    public String centro_de_custo = "";
    public String codigo = "";
    public String concreteira = "";
    public List<HashMap<String, String>> corpos = new ArrayList<>();
    public String createdBy = "";
    public Long data = null;
    public Long dataCreate = null;
    public Long dataFab = null;
    public HashMap<String, Integer> dimenssion;
    public Double fck = null;
    public Long hora = null;
    public String is24 = "";
    public boolean isValid = false;
    public String local_concretado = "";
    public String material = "";
    public String notaFiscal = "";
    public String obraId = "";
    public String obs = "";
    public Double quantidade = null;
    public boolean rompido = false;
    public String slump = "";
    public String tipo = "";
    public Double volume = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlvenaria() {
        return alvenaria;
    }

    public void setAlvenaria(String alvenaria) {
        this.alvenaria = alvenaria;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConcreteira() {
        return concreteira;
    }

    public void setConcreteira(String concreteira) {
        this.concreteira = concreteira;
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

    public Double getFck() {
        return fck;
    }

    public void setFck(Double fck) {
        this.fck = fck;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
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

    public String getLocal_concretado() {
        return local_concretado;
    }

    public void setLocal_concretado(String local_concretado) {
        this.local_concretado = local_concretado;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getSlump() {
        return slump;
    }

    public void setSlump(String slump) {
        this.slump = slump;
    }
}
