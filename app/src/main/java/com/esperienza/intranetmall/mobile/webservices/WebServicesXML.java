package com.esperienza.intranetmall.mobile.webservices;

/**
 * Created by BONSUCESSO on 16/11/2015.
 */
import android.net.LocalSocketAddress;
import android.util.Log;

import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.EntidadeBuscaAvancadaOs;
import com.esperienza.intranetmall.mobile.entidade.Ordem;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.KeepAliveHttpsTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.ksoap2.serialization.PropertyInfo;




import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public abstract class WebServicesXML {

    private String METHOD_WS = "";
    private String ACTION_WS = "";
    private SoapObject request;
    private List<String> properties;
    private final Integer timeout = 60000;



    abstract public void onCreate();

    public WebServicesXML() {
        properties = new LinkedList<String>();
        onCreate();
        request = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP, METHOD_WS);
        if(properties.size() > 0){
            for(String n: properties){
                request.addProperty(n, "");
            }
        }
        ACTION_WS = WebServiceURL.NAMESPACE_LISTASHOP + METHOD_WS;
        //Log.d(WebServiceURL.TAG_LISTASHOP,"ACTION_WS "+ ACTION_WS);
    }
    public WebServicesXML(int i) {
        properties = new LinkedList<String>();
        onCreate();
        request = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP, METHOD_WS);
        if(properties.size() > 0){
            for(String n: properties){
                request.addProperty(n, "");
            }
        }
        ACTION_WS = WebServiceURL.NAMESPACE_LISTASHOP + METHOD_WS;
        //Log.d(WebServiceURL.TAG_LISTASHOP,"ACTION_WS "+ ACTION_WS);
    }
    protected SoapObject getRetorno() throws IOException{
        //HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;


        XmlPullParser xml = null;
        SoapObject x = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        //Log.d(WebServiceURL.TAG_LISTASHOP,"Url "+ ACTION_WS);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServiceURL.URL_WS_LISTASHOP,timeout);
        try {

            Log.d("WebServices","Url "+  WebServiceURL.URL_WS_LISTASHOP);
            Log.d("WebServices","Action "+ ACTION_WS);
            Log.d("WebServices","envelope "+ envelope.toString());

            androidHttpTransport.debug = true;
            androidHttpTransport.call(ACTION_WS, envelope);
            //String com o retorno
            //Log.e("teste","Retorno: "+envelope.getResponse());
            //SoapPrimitive spr = (SoapPrimitive) envelope.getResponse();
            SoapObject sp = (SoapObject) envelope.getResponse();
            //   XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            //   xmlFactory.setNamespaceAware(true);
            //   xml = xmlFactory.newPullParser();
            //androidHttpTransport.getServiceConnection().disconnect();
            return sp;

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("",e.getMessage());
           // Log.e(WebServiceURL.TAG_LISTASHOP,"Erro no web service!",e);
        }finally {
            //androidHttpTransport.getServiceConnection().disconnect();
        }
        // xml.setInput(x);
        return null;
    }
    protected SoapObject getRetornoCEP() throws XmlPullParserException{
        XmlPullParser xml = null;
        SoapObject x = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        Log.d(WebServiceURL.TAG_CEP,"Url "+ ACTION_WS);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServiceURL.URL_WS_CEP, timeout);
        try {
            Log.d("WebServices","Url "+WebServiceURL.URL_WS_CEP);
            Log.d("WebServices","Action "+ ACTION_WS);
            Log.d("WebServices","envelope "+ envelope.toString());

            androidHttpTransport.debug = true;
            androidHttpTransport.call(ACTION_WS, envelope);
            //String com o retorno
            Log.e("teste","Retorno: "+envelope.getResponse());

            SoapObject sp = (SoapObject) envelope.getResponse();
            //   XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            //   xmlFactory.setNamespaceAware(true);
            //   xml = xmlFactory.newPullParser();

            return sp;
        }  catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(WebServiceURL.TAG_CEP,"Url '"+ WebServiceURL.URL_WS_CEP+"' nao foi encontrado ou nao consegue conexao!",e);

        } catch (Exception e) {
            Log.e(WebServiceURL.TAG_CEP,"Erro no web service!",e);
        }
        // xml.setInput(x);
        return null;
    }
    public void addcomplexobject(Ordem ordem)
    {
        SoapObject so_ordem = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"Ordem");
        so_ordem.addProperty("idShopping",ordem.getIdShopping());
        so_ordem.addProperty("iduser",ordem.getIduser());
        so_ordem.addProperty("idtipo",ordem.getIdtipo());
        so_ordem.addProperty("datainicio",ordem.getDatainicio());
        so_ordem.addProperty("datafim",ordem.getDatafim());
        so_ordem.addProperty("horainicio",ordem.getHorainicio());
        so_ordem.addProperty("horafim",ordem.getHorafim());
        so_ordem.addProperty("nomelojista",ordem.getNomelojista());
        so_ordem.addProperty("nomesolicita",ordem.getNomesolicita());
        so_ordem.addProperty("telefone",ordem.getTelefone());
        so_ordem.addProperty("email", ordem.getEmail());
        so_ordem.addProperty("descricao",ordem.getDescricao());
        so_ordem.addProperty("iddestino", ordem.getIddestino());
        so_ordem.addProperty("descricaotipo",ordem.getDescricaotipo());
        //PESSOAS
        SoapObject so_pessoas = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"Pessoas_");
        SoapObject so_pessoa;
        for (int i=0;i<ordem.getPessoas_().length;i++)
        {
            so_pessoa = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"PAutorizadas");
            so_pessoa.addProperty("nome",ordem.getPessoas_()[i].getNome());
            so_pessoa.addProperty("rg",ordem.getPessoas_()[i].getRg());
            so_pessoa.addProperty("empresa",ordem.getPessoas_()[i].getEmpresa());
            so_pessoas.addSoapObject(so_pessoa);
        }
        so_ordem.addSoapObject(so_pessoas);
        //ARQUIVOS
        SoapObject so_arquivos = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"Arquivos_");
        SoapObject so_arquivo;
        for(int i=0;i<ordem.getArquivos_().length;i++)
        {
            so_arquivo =  new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"Arquivos");
            so_arquivo.addProperty("extensao",ordem.getArquivos_()[i].getExtensao());
            so_arquivo.addProperty("file",ordem.getArquivos_()[i].getFile());
            so_arquivos.addSoapObject(so_arquivo);
        }
        so_ordem.addSoapObject(so_arquivos);

        //request
        request.addSoapObject(so_ordem);

    }
    public void addcomplexobjectPA(EntidadeBuscaAvancadaOs baos)
    {
          //lista status
        SoapObject so_l_status = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"listastatusservico");
        SoapObject so_status;
        for (int i=0;i<baos.getListasstatus().size();i++)
        {
            so_status = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"idservico");
            so_status.addProperty("int",baos.getListasstatus().get(i));

            so_l_status.addSoapObject(so_status);
        }
        //lista servicos
        SoapObject so_l_servico = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"listatiposervico");
        SoapObject so_servico;
        for (int i=0;i<baos.getListatiposervico().size();i++)
        {
            so_servico = new SoapObject(WebServiceURL.NAMESPACE_LISTASHOP,"idtiposervico");
            so_servico.addProperty("int",baos.getListatiposervico().get(i));

            so_l_servico.addSoapObject(so_servico);
        }
        if(baos.getListasstatus()!=null)
        request.addSoapObject(so_l_status);
        if(baos.getListatiposervico()!=null)
        request.addSoapObject(so_l_servico);
    }

    /* protected SoapObject getRetornoCEP() throws XmlPullParserException{
        XmlPullParser xml = null;
        SoapObject x = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        Log.d(WebServicesURL.TAG,"Url "+ ACTION_WS);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(WebServicesURL.URL_WS_CEP, timeout);
        try {
            Log.d("WebServices","Url "+WebServicesURL.URL_WS_CEP);
            Log.d("WebServices","Action "+ ACTION_WS);
            Log.d("WebServices","envelope "+ envelope.toString());

            androidHttpTransport.debug = true;
            androidHttpTransport.call(ACTION_WS, envelope);
            //String com o retorno
            Log.e("teste","Retorno: "+envelope.getResponse());

            SoapObject sp = (SoapObject) envelope.getResponse();
            //   XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            //   xmlFactory.setNamespaceAware(true);
            //   xml = xmlFactory.newPullParser();

            return sp;
        }  catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(WebServicesURL.TAG_CEP,"Url '"+ WebServicesURL.URL_WS_CEP+"' nao foi encontrado ou nao consegue conexao!",e);

        } catch (Exception e) {
            Log.e(WebServicesURL.TAG_CEP,"Erro no web service!",e);
        }
        // xml.setInput(x);
        return null;
    }*/


    abstract public Object getResult();

    protected void setMethodName(String method){
        METHOD_WS = method;
    }
    protected String getMethodName(){
        return METHOD_WS;
    }
    protected String getProperty(String name){
        return request.getPropertyAsString(name);
    }
    protected void setProperty(String name, Object value){
        request.setProperty(properties.indexOf(name), value);
    }
    protected void addProperty(String name){
        properties.add(name);
    }
    public String getNAMESPACE() {
        return WebServiceURL.NAMESPACE_LISTASHOP;
    }
    public String getURL_WS() {
        return WebServiceURL.URL_WS_LISTASHOP;
    }
    public String getACTION_WS() {
        return ACTION_WS;
    }
}
