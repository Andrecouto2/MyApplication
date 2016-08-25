package com.esperienza.intranetmall.mobile.webservices;

import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.*;
import static java.lang.String.*;

/**
 * Created by ThinkPad on 02/12/2015.
 */
public class ListaFuncionariosWs extends WebServicesXML{

    @Override
    public void onCreate() {
        setMethodName("DetalheFuncionario");
        addProperty("idShopping");
        addProperty("idUser");

    }
    public void setIdShop(String idShop){
        setProperty("idShopping", idShop);
    }
    public void setIdUser(String idUser){
        setProperty("idUser",idUser);
    }

    @Override
    public List<Funcionario> getResult() {

        SoapObject result = null;
        List<Funcionario> l = new ArrayList<>();
        try {
            result =  getRetorno();//AKII
            Funcionario f;
            int tam=result.getPropertyCount();
            if(tam==0)
            {
                return null;
            }
            for (int i=0;i<tam;i++)
            {
                f= new Funcionario();
                SoapObject raiz = (SoapObject) result.getProperty(i);
                f.setIdfnc(!raiz.getProperty("idcad").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idcad").toString()) : null);
                f.setNome_lojista(!raiz.getProperty("nome_lojista").toString().equals("anyType{}") ? raiz.getProperty("nome_lojista").toString() : null);
                f.setRg(!raiz.getProperty("rg").toString().equals("anyType{}") ? raiz.getProperty("rg").toString() : null);
                f.setCpf(!raiz.getProperty("cpf").toString().equals("anyType{}") ? raiz.getProperty("cpf").toString() : null);
                f.setDatanasc(!raiz.getProperty("dataNasc").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("dataNasc").toString()) : null);
                f.setStatus(!raiz.getProperty("status_cad").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("status_cad").toString()) : null);
                f.setIduser(!raiz.getProperty("idUser").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idUser").toString()) : null);
                f.setCargo_lojista(!raiz.getProperty("cargo_lojista").toString().equals("anyType{}") ? raiz.getProperty("cargo_lojista").toString() : null);
                f.setDatacadastro(!raiz.getProperty("datacad").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("datacad").toString()) : null);
                f.setCodfoto(!raiz.getProperty("codfoto").toString().equals("anyType{}") ? raiz.getProperty("codfoto").toString() : null);
                f.setData_demissao(!raiz.getProperty("dataDemissao").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("dataDemissao").toString()) : null);
                f.setData_admissao(!raiz.getProperty("dataAdm").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("dataAdm").toString()) : null);
                f.setTelefone(!raiz.getProperty("telefone").toString().equals("anyType{}") ? raiz.getProperty("telefone").toString() : null);
                f.setFiliacao_pai(!raiz.getProperty("filiacao_pai").toString().equals("anyType{}") ? raiz.getProperty("filiacao_pai").toString() : null);
                f.setFiliacao_mae(!raiz.getProperty("filiacao_mae").toString().equals("anyType{}") ? raiz.getProperty("filiacao_mae").toString() : null);
                f.setNaturalidade(!raiz.getProperty("naturalidade").toString().equals("anyType{}") ? raiz.getProperty("naturalidade").toString() : null);
                f.setEndereco(!raiz.getProperty("endereco").toString().equals("anyType{}") ? raiz.getProperty("endereco").toString() : null);
                if(!raiz.getProperty("numero").toString().equals("anyType{}"))
                {
                    try
                    {
                        f.setNumero(Integer.valueOf(raiz.getProperty("numero").toString()));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        f.setNumero(0);
                    }

                }
                f.setComplemento(!raiz.getProperty("compl").toString().equals("anyType{}") ? raiz.getProperty("compl").toString() : null);
                f.setBairro(!raiz.getProperty("bairro").toString().equals("anyType{}") ? raiz.getProperty("bairro").toString() : null);
                f.setCep(!raiz.getProperty("cep").toString().equals("anyType{}") ? raiz.getProperty("cep").toString() : null);
                f.setCidade(!raiz.getProperty("cidade").toString().equals("anyType{}") ? raiz.getProperty("cidade").toString() : null);
                f.setUf(!raiz.getProperty("uf").toString().equals("anyType{}") ? raiz.getProperty("uf").toString() : null);
                f.setSexo(!raiz.getProperty("sexo").toString().equals("anyType{}") ? raiz.getProperty("sexo").toString() : null);
                f.setValidade(!raiz.getProperty("validade").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("validade").toString()) : null);
                f.setId_cracha_tipo(!raiz.getProperty("status_cad").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("status_cad").toString()) : null);
                f.setDescricaoCracha(!raiz.getProperty("descricaoCracha").toString().equals("anyType{}") ? raiz.getProperty("descricaoCracha").toString() : null);
                f.setModelo(!raiz.getProperty("modelo").toString().equals("anyType{}") ? raiz.getProperty("modelo").toString() : null);
                f.setCor(!raiz.getProperty("cor").toString().equals("anyType{}") ? raiz.getProperty("cor").toString() : null);
                f.setPlaca(!raiz.getProperty("placa").toString().equals("anyType{}") ? raiz.getProperty("placa").toString() : null);
                f.setMarca(!raiz.getProperty("marca").toString().equals("anyType{}") ? raiz.getProperty("marca").toString() : null);
                f.setImagem(!raiz.getProperty("imagem").toString().equals("anyType{}") ? raiz.getProperty("imagem").toString() : null);
                f.setStatusEnvio(3);
                f.setIdshop(AppHelper.getIdShop());

                l.add(f);
            }





        }catch (Exception e) {
            e.printStackTrace();
            return null;


        }
        return l;

    }


}
