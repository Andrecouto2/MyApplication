package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.Home;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ThinkPad on 29/12/2015.
 */
public class BuscaDadosHomeWs extends WebServicesXML {

    @Override
    public void onCreate() {

        setMethodName("Retorna_Home");
        addProperty("idShopp");
        addProperty("login");
        addProperty("senha");

    }
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
    public void setIdShopping(String idShopping){
        setProperty("idShopp", idShopping);
    }
    public String getIdShopping(){
        return getProperty("idShopp");
    }

    @Override
    public Home getResult() {
        Home home=null;
        SoapObject result;
        try
        {
               result=getRetorno();

               home = new Home();
               home.setIdshop(Integer.parseInt(getIdShopping()));
               home.setIduser(Integer.parseInt(result.getProperty("iduser").toString()));
               //home.setImagembanner(result.getProperty("imagembanner").toString());
               home.setCircularnaolida(Integer.parseInt(result.getProperty("circularnaolida").toString()));
               home.setAutorizacao(Integer.parseInt(result.getProperty("autorizacao").toString()));
               home.setEmexecucao(Integer.parseInt(result.getProperty("emexecucao").toString()));
               home.setNaoautorizado(Integer.parseInt(result.getProperty("naoautorizado").toString()));
               home.setAguardandoaprovacao(Integer.parseInt(result.getProperty("aguardandoaprovacao").toString()));


        }
        catch (Exception e)
        {
            e.printStackTrace();
            home=null;
        }

        return home;
    }
}
