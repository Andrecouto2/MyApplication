package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.ConfigHorariosOS;

/**
 * Created by ThinkPad on 12/02/2016.
 */
public class ConfigHorarioOSDAO extends DAO {

    private final String DQL_LISTA_CONFIGHORARIOS = "SELECT * FROM TBCONFIGHORARIOS";

    public static ConfigHorarioOSDAO getNewInstance(){
        return new ConfigHorarioOSDAO();
    }

    public ConfigHorariosOS getConfigHorarioOS(){
        ConfigHorariosOS ch=null;
        Cursor c = getDb().rawQuery(DQL_LISTA_CONFIGHORARIOS, null);
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                ch = new ConfigHorariosOS();
                ch.setId_config_horario(c.getInt(c.getColumnIndex("id_config_horario")));
                ch.setHorario_func_inicio(c.getString(c.getColumnIndex("horario_func_inicio")));
                ch.setHorario_func_fim(c.getString(c.getColumnIndex("horario_func_fim")));
                ch.setGmt(c.getString(c.getColumnIndex("gmt")));
                ch.setTempo_token(c.getInt(c.getColumnIndex("tempo_token")));


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            c.close();
        }

        return ch;
    }
    public void save(ConfigHorariosOS configHorariosOS) {
        ConfigHorariosOS old = getConfigHorarioOS();
        ContentValues vl = new ContentValues();
        vl.put("id_config_horario", configHorariosOS.getId_config_horario());
        vl.put("horario_func_inicio", configHorariosOS.getHorario_func_inicio());
        vl.put("horario_func_fim",configHorariosOS.getHorario_func_fim());
        vl.put("gmt",configHorariosOS.getGmt());
        vl.put("tempo_token",configHorariosOS.getTempo_token());


        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBCONFIGHORARIOS", "", vl);
        }else{
            getDb().update("TBCONFIGHORARIOS", vl, "id_config_horario = ?", new String[]{String.valueOf(configHorariosOS.getId_config_horario())});
        }

    }
    public void removeAll(){
        getDb().delete("TBCONFIGHORARIOS", "", null);
    }
}
