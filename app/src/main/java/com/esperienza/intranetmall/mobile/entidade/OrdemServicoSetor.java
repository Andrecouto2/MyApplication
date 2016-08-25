package com.esperienza.intranetmall.mobile.entidade;

import java.util.List;

/**
 * Created by ThinkPad on 04/02/2016.
 */
public class OrdemServicoSetor {

    private int idordemservicosetor;
    private int idshop;
    private String titulo;
    private int ativo;
    private List<TipoServico> tipoServicos;

    public int getIdordemservicosetor() {
        return idordemservicosetor;
    }

    public void setIdordemservicosetor(int idordemservicosetor) {
        this.idordemservicosetor = idordemservicosetor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public List<TipoServico> getTipoServicos() {
        return tipoServicos;
    }

    public void setTipoServicos(List<TipoServico> tipoServicos) {
        this.tipoServicos = tipoServicos;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
