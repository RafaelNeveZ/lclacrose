package com.lacrose.lc.lclacrose.Model;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public  class BlocoLotes {


    private String id;
    private int codigo;
    private long idade;
    private long notaFiscal;
    private String Lote;
    private int FBK;
    private long data;
    private long datafab;
    private String fabricante;
    private String more;
    private HashMap<String, Integer> dimenssoes;
    private boolean func_estrutural;

    public boolean isFunc_estrutural() {
        return func_estrutural;
    }

    public void setFunc_estrutural(boolean func_estrutural) {
        this.func_estrutural = func_estrutural;
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

    public String getLote() {
        return Lote;
    }

    public void setLote(String lote) {
        Lote = lote;
    }

    public int getFBK() {
        return FBK;
    }

    public void setFBK(int FBK) {
        this.FBK = FBK;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public long getDatafab() {
        return datafab;
    }

    public void setDatafab(long datafab) {
        this.datafab = datafab;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public HashMap<String, Integer> getDimenssoes() {
        return dimenssoes;
    }

    public void setDimenssoes(HashMap<String, Integer> dimenssoes) {
        this.dimenssoes = dimenssoes;
    }

    public BlocoLotes() {
    }
}