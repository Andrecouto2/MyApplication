package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

/**
 * Created by ThinkPad on 13/01/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class PessoasAutorizadasOS {

    private int id;
    private int idos;
    private int iduser;
    private int idshop;
    private String nome;
    private String rg;
    private String empresa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdos() {
        return idos;
    }

    public void setIdos(int idos) {
        this.idos = idos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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
}
