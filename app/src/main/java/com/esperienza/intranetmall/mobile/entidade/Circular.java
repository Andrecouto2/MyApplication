package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by BONSUCESSO on 12/11/2015.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Circular {

    private int idcircular;

    private String titulo;

    private Date data_cadastro;

    private int acesso;

    private String nomearquivo;

    private int iduser;

    private int flagleitura;

    private String anomes;

    private int idusermobile;

    private int idshop;



    public int getIdcircular() {
        return idcircular;
    }

    public void setIdcircular(int idcircular) {
        this.idcircular = idcircular;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getFlagleitura() {
        return flagleitura;
    }

    public void setFlagleitura(int flagleitura) {
        this.flagleitura = flagleitura;
    }

    public String getAnomes() {
        return anomes;
    }

    public void setAnomes(String anomes) {
        this.anomes = anomes;
    }


    public int getIdusermobile() {
        return idusermobile;
    }

    public void setIdusermobile(int idusermobile) {
        this.idusermobile = idusermobile;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
