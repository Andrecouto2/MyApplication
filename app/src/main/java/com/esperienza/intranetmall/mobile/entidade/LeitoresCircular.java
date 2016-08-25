package com.esperienza.intranetmall.mobile.entidade;

import java.util.Date;

/**
 * Created by ThinkPad on 21/12/2015.
 */
public class LeitoresCircular {

      private int idleitorcircular;
      private int iduser;
      private String empresa;
      private String nome;
      private int idcircular;
      private Date data_acessou;
      private int retorno;
      private int idusermobile;
      private int idshop;


    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdcircular() {
        return idcircular;
    }

    public void setIdcircular(int idcircular) {
        this.idcircular = idcircular;
    }

    public Date getData_acessou() {
        return data_acessou;
    }

    public void setData_acessou(Date data_acessou) {
        this.data_acessou = data_acessou;
    }

    public int getRetorno() {
        return retorno;
    }

    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }

    public int getIdleitorcircular() {
        return idleitorcircular;
    }

    public void setIdleitorcircular(int idleitorcircular) {
        this.idleitorcircular = idleitorcircular;
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
