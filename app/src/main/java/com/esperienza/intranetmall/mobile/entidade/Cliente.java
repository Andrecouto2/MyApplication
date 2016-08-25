package com.esperienza.intranetmall.mobile.entidade;

import java.util.Date;

/**
 * Created by BONSUCESSO on 16/11/2015.
 */
public class Cliente {

    private int idcliente;
    private String nome;
    private String img_cliente;
    private String link_cliente;
    private int ativo;
    private Date data_cliente;
    private int ativo_cracha;

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImg_cliente() {
        return img_cliente;
    }

    public void setImg_cliente(String img_cliente) {
        this.img_cliente = img_cliente;
    }

    public String getLink_cliente() {
        return link_cliente;
    }

    public void setLink_cliente(String link_cliente) {
        this.link_cliente = link_cliente;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public Date getData_cliente() {
        return data_cliente;
    }

    public void setData_cliente(Date data_cliente) {
        this.data_cliente = data_cliente;
    }

    public int getAtivo_cracha() {
        return ativo_cracha;
    }

    public void setAtivo_cracha(int ativo_cracha) {
        this.ativo_cracha = ativo_cracha;
    }
}
