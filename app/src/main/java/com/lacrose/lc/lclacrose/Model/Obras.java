package com.lacrose.lc.lclacrose.Model;

/**
 * Created by rafae on 13/10/2017.
 */

public class Obras {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String nome;
    String centro_de_custo;

    public Obras() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }
}
