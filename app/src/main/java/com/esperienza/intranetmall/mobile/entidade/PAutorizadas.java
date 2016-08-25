package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 07/03/2016.
 */
import java.util.Hashtable;


public class PAutorizadas {

    private String nome;
    private String rg;
    private String empresa;

    public PAutorizadas(){}

    public PAutorizadas(String nome,String rg,String empresa)
    {
        this.setNome(nome);
        this.setRg(rg);
        this.setEmpresa(empresa);
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
}
