package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 28/12/2015.
 */
public class Dispositivo {

    private int iddispositivo;
    private int idshop;
    private int iduser;
    private String iddeviceregistration;


    public int getIddispositivo() {
        return iddispositivo;
    }

    public void setIddispositivo(int iddispositivo) {
        this.iddispositivo = iddispositivo;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getIddeviceregistration() {
        return iddeviceregistration;
    }

    public void setIddeviceregistration(String iddeviceregistration) {
        this.iddeviceregistration = iddeviceregistration;
    }
}
