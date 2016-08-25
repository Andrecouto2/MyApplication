package com.esperienza.intranetmall.mobile.entidade;


import org.parceler.Parcel;

import java.util.Hashtable;

/**
 * Created by ThinkPad on 13/01/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ArquivoOS  {

    private int idarquivo;
    private int idos;
    private int iduser;
    private int idshop;
    private int idusermobile;
    private String codgerador;
    private String urlarquivo;
    private String extensao;
    private byte[] datablob;

    public ArquivoOS(){}

    public int getIdarquivo() {
        return idarquivo;
    }

    public void setIdarquivo(int idarquivo) {
        this.idarquivo = idarquivo;
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

    public String getCodgerador() {
        return codgerador;
    }

    public void setCodgerador(String codgerador) {
        this.codgerador = codgerador;
    }

    public String getUrlarquivo() {
        return urlarquivo;
    }

    public void setUrlarquivo(String urlarquivo) {
        this.urlarquivo = urlarquivo;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public byte[] getDatablob() {
        return datablob;
    }

    public void setDatablob(byte[] datablob) {
        this.datablob = datablob;
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
