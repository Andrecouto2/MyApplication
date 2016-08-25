package com.esperienza.intranetmall.mobile.entidade;

import android.bluetooth.BluetoothClass;

import java.util.List;

/**
 * Created by ThinkPad on 21/03/2016.
 */
public class EntidadeLogin {

    private Usuario usuario;
    private Dispositivo dispositivo;
    private Home home;
    private List<OrdemServicoSetor> ordemServicoSetorList;
    private List<CamposObrigatoriosFnc> camposObrigatoriosFncList;
    private List<Calendario> calendarioList;
    private List<RegraOrdemServico> regraOrdemServicoList;
    private List<Usuario> usuarioList;


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }


    public List<CamposObrigatoriosFnc> getCamposObrigatoriosFncList() {
        return camposObrigatoriosFncList;
    }

    public void setCamposObrigatoriosFncList(List<CamposObrigatoriosFnc> camposObrigatoriosFncList) {
        this.camposObrigatoriosFncList = camposObrigatoriosFncList;
    }

    public List<Calendario> getCalendarioList() {
        return calendarioList;
    }

    public void setCalendarioList(List<Calendario> calendarioList) {
        this.calendarioList = calendarioList;
    }

    public List<RegraOrdemServico> getRegraOrdemServicoList() {
        return regraOrdemServicoList;
    }

    public void setRegraOrdemServicoList(List<RegraOrdemServico> regraOrdemServicoList) {
        this.regraOrdemServicoList = regraOrdemServicoList;
    }

    public List<OrdemServicoSetor> getOrdemServicoSetorList() {
        return ordemServicoSetorList;
    }

    public void setOrdemServicoSetorList(List<OrdemServicoSetor> ordemServicoSetorList) {
        this.ordemServicoSetorList = ordemServicoSetorList;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
}
