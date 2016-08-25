package com.esperienza.intranetmall.mobile.entidade;



import java.util.Hashtable;


/**
 * Created by ThinkPad on 07/03/2016.
 */
public class Ordem  {

    private String idShopping;
    private String iduser;
    private String idtipo;
    private String datainicio;
    private String datafim;
    private String horainicio;
    private String horafim;
    private String nomelojista;
    private String nomesolicita;
    private String telefone;
    private String email;
    private String descricao;
    private String iddestino;
    private String descricaotipo;
    private Arquivos[] Arquivos_;
    private PAutorizadas[] Pessoas_;

    public Ordem(){}




    public String getIdShopping() {
        return idShopping;
    }

    public void setIdShopping(String idShopping) {
        this.idShopping = idShopping;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(String idtipo) {
        this.idtipo = idtipo;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafim() {
        return horafim;
    }

    public void setHorafim(String horafim) {
        this.horafim = horafim;
    }

    public String getNomelojista() {
        return nomelojista;
    }

    public void setNomelojista(String nomelojista) {
        this.nomelojista = nomelojista;
    }

    public String getNomesolicita() {
        return nomesolicita;
    }

    public void setNomesolicita(String nomesolicita) {
        this.nomesolicita = nomesolicita;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIddestino() {
        return iddestino;
    }

    public void setIddestino(String iddestino) {
        this.iddestino = iddestino;
    }

    public Arquivos[] getArquivos_() {
        return Arquivos_;
    }

    public void setArquivos_(Arquivos[] arquivos_) {
        Arquivos_ = arquivos_;
    }

    public PAutorizadas[] getPessoas_() {
        return Pessoas_;
    }

    public void setPessoas_(PAutorizadas[] pessoas_) {
        Pessoas_ = pessoas_;
    }

    public String getDescricaotipo() {
        return descricaotipo;
    }

    public void setDescricaotipo(String descricaotipo) {
        this.descricaotipo = descricaotipo;
    }
}
