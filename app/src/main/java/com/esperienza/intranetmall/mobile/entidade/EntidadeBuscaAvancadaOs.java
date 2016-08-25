package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 15/07/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class EntidadeBuscaAvancadaOs {

    private int iduser;
    private int idshop;
    private String datainicial;
    private String datafinal;
    private ArrayList<Integer> listasstatus;
    private ArrayList<Integer> listatiposervico;

    public EntidadeBuscaAvancadaOs()
    {
        listasstatus= new ArrayList<>();
        listatiposervico= new ArrayList<>();
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

    public String getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(String datainicial) {
        this.datainicial = datainicial;
    }

    public String getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(String datafinal) {
        this.datafinal = datafinal;
    }

    public ArrayList<Integer> getListasstatus() {
        return listasstatus;
    }

    public void setListasstatus(ArrayList<Integer> listasstatus) {
        this.listasstatus = listasstatus;
    }

    public ArrayList<Integer> getListatiposervico() {
        return listatiposervico;
    }

    public void setListatiposervico(ArrayList<Integer> listatiposervico) {
        this.listatiposervico = listatiposervico;
    }
}
