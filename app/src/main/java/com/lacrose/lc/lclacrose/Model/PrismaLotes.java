package com.lacrose.lc.lclacrose.Model;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public  class PrismaLotes {


    private String id;
    private int codigo;
    private long idade;
    private long notaFiscal;
    private String Lote;
    private int FPK;
    private long data;
    private long dataFab;
    private long dataAssent;
    private String fabricante;
    private String more;
    private HashMap<String, Integer> dimenssoes;
    private boolean funcEstrutural;

    public long getDataFab() {
        return dataFab;
    }

    public void setDataFab(long dataFab) {
        this.dataFab = dataFab;
    }

    public long getDataAssent() {
        return dataAssent;
    }

    public void setDataAssent(long dataAssent) {
        this.dataAssent = dataAssent;
    }

    public boolean isFuncEstrutural() {
        return funcEstrutural;
    }

    public void setFuncEstrutural(boolean funcEstrutural) {
        this.funcEstrutural = funcEstrutural;
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

    public PrismaLotes() {
    }
}
