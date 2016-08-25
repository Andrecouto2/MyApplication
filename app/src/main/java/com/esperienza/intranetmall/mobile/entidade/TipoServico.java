package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

/**
 * Created by ThinkPad on 04/02/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class TipoServico {

    private int idtipo;
    private int idshop;
    private String descricao;
    private int iddepto;
    private int idordemservicosetor;
    private String obs;
    private int obrigatorioobs;
    private int obrigatorioanexo;
    private int ativo;
    private int forafuncionamento;

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIddepto() {
        return iddepto;
    }

    public void setIddepto(int iddepto) {
        this.iddepto = iddepto;
    }

    public int getIdordemservicosetor() {
        return idordemservicosetor;
    }

    public void setIdordemservicosetor(int idordemservicosetor) {
        this.idordemservicosetor = idordemservicosetor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getObrigatorioobs() {
        return obrigatorioobs;
    }

    public void setObrigatorioobs(int obrigatorioobs) {
        this.obrigatorioobs = obrigatorioobs;
    }

    public int getObrigatorioanexo() {
        return obrigatorioanexo;
    }

    public void setObrigatorioanexo(int obrigatorioanexo) {
        this.obrigatorioanexo = obrigatorioanexo;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getForafuncionamento() {
        return forafuncionamento;
    }

    public void setForafuncionamento(int forafuncionamento) {
        this.forafuncionamento = forafuncionamento;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
