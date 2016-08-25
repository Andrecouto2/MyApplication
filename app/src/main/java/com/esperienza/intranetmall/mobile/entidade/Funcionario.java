package com.esperienza.intranetmall.mobile.entidade;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by BONSUCESSO on 12/11/2015.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Funcionario {

    private int idfnc;

    private String nome_lojista;

    private String rg;

    private String cpf;

    private Date datanasc;

    private int status;

    private int iduser;

    //private int idusermobile;

    private int idshop;

    private String cargo_lojista;

    private Date datacadastro;

    private String codfoto;

    private Date data_demissao;

    private Date data_admissao;

    private String telefone;

    private String filiacao_pai;

    private String filiacao_mae;

    private String naturalidade;

    private String endereco;

    private int numero;

    private String complemento;

    private String bairro;

    private String cep;

    private String cidade;

    private String uf;

    private String modelo;

    private String cor;

    private String placa;

    private String marca;

    private String sexo;

    private Date validade;

    private int id_cracha_tipo;

    private String descricaoCracha;

    private int statusEnvio;

    private String imagem;

    public int getIdfnc() {
        return idfnc;
    }

    public void setIdfnc(int idfnc) {
        this.idfnc = idfnc;
    }

    public String getNome_lojista() {
        return nome_lojista;
    }

    public void setNome_lojista(String nome_lojista) {
        this.nome_lojista = nome_lojista;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getCargo_lojista() {
        return cargo_lojista;
    }

    public void setCargo_lojista(String cargo_lojista) {
        this.cargo_lojista = cargo_lojista;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public String getCodfoto() {
        return codfoto;
    }

    public void setCodfoto(String codfoto) {
        this.codfoto = codfoto;
    }

    public Date getData_demissao() {
        return data_demissao;
    }

    public void setData_demissao(Date data_demissao) {
        this.data_demissao = data_demissao;
    }

    public Date getData_admissao() {
        return data_admissao;
    }

    public void setData_admissao(Date data_admissao) {
        this.data_admissao = data_admissao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFiliacao_pai() {
        return filiacao_pai;
    }

    public void setFiliacao_pai(String filiacao_pai) {
        this.filiacao_pai = filiacao_pai;
    }

    public String getFiliacao_mae() {
        return filiacao_mae;
    }

    public void setFiliacao_mae(String filiacao_mae) {
        this.filiacao_mae = filiacao_mae;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public int getId_cracha_tipo() {
        return id_cracha_tipo;
    }

    public void setId_cracha_tipo(int id_cracha_tipo) {
        this.id_cracha_tipo = id_cracha_tipo;
    }

    public String getDescricaoCracha() {
        return descricaoCracha;
    }

    public void setDescricaoCracha(String descricaoCracha) {
        this.descricaoCracha = descricaoCracha;
    }

    public int getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(int statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
