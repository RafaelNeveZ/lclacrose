package com.lacrose.lc.lclacrose.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 13/10/2017.
 */

public class Obras {

    public String id="";
    public String alvenaria="";
    public String centro_de_custo="";
    public String cliente="";
    public Double cnpj=null;
    public List<String> concreteiras = new ArrayList<>();
    public String createdBy="";
    public Double dataCreate=null;
    public String emailBoletim="";
    public String engenheiro="";
    public String formalizacao="";
    public String is24="";
    public boolean isValid=false;
    public List<String> laboratoristas = new ArrayList<>();
    public String local="";
    public String local_de_rompimento="";
    public List<String> materiais = new ArrayList<>();
    public String nome="";
    public Double prazo_boletim=null;
    public Double qtdForma=null;
    public Double qtdPrensa=null;
    public Double qtdRetifica=null;
    public Double qtdSlumpFlow=null;
    public Double qtdSlumpTest=null;
    public List<String> tecnicos = new ArrayList<>();
    public Double telefone1=null;
    public Double telefone2=null;
    public String tipoCura="";
    public List<String> tipoLotes = new ArrayList<>();
    public String tipoPreparo="";
    public String valid="";
    public String clienteNome="";

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

    public String getCentro_de_custo() {
        return centro_de_custo;
    }

    public void setCentro_de_custo(String centro_de_custo) {
        this.centro_de_custo = centro_de_custo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getCnpj() {
        return cnpj;
    }

    public void setCnpj(Double cnpj) {
        this.cnpj = cnpj;
    }

    public List<String> getConcreteiras() {
        return concreteiras;
    }

    public void setConcreteiras(List<String> concreteiras) {
        this.concreteiras = concreteiras;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmailBoletim() {
        return emailBoletim;
    }

    public void setEmailBoletim(String emailBoletim) {
        this.emailBoletim = emailBoletim;
    }

    public String getEngenheiro() {
        return engenheiro;
    }

    public void setEngenheiro(String engenheiro) {
        this.engenheiro = engenheiro;
    }

    public String getFormalizacao() {
        return formalizacao;
    }

    public void setFormalizacao(String formalizacao) {
        this.formalizacao = formalizacao;
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

    public List<String> getLaboratoristas() {
        return laboratoristas;
    }

    public void setLaboratoristas(List<String> laboratoristas) {
        this.laboratoristas = laboratoristas;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal_de_rompimento() {
        return local_de_rompimento;
    }

    public void setLocal_de_rompimento(String local_de_rompimento) {
        this.local_de_rompimento = local_de_rompimento;
    }

    public Double getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Double dataCreate) {
        this.dataCreate = dataCreate;
    }

    public List<String> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<String> materiais) {
        this.materiais = materiais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrazo_boletim() {
        return prazo_boletim;
    }

    public void setPrazo_boletim(Double prazo_boletim) {
        this.prazo_boletim = prazo_boletim;
    }

    public Double getQtdForma() {
        return qtdForma;
    }

    public void setQtdForma(Double qtdForma) {
        this.qtdForma = qtdForma;
    }

    public Double getQtdPrensa() {
        return qtdPrensa;
    }

    public void setQtdPrensa(Double qtdPrensa) {
        this.qtdPrensa = qtdPrensa;
    }

    public Double getQtdRetifica() {
        return qtdRetifica;
    }

    public void setQtdRetifica(Double qtdRetifica) {
        this.qtdRetifica = qtdRetifica;
    }

    public Double getQtdSlumpFlow() {
        return qtdSlumpFlow;
    }

    public void setQtdSlumpFlow(Double qtdSlumpFlow) {
        this.qtdSlumpFlow = qtdSlumpFlow;
    }

    public Double getQtdSlumpTest() {
        return qtdSlumpTest;
    }

    public void setQtdSlumpTest(Double qtdSlumpTest) {
        this.qtdSlumpTest = qtdSlumpTest;
    }

    public List<String> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<String> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public Double getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(Double telefone1) {
        this.telefone1 = telefone1;
    }

    public Double getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(Double telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTipoCura() {
        return tipoCura;
    }

    public void setTipoCura(String tipoCura) {
        this.tipoCura = tipoCura;
    }

    public List<String> getTipoLotes() {
        return tipoLotes;
    }

    public void setTipoLotes(List<String> tipoLotes) {
        this.tipoLotes = tipoLotes;
    }

    public String getTipoPreparo() {
        return tipoPreparo;
    }

    public void setTipoPreparo(String tipoPreparo) {
        this.tipoPreparo = tipoPreparo;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
}
