package com.esperienza.intranetmall.mobile.webservices;

import android.util.Xml;

import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.DateHelper;
//import com.itextpdf.tool.xml.html.head.XML;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ThinkPad on 26/01/2016.
 */
public class CadastraObsOsWs extends WebServicesXML {

    @Override
    public void onCreate() {
        setMethodName("GravaObsOs");
        addProperty("idUsuario");
        addProperty("idShopping");
        addProperty("idOs");
        addProperty("obs");
        addProperty("aprovacao");

    }
    public void setIdShopping(String idshopping){
        setProperty("idShopping", idshopping);
    }
    public void setIdUser(String iduser){
        setProperty("idUsuario", iduser);
    }
    public void setIdOs(String idos){setProperty("idOs",idos);}
    public void setObs(String obs){setProperty("obs",obs);}
    public void setAprovacao(String apr){setProperty("aprovacao",apr);}

    @Override
    public ObservacaoOS getResult() {
        ObservacaoOS result = new ObservacaoOS();
        result.setUsuario(new Usuario());
        try {
            SoapObject resultSO = getRetorno();
            //SoapObject resultraiz = (SoapObject) resultSO.getProperty(0);
            result.setIdcomentario(Integer.parseInt(resultSO.getProperty("idcomentario").toString()));
            result.setIduser(Integer.parseInt(resultSO.getProperty("iduser").toString()));
            result.setObservacoes(resultSO.getProperty("observacoes").toString());
            result.setDatacad(DateHelper.toDate(resultSO.getProperty("datacad").toString()));
            result.setHoracad(resultSO.getProperty("horacad").toString());
            result.setIdos(Integer.parseInt(resultSO.getProperty("idos").toString()));
            //resultSO = (SoapObject) resultSO.getProperty("usuario");
            //result.getUsuario().setIduser(Integer.parseInt(resultSO.getProperty("idUser").toString()));
            //telefone porém status OS
            result.getUsuario().setTelefone1(resultSO.getProperty("aprovacao").toString());

        }catch (Exception e)
        {
            e.printStackTrace();
            result=null;
        }
        return result;
    }
}
