package com.esperienza.intranetmall.mobile.entidade;

import java.util.List;

/**
 * Created by ThinkPad on 29/12/2015.
 */
public class Home {

    private int idhome;
    private int iduser;
    private int idshop;
    private int aguardandoaprovacao;
    private int autorizacao;
    private int naoautorizado;
    private int emexecucao;
    private int circularnaolida;
    private int qtdfuncionario;
    private List<Destaque> destaque;


    public int getIdhome() {
        return idhome;
    }

    public void setIdhome(int idhome) {
        this.idhome = idhome;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }

    public int getAguardandoaprovacao() {
        return aguardandoaprovacao;
    }

    public void setAguardandoaprovacao(int aguardandoaprovacao) {
        this.aguardandoaprovacao = aguardandoaprovacao;
    }

    public int getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(int autorizacao) {
        this.autorizacao = autorizacao;
    }

    public int getNaoautorizado() {
        return naoautorizado;
    }

    public void setNaoautorizado(int naoautorizado) {
        this.naoautorizado = naoautorizado;
    }

    public int getEmexecucao() {
        return emexecucao;
    }

    public void setEmexecucao(int emexecucao) {
        this.emexecucao = emexecucao;
    }

    public int getCircularnaolida() {
        return circularnaolida;
    }

    public void setCircularnaolida(int circularnaolida) {
        this.circularnaolida = circularnaolida;
    }


    public int getQtdfuncionario() {return qtdfuncionario;}

    public void setQtdfuncionario(int qtdfuncionario) {
        this.qtdfuncionario = qtdfuncionario;
    }

    public List<Destaque> getDestaque() {
        return destaque;
    }

    public void setDestaque(List<Destaque> destaque) {
        this.destaque = destaque;
    }
}
