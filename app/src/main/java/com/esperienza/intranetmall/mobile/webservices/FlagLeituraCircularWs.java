package com.esperienza.intranetmall.mobile.webservices;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ThinkPad on 18/12/2015.
 */
public class FlagLeituraCircularWs extends WebServicesXML{


    @Override
    public void onCreate() {
        setMethodName("ControleCircular");
        addProperty("idUsuario");
        addProperty("idCircular");
        addProperty("idShopping");
    }
    public void setIdShop(String idShop) {
        setProperty("idShopping", idShop);
    }

    public void setIdUser(String idUser) {
        setProperty("idUsuario", idUser);
    }

    public void setCircular(String idcircular){
        setProperty("idCircular",idcircular);
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
