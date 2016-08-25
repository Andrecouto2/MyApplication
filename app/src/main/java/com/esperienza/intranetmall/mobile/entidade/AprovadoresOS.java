package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

/**
 * Created by ThinkPad on 28/01/2016.
 */
@Parcel(Parcel.Serialization.BEAN)
public class AprovadoresOS {

      private int iduser;
      private int idos;
      private int idusermobile;
      private int idshop;
      private int alcadas;
      private int ordem;
      private int acao;
      private int suplente;
      private String nome;
      private String email;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdos() {
        return idos;
    }

    public void setIdos(int idos) {
        this.idos = idos;
    }

    public int getAlcadas() {
        return alcadas;
    }

    public void setAlcadas(int alcadas) {
        this.alcadas = alcadas;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getAcao() {
        return acao;
    }

    public void setAcao(int acao) {
        this.acao = acao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getSuplente() {
        return suplente;
    }

    public void setSuplente(int suplente) {
        this.suplente = suplente;
    }
}
