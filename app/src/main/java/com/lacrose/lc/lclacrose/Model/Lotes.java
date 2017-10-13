package com.lacrose.lc.lclacrose.Model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public  class Lotes {


    private String id;
    private int codigo;
    private String material;
    private String dimenssoes_nominais;
    private String concreteira;
    private int notaFiscal;
    private float volume_do_caminhão;
    private int FCK;
    private int slump;
    private float slumFlow;
    private Date data;
    private String local_concretado;
    private HashMap<String, String> corpos;

    public Lotes() {
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

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(int notaFiscal) {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal_concretado() {
        return local_concretado;
    }

    public void setLocal_concretado(String local_concretado) {
        this.local_concretado = local_concretado;
    }

    public void addCorpo(String remarkKey, String remark) {
        if (corpos == null)
            corpos = new HashMap<>();

        corpos.put(remarkKey, remark);
    }
}
