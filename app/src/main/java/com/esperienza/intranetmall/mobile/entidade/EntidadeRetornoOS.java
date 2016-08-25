package com.esperienza.intranetmall.mobile.entidade;

import java.util.List;

/**
 * Created by ThinkPad on 25/02/2016.
 */
public class EntidadeRetornoOS {

    private List<OrdemServicoSetor> setores;
    private List<RegraOrdemServico> regras;
    private List<Calendario> calendarios;
    private List<OrdemServico> listaordemdeservico;
    private List<Usuario> listausuariodestino;

    public List<OrdemServicoSetor> getSetores() {
        return setores;
    }

    public void setSetores(List<OrdemServicoSetor> setores) {
        this.setores = setores;
    }

    public List<RegraOrdemServico> getRegras() {
        return regras;
    }

    public void setRegras(List<RegraOrdemServico> regras) {
        this.regras = regras;
    }

    public List<Calendario> getCalendarios() {
        return calendarios;
    }

    public void setCalendarios(List<Calendario> calendarios) {
        this.calendarios = calendarios;
    }

    public List<OrdemServico> getListaordemdeservico() {
        return listaordemdeservico;
    }

    public void setListaordemdeservico(List<OrdemServico> listaordemdeservico) {
        this.listaordemdeservico = listaordemdeservico;
    }

    public List<Usuario> getListausuariodestino() {
        return listausuariodestino;
    }

    public void setListausuariodestino(List<Usuario> listausuariodestino) {
        this.listausuariodestino = listausuariodestino;
    }
}
