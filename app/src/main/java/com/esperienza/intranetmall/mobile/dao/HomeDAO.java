package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Home;

/**
 * Created by ThinkPad on 29/12/2015.
 */
public class HomeDAO extends DAO{

    private final String DQL_GET_HOMEUSERSHOP= "SELECT * FROM TBHOME WHERE iduser = ? and idshop = ?";
    private final String DQL_GET_HOME="SELECT * FROM TBHOME WHERE idhome = ?";

    public static HomeDAO getNewInstance(){ return new HomeDAO();}
    public Home getHome(Integer iduser,Integer idshop){
        Home h = null;
        Cursor c = getDb().rawQuery(DQL_GET_HOMEUSERSHOP, new String[]{iduser.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                h = new Home();
                h.setIdhome(c.getInt(c.getColumnIndex("idhome")));
                h.setIduser(c.getInt(c.getColumnIndex("iduser")));
                h.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                h.setAguardandoaprovacao(c.getInt(c.getColumnIndex("aguardandoaprovacao")));
                h.setAutorizacao(c.getInt(c.getColumnIndex("autorizacao")));
                h.setNaoautorizado(c.getInt(c.getColumnIndex("naoautorizado")));
                h.setEmexecucao(c.getInt(c.getColumnIndex("emexecucao")));
                h.setCircularnaolida(c.getInt(c.getColumnIndex("circularnaolida")));
                h.setQtdfuncionario(c.getInt(c.getColumnIndex("qtdfnc")));
                //h.setImagembanner(c.getString(c.getColumnIndex("imagembanner")));


            }
        }finally {
            c.close();
        }

        return h;
    }
    public Home getHome(Integer idhome){
        Home h = null;
        Cursor c = getDb().rawQuery(DQL_GET_HOME, new String[]{idhome.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                h = new Home();
                h.setIdhome(c.getInt(c.getColumnIndex("idhome")));
                h.setIduser(c.getInt(c.getColumnIndex("iduser")));
                h.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                h.setAguardandoaprovacao(c.getInt(c.getColumnIndex("aguardandoaprovacao")));
                h.setAutorizacao(c.getInt(c.getColumnIndex("autorizacao")));
                h.setNaoautorizado(c.getInt(c.getColumnIndex("naoautorizado")));
                h.setEmexecucao(c.getInt(c.getColumnIndex("emexecucao")));
                h.setCircularnaolida(c.getInt(c.getColumnIndex("circularnaolida")));
                h.setQtdfuncionario(c.getInt(c.getColumnIndex("qtdfnc")));
               // h.setImagembanner(c.getString(c.getColumnIndex("imagembanner")));


            }
        }finally {
            c.close();
        }

        return h;
    }
    public void save(Home home) {
        Home old = getHome(home.getIduser(),home.getIdshop());
        ContentValues vl = new ContentValues();
        if(old!=null)
        {
            if(old.getIdhome()>0)
            {
                vl.put("idhome", old.getIdhome());
            }
        }
        vl.put("idshop", home.getIdshop());
        vl.put("iduser", home.getIduser());
        vl.put("aguardandoaprovacao ", home.getAguardandoaprovacao());
        vl.put("autorizacao",home.getAutorizacao());
        vl.put("naoautorizado",home.getNaoautorizado());
        vl.put("emexecucao",home.getEmexecucao());
        vl.put("circularnaolida",home.getCircularnaolida());
        vl.put("qtdfnc",home.getQtdfuncionario());
        //vl.put("imagembanner",home.getImagembanner());

        if(old == null){
            //vl.put("idhome", home.getIdhome());
            getDb().insert("TBHOME", "", vl);
        } else {
            getDb().update("TBHOME", vl, "idhome = ?", new String[]{String.valueOf(old.getIdhome())});
        }

    }
    public void removeAll(){
        getDb().delete("TBHOME", "", null);
    }
}
