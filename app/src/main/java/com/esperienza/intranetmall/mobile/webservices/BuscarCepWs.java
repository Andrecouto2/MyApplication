package com.esperienza.intranetmall.mobile.webservices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.esperienza.intranetmall.mobile.entidade.Endereco;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;




public class BuscarCepWs extends WebServicesCepXML {

    @Override
    public void onCreate() {
        setMethodName("Consultar");
        addProperty("cep");

    }



    public void setCep(String cep){
        setProperty("cep", cep);
    }


    public Endereco getResult() {
           SoapObject result;
           Endereco end= new Endereco();
        try {
            result = getRetornoCEP();

            if(result!=null){
               String tipologradouro=result.getPrimitiveProperty("tipoLogradouro").toString();
               String logradouro=result.getPrimitiveProperty("logradouro").toString();
               String bairro=result.getPrimitiveProperty("bairro").toString();
               String cid= result.getPrimitiveProperty("cidade").toString();
               String uf=result.getPrimitiveProperty("uf").toString();
               end.setLogradouro(tipologradouro+" "+logradouro);
               end.setBairro(bairro);
               end.setMunicipio(cid);
               end.setUf(uf);


               return end;




            }

        } catch (Exception e) {
            Log.e("Webservice.CEP",
                    "Erro ao carregar a lista de campanhas do WS. \n" + e.getMessage());
        }
        return null;
    }

}

