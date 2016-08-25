package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BONSUCESSO on 13/11/2015.
 */
public class CircularDAO extends DAO {

    final String DQL_SELECT_CIRCULAR = "SELECT * FROM TBCIRCULAR WHERE idusermobile = ? and idshop = ? ORDER BY ano_mes DESC";
    private final String DQL_GET_CIRCULAR= "SELECT * FROM TBCIRCULAR WHERE idcircula = ? and idusermobile = ? and idshop = ?";
    private final String DQL_GET_CIRCULARESLEITURA= "SELECT * FROM TBCIRCULAR WHERE idusermobile = ? and idshop = ? and leitura = ?";
    private final String DQL_GET_CIRCULAR_USUARIO = "SELECT * FROM TBCIRCULAR WHERE idusermobile = ? and idshop = ? ORDER BY ano_mes DESC";

    public static CircularDAO getNewInstance(){return new CircularDAO();}
//AKIKKKKKKKKKKKKKKKKK
    public Circular getCircular(Integer idcircular,Integer iduser,Integer idshop ){
        Circular ci = null;
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULAR, new String[]{idcircular.toString(),iduser.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0){
                c.moveToNext();
                ci = new Circular();
                ci.setIdcircular(c.getInt(c.getColumnIndex("idcircula")));
                ci.setTitulo(c.getString(c.getColumnIndex("titulo")));
                ci.setData_cadastro(DateHelper.toDate(c.getString(c.getColumnIndex("data_cad"))));
                ci.setAcesso(c.getInt(c.getColumnIndex("acessos")));
                ci.setNomearquivo(c.getString(c.getColumnIndex("nomearquivo")));
                ci.setIduser(c.getInt(c.getColumnIndex("iduser")));
                ci.setFlagleitura(c.getInt(c.getColumnIndex("leitura")));
                ci.setAnomes(c.getString(c.getColumnIndex("ano_mes")));
                ci.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                ci.setIdshop(c.getInt(c.getColumnIndex("idshop")));
            }
        }finally {
            c.close();
        }


        return ci;
    }
    public List<Circular> listCircular(Integer iduser,Integer idshop){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_SELECT_CIRCULAR ,new String[]{iduser.toString(),idshop.toString()} );
        try
        {
            if(c.getCount() > 0){
                while (c.moveToNext()) {
                    l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")),iduser,idshop));
                }
            }
        }finally {
            c.close();
        }

        return l;
    }
    public List<Circular> listCircularPorUsuario(Integer iduser,Integer idshop){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULAR_USUARIO , new String[]{iduser.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0){
                while (c.moveToNext()) {
                    l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")),iduser,idshop));
                }
            }
        }
        finally {
            c.close();
        }


        return l;
    }
    public List<Circular> listCircularLeitura(Integer iduser,Integer idshop,Integer leitura){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULARESLEITURA , new String[]{iduser.toString(),idshop.toString(),leitura.toString()});
        try {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")), iduser, idshop));
                }
            }
        }finally {
            c.close();
        }

        return l;
    }
    /*public List<Circular> listCircularAnoMes(String anomes,Integer iduser){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULAR_ANO_MES , new String[]{anomes,iduser.toString()});
        if(c.getCount() > 0){
            while (c.moveToNext()) {
                l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")),iduser));
            }
        }
        c.close();
        return l;
    }*/
    /*public List<Circular> listCircularAno(String ano,Integer iduser){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULAR_ANO , new String[]{ano,iduser.toString()});
        if(c.getCount() > 0){
            while (c.moveToNext()) {
                l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")),iduser));
            }
        }
        c.close();
        return l;
    }*/
    /*public List<Circular> listCircularMes(String mes,Integer iduser){
        List<Circular> l = new ArrayList<Circular>();
        Cursor c = getDb().rawQuery(DQL_GET_CIRCULAR_MES , new String[]{mes,iduser.toString()});
        if(c.getCount() > 0){
            while (c.moveToNext()) {
                l.add(getCircular(c.getInt(c.getColumnIndex("idcircula")),iduser));
            }
        }
        c.close();
        return l;
    }*/
    public void save(Circular circular,Integer iduser,Integer idshop) {
        Circular old = getCircular(circular.getIdcircular(),iduser,idshop);
        ContentValues vl = new ContentValues();
        vl.put("idcircula", circular.getIdcircular());
        vl.put("titulo", circular.getTitulo());
        vl.put("data_cad", DateHelper.toString(circular.getData_cadastro()));
        vl.put("acessos", circular.getAcesso());
        vl.put("nomearquivo", circular.getNomearquivo());
        vl.put("iduser", iduser);
        vl.put("leitura", circular.getFlagleitura());
        vl.put("ano_mes", circular.getAnomes());
        vl.put("idusermobile",circular.getIdusermobile());
        vl.put("idshop",circular.getIdshop());

        if(old == null){
            vl.put("idcircula", circular.getIdcircular());
            getDb().insert("TBCIRCULAR", "", vl);
        }else{
            getDb().update("TBCIRCULAR", vl, "idcircula = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(circular.getIdcircular()),iduser.toString(),idshop.toString()});
        }

    }
    public void remove(Circular circular){
        //if(valid(circular.getIdcircular()))
            getDb().delete("TBCIRCULAR", "idcircula = ?", new String[]{String.valueOf(circular.getIdcircular())});
    }
    public void removeAll(){
        getDb().delete("TBCIRCULAR", "", null);
    }
}
