package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 18/04/2016.
 */
public class CamposObrigatoriosFnc {

    private int id;
    private int iduser;
    private int idshop;
    private int obrigatorio;
    private String campo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(int obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
