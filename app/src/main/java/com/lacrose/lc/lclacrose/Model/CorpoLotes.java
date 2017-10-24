package com.lacrose.lc.lclacrose.Model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public  class CorpoLotes {


    private String id;
    private int codigo;
    private String material;
    private String dimenssoes_nominais;
    private String concreteira;
    private long idade;
    private long notaFiscal;
    private float volume_do_caminhão;
    private int FCK;
    private int slump;
    private float slumFlow;
    private long data;
    private String local_concretado;
    private String more;
    private HashMap<String, Integer> dimenssoes;

    public HashMap<String, Integer> getDimenssoes() {
        return dimenssoes;
    }

    public void setDimenssoes(HashMap<String, Integer> dimenssoes) {
        this.dimenssoes = dimenssoes;
    }


    public CorpoLotes() {
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDimenssoes_nominais() {
        return dimenssoes_nominais;
    }

    public void setDimenssoes_nominais(String dimenssoes_nominais) {
        this.dimenssoes_nominais = dimenssoes_nominais;
    }

    public String getConcreteira() {
        return concreteira;
    }

    public void setConcreteira(String concreteira) {
        this.concreteira = concreteira;
    }

    public long getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(long notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public float getVolume_do_caminhão() {
        return volume_do_caminhão;
    }

    public void setVolume_do_caminhão(float volume_do_caminhão) {
        this.volume_do_caminhão = volume_do_caminhão;
    }

    public int getFCK() {
        return FCK;
    }

    public void setFCK(int FCK) {
        this.FCK = FCK;
    }

    public int getSlump() {
        return slump;
    }

    public void setSlump(int slump) {
        this.slump = slump;
    }

    public float getSlumFlow() {
        return slumFlow;
    }

    public void setSlumFlow(float slumFlow) {
        this.slumFlow = slumFlow;
    }

    public long getIdade() {
        return idade;
    }

    public void setIdade(long idade) {
        this.idade = idade;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getLocal_concretado() {
        return local_concretado;
    }

    public void setLocal_concretado(String local_concretado) {
        this.local_concretado = local_concretado;
    }

    public void addDimension(String Key, int value) {
        if (dimenssoes == null)
            dimenssoes = new HashMap<>();

        dimenssoes.put(Key, value);
    }
}