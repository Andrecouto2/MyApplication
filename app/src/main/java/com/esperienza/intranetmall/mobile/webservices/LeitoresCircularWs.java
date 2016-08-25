package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.LeitoresCircular;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 21/12/2015.
 */
public class LeitoresCircularWs extends WebServicesXML {


    @Override
    public void onCreate() {
        setMethodName("LeituraCircular");
        addProperty("idShopping");//string
        addProperty("idcircular");//int

    }
    public void setIdShop(String idShop) {setProperty("idShopping", idShop);}
    public void setIdCircular(int idcircular) {
        setProperty("idcircular", idcircular);
    }

    @Override
    public List<LeitoresCircular> getResult() {
        SoapObject result = null;
        List<LeitoresCircular> l = new ArrayList<>();
        try {
            result = getRetorno();
            LeitoresCircular leitor;
            int tam = result.getPropertyCount();
            for (int i = 0; i < tam; i++)
            {
                leitor = new LeitoresCircular();
                SoapObject raiz = (SoapObject) result.getProperty(i);
                leitor.setIdleitorcircular(!raiz.getProperty("idControle").equals("null") ? Integer.valueOf(raiz.getProperty("idControle").toString()) : null);
                leitor.setIduser(!raiz.getProperty("iduser").equals("null") ? Integer.valueOf(raiz.getProperty("iduser").toString()) : null);
                leitor.setIdcircular(!raiz.getProperty("idcircular").equals("null") ? Integer.valueOf(raiz.getProperty("idcircular").toString()) : null);
                leitor.setNome(!raiz.getProperty("nome").equals("null") ? raiz.getProperty("nome").toString() : null);
                leitor.setEmpresa(!raiz.getProperty("empresa").equals("null") ? raiz.getProperty("empresa").toString() : null);
                leitor.setData_acessou(!raiz.getProperty("data_acessou").equals("null") ? DateHelper.toDate(raiz.getProperty("data_acessou").toString()) : null);

                //CAMPO USER PAI NAO DECLARADO AQUI
                l.add(leitor);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return l;
    }
}
