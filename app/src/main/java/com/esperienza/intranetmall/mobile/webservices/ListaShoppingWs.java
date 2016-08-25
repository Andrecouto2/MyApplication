package com.esperienza.intranetmall.mobile.webservices;

/**
 * Created by BONSUCESSO on 17/11/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.esperienza.intranetmall.mobile.entidade.Cliente;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParser;


public class ListaShoppingWs extends WebServicesXML {

    @Override
    public void onCreate() {
        setMethodName("ListaShoppings");

    }

    @Override
    public List<Cliente> getResult() {

        SoapObject result = null;
        List<Cliente> l = new ArrayList<Cliente>();
        try {
            result =  getRetorno();
            Cliente c=null;
            int tam=result.getPropertyCount();
            for (int i=0;i<tam;i++)
            {
                c= new Cliente();
                SoapObject raiz = (SoapObject) result.getProperty(i);
                c.setIdcliente(Integer.valueOf(raiz.getProperty("Id").toString()));
                c.setNome(raiz.getProperty("Nome").toString());
                c.setImg_cliente(raiz.getPrimitiveProperty("Logo").toString());
                c.setAtivo_cracha(Integer.parseInt(raiz.getPrimitiveProperty("Cracha").toString()));
                // c.setNumcontrato(result.getAttributeValue(null, "contrato"));
                //c.setPagas(Integer.parseInt(result.getAttributeValue(null, "pagas")));
                //c.setParcela(Double.valueOf(result.getAttributeValue(null, "vlrparcela")));

                l.add(c);
            }





        }catch (Exception e) {
            //Log.e("Webservice.BuscarContratos","Erro ao carregar a lista de contratos do WS.",e);
        }
        return l;

    }
}
