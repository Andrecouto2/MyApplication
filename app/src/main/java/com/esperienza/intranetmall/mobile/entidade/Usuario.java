package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by BONSUCESSO on 11/11/2015.
 */

import org.parceler.Parcel;

import java.sql.Time;
import java.util.Date;
@Parcel(Parcel.Serialization.BEAN)
public class Usuario {

    private int iduser;

    private int idshop;

    private String empresa;

    private String luc;

    private String contrato;

    private String login;

    private String senha;

    private int contadordeacesso;

    private Date dataacesso;

    private String horaacesso;

    private int ativo_inativo;

    private String nomeresponsavel;

    private String telefone1;

    private String telefone2;

    private String email;

    private String email2;

    private String rsocial;

    private String piso;

    private String depto;

    private int primeiroacesso;

    private int id_cracha_tipo;

    private int codpessoa;

    private int acesso;

    private String imglogoshop;

    private int tipo;

    public Usuario(){

    }


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

    public String getLuc() {
        return luc;
    }

    public void setLuc(String luc) {
        this.luc = luc;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getContadordeacesso() {
        return contadordeacesso;
    }

    public void setContadordeacesso(int contadordeacesso) {
        this.contadordeacesso = contadordeacesso;
    }

    public Date getDataacesso() {
        return dataacesso;
    }

    public void setDataacesso(Date dataacesso) {
        this.dataacesso = dataacesso;
    }

    public String getHoraacesso() {
        return horaacesso;
    }

    public void setHoraacesso(String horaacesso) {
        this.horaacesso = horaacesso;
    }

    public int getAtivo_inativo() {
        return ativo_inativo;
    }

    public void setAtivo_inativo(int ativo_inativo) {
        this.ativo_inativo = ativo_inativo;
    }

    public String getNomeresponsavel() {
        return nomeresponsavel;
    }

    public void setNomeresponsavel(String nomeresponsavel) {
        this.nomeresponsavel = nomeresponsavel;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getRsocial() {
        return rsocial;
    }

    public void setRsocial(String rsocial) {
        this.rsocial = rsocial;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public int getPrimeiroacesso() {
        return primeiroacesso;
    }

    public void setPrimeiroacesso(int primeiroacesso) {
        this.primeiroacesso = primeiroacesso;
    }

    public int getId_cracha_tipo() {
        return id_cracha_tipo;
    }

    public void setId_cracha_tipo(int id_cracha_tipo) {
        this.id_cracha_tipo = id_cracha_tipo;
    }

    public int getCodpessoa() {
        return codpessoa;
    }

    public void setCodpessoa(int codpessoa) {
        this.codpessoa = codpessoa;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }

    public String getImglogoshop() {
        return imglogoshop;
    }

    public void setImglogoshop(String imglogoshop) {
        this.imglogoshop = imglogoshop;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
