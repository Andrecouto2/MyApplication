package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.Dispositivo;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ThinkPad on 28/12/2015.
 */
public class CadastraDispositivoWs extends WebServicesXML {

    public CadastraDispositivoWs(){};
    @Override
    public void onCreate() {
        setMethodName("Cadastra_Dispositivo");
        addProperty("login");
        addProperty("senha");
        addProperty("idShopp");
        addProperty("registrationDevice");
    }

    public void setIdShopping(String idshopping){
        setProperty("idShopp", idshopping);
    }
    public void setIdDev(String iddev){
        setProperty("registrationDevice", iddev);
    }
    public String getIdShopping(){return getProperty("idShopp");}
    public String getIdDevice(){return getProperty("registrationDevice");}
    public void setUsuario(String usuario){
        setProperty("login", usuario);
    }
    public String getUsuario(){
        return getProperty("login");
    }
    public void setSenha(String senha){
        setProperty("senha", senha);
    }
    public String getSenha(){
        return  getProperty("senha");
    }

    @Override
    public Dispositivo getResult() {
        Dispositivo result = null;
        try {
            SoapObject resultDev = getRetorno();

            result = new Dispositivo();
            if(!resultDev.getProperty("idDevice").toString().equals("0"))
            {
                result.setIddispositivo(Integer.parseInt(resultDev.getProperty("idDevice").toString()));
                result.setIdshop(Integer.parseInt(resultDev.getProperty("idShopp").toString()));
                result.setIduser(Integer.parseInt(resultDev.getProperty("idUser").toString()));
                result.setIddeviceregistration(resultDev.getProperty("registrationDevice").toString());
            }
            else
            {
                result=null;
            }


            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
