package com.esperienza.intranetmall.mobile.webservices;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ThinkPad on 14/12/2015.
 */
public class EnvioFuncionarioWs extends WebServicesXML{

    public EnvioFuncionarioWs(){}
    @Override
    public void onCreate() {
        setMethodName("GravaFuncionario");
        addProperty("idShopping");
        addProperty("iduser");
        addProperty("idCad");
        addProperty("status_cad");
        addProperty("data_adm");
        addProperty("nome_lojista");
        addProperty("data_dem");
        addProperty("cargo_lojista");
        addProperty("telefone");
        addProperty("rg");
        addProperty("cpf");
        addProperty("filiacao_pai");
        addProperty("datanasc");
        addProperty("filiacao_mae");
        addProperty("naturalidade");
        addProperty("endereco");
        addProperty("numero");
        addProperty("compl");
        addProperty("bairro");
        addProperty("cep");
        addProperty("cidade");
        addProperty("uf");
        addProperty("sexo");
        addProperty("validade");
        addProperty("modelo");
        addProperty("marca");
        addProperty("cor");
        addProperty("placa");
        addProperty("imagem");

    }
    public void setIdShopping(String idshopping){
        setProperty("idShopping", idshopping);
    }
    public void setIdUser(String iduser){
        setProperty("iduser", iduser);
    }
    public void setIdCad(String idcad){
        setProperty("idCad", idcad);
    }
    public void setStatus_cad(String status_cad){
        setProperty("status_cad", status_cad);
    }
    public void setDataAdm(String data_adm){
        setProperty("data_adm", data_adm);
    }
    public void setNomeLojista(String nome_lojista){
        setProperty("nome_lojista", nome_lojista);
    }
    public void setDataDem(String data_dem){
        setProperty("data_dem",data_dem);
    }
    public void setCargoLojista(String cargo_lojista){
        setProperty("cargo_lojista",cargo_lojista);
    }
    public void setTelefone(String telefone){
        setProperty("telefone",telefone);
    }
    public void setRg(String rg){
        setProperty("rg",rg);
    }
    public void setCpf(String cpf){
        setProperty("cpf",cpf);
    }
    public void setFiliacaoPai(String filiacao_pai)
    {
        setProperty("filiacao_pai",filiacao_pai);
    }
    public void setDatanasc(String datanasc)
    {
        setProperty("datanasc",datanasc);
    }
    public void setFiliacao_mae(String filiacao_mae)
    {
        setProperty("filiacao_mae",filiacao_mae);
    }
    public void setNaturalidade(String naturalidade)
    {
        setProperty("naturalidade",naturalidade);
    }
    public void setEndereco(String endereco)
    {
        setProperty("endereco",endereco);
    }
    public void setNumero(String numero)
    {
        setProperty("numero",numero);
    }
    public void setComplemento(String compl)
    {
        setProperty("compl",compl);
    }
    public void setBairro(String bairro)
    {
        setProperty("bairro",bairro);
    }
    public void setCep(String cep)
    {
        setProperty("cep",cep);
    }

    public void setCidade(String cidade)
    {
        setProperty("cidade",cidade);
    }
    public void setUf(String uf)
    {
        setProperty("uf",uf);
    }
    public void setSexo(String sexo)
    {
        setProperty("sexo",sexo);
    }
    public void setValidade(String validade)
    {
        setProperty("validade",validade);
    }
    public void setModelo(String modelo)
    {
        setProperty("modelo",modelo);
    }
    public void setMarca(String marca)
    {
        setProperty("marca",marca);
    }
    public void setCor(String cor)
    {
        setProperty("cor",cor);
    }
    public void setPlaca(String placa)
    {
        setProperty("placa",placa);
    }
    public void setImagem(String imagem)
    {
        setProperty("imagem",imagem);
    }


    @Override
    public String getResult() {
        String result = null;
        try {
            SoapObject resultSO = getRetorno();
            SoapObject resultraiz = (SoapObject) resultSO.getProperty(0);
            return resultraiz.getProperty(0).toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
