package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Pavimentos {
    private String codigo;
    private float carga;
    private long dataCreate;
    private String createdBy;
    private boolean isValid;


    private HashMap<String, Float> dimenssions;

    public HashMap<String, Float> getDim() {
        return dimenssions;
    }

    public void setDim(String Key,Float value) {
        if(dimenssions == null){
            dimenssions = new HashMap<>();
        }
        this.dimenssions.put(Key,value);
    }


    public long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
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



    public Pavimentos() {
    }

}
