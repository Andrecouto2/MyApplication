package com.esperienza.intranetmall.mobile.adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.dao.DAO;
import com.esperienza.intranetmall.mobile.entidade.CamposObrigatoriosFnc;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/04/2016.
 */
public class CamposObrigatoriosFncDAO extends DAO{

    private final String DQL_GET_CAMPOS= "SELECT * FROM TBCONFIGFUNCIONARIOS WHERE iduser = ? and idshop = ? ORDER BY id_config_fnc ASC";
    private final String DQL_GET_CAMPO= "SELECT * FROM TBCONFIGFUNCIONARIOS WHERE id_config_fnc = ? and iduser = ? and idshop = ?";

    public static  CamposObrigatoriosFncDAO getNewInstance(){return new CamposObrigatoriosFncDAO();}

    public CamposObrigatoriosFnc getCampoObrigatorioFnc(Integer idobrigfnc,Integer iduser,Integer idshop)
    {
        CamposObrigatoriosFnc cof=null;
        try
        {
            Cursor c = getDb().rawQuery(DQL_GET_CAMPO, new String[]{idobrigfnc.toString(),iduser.toString(),idshop.toString()});

        try
        {
            if(c.getCount() > 0){
                c.moveToNext();
                cof = new CamposObrigatoriosFnc();
                cof.setId(c.getInt(c.getColumnIndex("id_config_fnc")));
                cof.setIduser(c.getInt(c.getColumnIndex("iduser")));
                cof.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                cof.setObrigatorio(c.getInt(c.getColumnIndex("obrigatorio")));
                cof.setCampo(c.getString(c.getColumnIndex("campo")));
            }
        }finally {
            c.close();
        }
        } catch (Exception e)
        {
              e.printStackTrace();
        }
        return cof;

    }

    public List<CamposObrigatoriosFnc> getCamposObrigatorio(Integer iduser,Integer idshop)
    {
        List<CamposObrigatoriosFnc> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_CAMPOS ,new String[]{iduser.toString(),idshop.toString()} );

        try
        {
            if(c.getCount() > 0)
            {
                while (c.moveToNext())
                {
                   l.add(getCampoObrigatorioFnc(c.getInt(c.getColumnIndex("id_config_fnc")),iduser,idshop));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            c.close();
        }
        return l;
    }
    public void save(CamposObrigatoriosFnc campos,Integer iduser,Integer idshop)
    {
        CamposObrigatoriosFnc old = getCampoObrigatorioFnc(campos.getId(),campos.getIduser(),campos.getIdshop());
        ContentValues vl = new ContentValues();

        vl.put("id_config_fnc",campos.getId());
        vl.put("iduser",campos.getIduser());
        vl.put("idshop",campos.getIdshop());
        vl.put("obrigatorio",campos.getObrigatorio());
        vl.put("campo",campos.getCampo());

        if(old == null){
            vl.put("id_config_fnc", campos.getId());
            getDb().insert("TBCONFIGFUNCIONARIOS", "", vl);
        }else{
            getDb().update("TBCONFIGFUNCIONARIOS", vl, "id_config_fnc = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(campos.getId()),iduser.toString(),idshop.toString()});
        }


    }

}
