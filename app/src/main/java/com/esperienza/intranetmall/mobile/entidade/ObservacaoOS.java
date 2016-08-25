package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by ThinkPad on 13/01/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ObservacaoOS {

    private int idcomentario;
    private int idos;
    private int iduser;
    private int idshop;
    private int idusermobile;
    private Date datacad;
    private String horacad;
    private String observacoes;
    private Usuario usuario;

    public int getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(int idcomentario) {
        this.idcomentario = idcomentario;
    }

    public int getIdos() {
        return idos;
    }

    public void setIdos(int idos) {
        this.idos = idos;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public Date getDatacad() {
        return datacad;
    }

    public void setDatacad(Date datacad) {
        this.datacad = datacad;
    }

    public String getHoracad() {
        return horacad;
    }

    public void setHoracad(String horacad) {
        this.horacad = horacad;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }

    public int getIdusermobile() {
        return idusermobile;
    }

    public void setIdusermobile(int idusermobile) {
        this.idusermobile = idusermobile;
    }
}
