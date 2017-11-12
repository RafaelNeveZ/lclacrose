package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by rafae on 13/10/2017.
 */

public class Prismas {
    private String codigo;
    private float carga;

    private Object dataCreate;

    public HashMap<String, Boolean> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String Key,Boolean tru) {
        if(createdBy == null){
            createdBy = new HashMap<>();
        }
        this.createdBy.put(Key,tru);
    }

    private HashMap<String, Boolean> createdBy;

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

    public Object getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Object dataCreate) {
        this.dataCreate = dataCreate;
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



    public Prismas() {
    }

}
