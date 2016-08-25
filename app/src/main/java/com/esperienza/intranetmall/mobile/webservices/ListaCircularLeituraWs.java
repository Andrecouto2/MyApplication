package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.EntidadeCircular;
import com.esperienza.intranetmall.mobile.entidade.LeitoresCircular;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 17/12/2015.
 */
public class ListaCircularLeituraWs extends WebServicesXML {


    @Override
    public void onCreate() {
        setMethodName("ListaCircularLeitura");
        addProperty("idShopping");
        addProperty("idUsuario");

    }

    public void setIdShop(String idShop) {
        setProperty("idShopping", idShop);
    }

    public void setIdUser(String idUser) {
        setProperty("idUsuario", idUser);
    }

    public void setLoad(int load) {
        setProperty("load", load);
    }

    public String getIdShop(){return getProperty("idShopping");}

    public String getIdUser(){return getProperty("idUsuario");}

    public int getLoad(){try{return Integer.parseInt(getProperty("load"));}catch (Exception e){e.printStackTrace();return -1;  }}

    @Override
    public EntidadeCircular getResult() {
        SoapObject result = null;
        EntidadeCircular ec = new EntidadeCircular();
        List<Circular> l = new ArrayList<>();
        List<LeitoresCircular> lc = new ArrayList<>();

            try {
                result = getRetorno();
                SoapObject aux = (SoapObject) result.getProperty("circulares");
                Circular c;
                int tam = aux.getPropertyCount();
                for (int i = 0; i < tam; i++) {
                    c = new Circular();
                    SoapObject raiz = (SoapObject) aux.getProperty(i);
                    c.setIdcircular(!raiz.getProperty("idcircular").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idcircular").toString()) : null);
                    c.setTitulo(!raiz.getProperty("titulo").toString().equals("anyType{}") ? raiz.getProperty("titulo").toString() : null);
                    c.setData_cadastro(!raiz.getProperty("data_cad").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("data_cad").toString()) : null);
                    c.setAcesso(!raiz.getProperty("acessos").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("acessos").toString()) : null);
                    c.setNomearquivo(!raiz.getProperty("nomearquivo").toString().equals("anyType{}") ? raiz.getProperty("nomearquivo").toString() : null);
                    c.setIduser(!raiz.getProperty("idUser").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idUser").toString()) : null);
                    c.setFlagleitura(!raiz.getProperty("leitura").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("leitura").toString()) : null);
                    c.setIdusermobile(AppHelper.getUsuario().getIduser());
                    c.setIdshop(AppHelper.getIdShop());
                    if (!raiz.getProperty("data_cad").toString().equals("anyType{}") && raiz.getProperty("data_cad") != null) {
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(DateHelper.toDate(raiz.getProperty("data_cad").toString()));
                            String mes = String.valueOf(calendar.get(Calendar.MONTH)); //Day of the month :)
                            String ano = String.valueOf(calendar.get(Calendar.YEAR)); //number of seconds
                            if (mes.length() != 2) {
                                mes = "0" + mes;
                            }

                            c.setAnomes(ano + mes);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    //ATENÇÃO EXISTE O RETORNO DESCRIÇÃO DA CIRCULAR NO WEBSERVICE
                    l.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ec.setCirculares(null);
            }
        ec.setCirculares(l);
        try
        {
            LeitoresCircular leitor;
            SoapObject aux = (SoapObject) result.getProperty("leitores");
            int tam = aux.getPropertyCount();
            for (int i = 0; i < tam; i++)
            {
                leitor = new LeitoresCircular();
                SoapObject raiz = (SoapObject) aux.getProperty(i);
                leitor.setIdleitorcircular(!raiz.getProperty("idControle").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idControle").toString()) : null);
                leitor.setIduser(!raiz.getProperty("iduser").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("iduser").toString()) : null);
                leitor.setIdcircular(!raiz.getProperty("idcircular").toString().equals("anyType{}") ? Integer.valueOf(raiz.getProperty("idcircular").toString()) : null);
                leitor.setNome(!raiz.getProperty("nome").toString().equals("anyType{}") ? raiz.getProperty("nome").toString() : null);
                leitor.setEmpresa(!raiz.getProperty("empresa").toString().equals("anyType{}") ? raiz.getProperty("empresa").toString() : null);
                leitor.setData_acessou(!raiz.getProperty("data_acessou").toString().equals("anyType{}") ? DateHelper.toDate(raiz.getProperty("data_acessou").toString()) : null);
                leitor.setIdshop(AppHelper.getIdShop());
                leitor.setIdusermobile(AppHelper.getUsuario().getIduser());
                //CAMPO USER PAI NAO DECLARADO AQUI
                lc.add(leitor);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ec.setLeitoresCircular(null);
        }
        ec.setLeitoresCircular(lc);



        return ec;
    }
}