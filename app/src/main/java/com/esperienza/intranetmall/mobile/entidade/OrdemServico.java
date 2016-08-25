package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BONSUCESSO on 12/11/2015.
 */
@Parcel(Parcel.Serialization.BEAN)
public class OrdemServico {

      private int id_os;
      private int iduser;
      private int idusermobile;
      private int idshop;
      private int idtipo;
      private Date datacad;
      private String horacad;
      private Date datainicio;
      private String horainicio;
      private Date datafim;
      private String horafim;
      private int status;
      private String nomelojista;
      private String nomesolicita;
      private String telefone;
      private String email;
      private String descricao;
      private Date inicial;
      private Date termino;
      private int iddestino;
      private String email2;
      private String idWiseit;
      private String codItemOcorrencia;
      private String anomesdia;
      private int observacoes;
      private Usuario userdestino;
      private ArrayList<ArquivoOS> arquivos;
      private ArrayList<ObservacaoOS> observacao;
      private ArrayList<PessoasAutorizadasOS> pessoas;
      private ArrayList<AprovadoresOS> aprovadores;


    public int getId_os() {
        return id_os;
    }

    public void setId_os(int id_os) {
        this.id_os = id_os;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public Date getDatacad() {
        return datacad;
    }

    public void setDatacad(Date datacad) {
        this.datacad = datacad;
    }

    public String getHoracad() {
        return horacad;
    }

    public void setHoracad(String horacad) {
        this.horacad = horacad;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public String getHorafim() {
        return horafim;
    }

    public void setHorafim(String horafim) {
        this.horafim = horafim;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Date getInicial() {
        return inicial;
    }

    public void setInicial(Date inicial) {
        this.inicial = inicial;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public int getIddestino() {
        return iddestino;
    }

    public void setIddestino(int iddestino) {
        this.iddestino = iddestino;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getIdWiseit() {
        return idWiseit;
    }

    public void setIdWiseit(String idWiseit) {
        this.idWiseit = idWiseit;
    }

    public String getCodItemOcorrencia() {
        return codItemOcorrencia;
    }

    public void setCodItemOcorrencia(String codItemOcorrencia) {
        this.codItemOcorrencia = codItemOcorrencia;
    }

    public int getIdusermobile() {
        return idusermobile;
    }

    public void setIdusermobile(int idusermobile) {
        this.idusermobile = idusermobile;
    }

    public String getAnomesdia() {
        return anomesdia;
    }

    public void setAnomesdia(String anomesdia) {
        this.anomesdia = anomesdia;
    }

    public ArrayList<ArquivoOS> getArquivos() {
        return arquivos;
    }

    public void setArquivos(ArrayList<ArquivoOS> arquivos) {
        this.arquivos = arquivos;
    }

    public ArrayList<ObservacaoOS> getObservacao() {
        return observacao;
    }

    public void setObservacao(ArrayList<ObservacaoOS> observacao) {
        this.observacao = observacao;
    }

    public ArrayList<PessoasAutorizadasOS> getPessoas() {
        return pessoas;
    }

    public void setPessoas(ArrayList<PessoasAutorizadasOS> pessoas) {
        this.pessoas = pessoas;
    }

    public Usuario getUserdestino() {
        return userdestino;
    }

    public void setUserdestino(Usuario userdestino) {
        this.userdestino = userdestino;
    }

    public ArrayList<AprovadoresOS> getAprovadores() {
        return aprovadores;
    }

    public void setAprovadores(ArrayList<AprovadoresOS> aprovadores) {
        this.aprovadores = aprovadores;
    }

    public int getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(int observacoes) {
        this.observacoes = observacoes;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
