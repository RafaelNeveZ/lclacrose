package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Blocos {
    private String codigo;
    private float carga;

    private float espessura_longitudinal, espessura_transvessal;
    private String createdBy;
    private HashMap<String, Float> dimenssions;
    private long dataCreate;
    public HashMap<String, Float> getDim() {
        return dimenssions;
    }
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setDim(String Key,Float value) {
        if(dimenssions == null){
            dimenssions = new HashMap<>();
        }
        this.dimenssions.put(Key,value);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public float getEspessura_longitudinal() {
        return espessura_longitudinal;
    }

    public void setEspessura_longitudinal(float espessura_longitudinal) {
        this.espessura_longitudinal = espessura_longitudinal;
    }

    public float getEspessura_transvessal() {
        return espessura_transvessal;
    }

    public void setEspessura_transvessal(float espessura_transvessal) {
        this.espessura_transvessal = espessura_transvessal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getCarga() {
        return carga;
    }

    public void setCarga(float carga) {
        this.carga = carga;
    }


    public Blocos() {
    }


}
