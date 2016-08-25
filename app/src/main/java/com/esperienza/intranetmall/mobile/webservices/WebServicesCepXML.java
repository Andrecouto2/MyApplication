package com.esperienza.intranetmall.mobile.webservices;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;





import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;




public abstract class WebServicesCepXML {
    private String METHOD_WS = "";
    private String ACTION_WS = "";
    private SoapObject request;
    private List<String> properties;
    private final Integer timeout = 190000;



    abstract public void onCreate();


    public WebServicesCepXML() {
        properties = new LinkedList<String>();
        onCreate();
        request = new SoapObject(WebServiceURL.NAMESPACE_CEP, METHOD_WS);
        if(properties.size() > 0){
            for(String n: properties){
                request.addProperty(n, "");
            }
        }
        ACTION_WS = WebServiceURL.NAMESPACE_CEP + METHOD_WS;
        Log.d(WebServiceURL.TAG_CEP,"ACTION_WS "+ ACTION_WS);
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
        return WebServiceURL.NAMESPACE_CEP;
    }
    public String getURL_WS() {
        return WebServiceURL.URL_WS_CEP;
    }
    public String getACTION_WS() {
        return ACTION_WS;
    }
}
