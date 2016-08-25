package com.esperienza.intranetmall.mobile.entidade;

import java.util.Date;

/**
 * Created by ThinkPad on 12/02/2016.
 */
public class Calendario {

    private Date ddata;
    private int dutil;
    private int feriado;
    private int idshop;

    public Date getDdata() {
        return ddata;
    }

    public void setDdata(Date ddata) {
        this.ddata = ddata;
    }

    public int getDutil() {
        return dutil;
    }

    public void setDutil(int dutil) {
        this.dutil = dutil;
    }

    public int getFeriado() {
        return feriado;
    }

    public void setFeriado(int feriado) {
        this.feriado = feriado;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
