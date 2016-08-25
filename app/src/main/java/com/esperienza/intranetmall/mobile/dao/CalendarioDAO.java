package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.webservices.CadastraDispositivoWs;

/**
 * Created by ThinkPad on 12/02/2016.
 */
public class CalendarioDAO extends DAO {

    private final String DQL_GET_CALENDARIO = "SELECT * FROM TBCALENDARIO WHERE ddata = ? and idshop = ?";
    private final String DQL_GET_CALENDARIO_PROX_DIA_UTIL ="SELECT * FROM TBCALENDARIO WHERE ddata > ? AND dutil=1 and idshop = ? LIMIT 1 ";

    public static CalendarioDAO getNewInstance(){
        return new CalendarioDAO();
    }

    public Calendario getCalendario(String data,Integer idshop){
        Calendario cal=null;
        Cursor c = getDb().rawQuery(DQL_GET_CALENDARIO, new String[]{data,idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                cal= new Calendario();
                cal.setDdata(DateHelper.toDate(c.getString(c.getColumnIndex("ddata"))));
                cal.setDutil(c.getInt(c.getColumnIndex("dutil")));
                cal.setFeriado(c.getInt(c.getColumnIndex("feriado")));
                cal.setIdshop(c.getInt(c.getColumnIndex("idshop")));

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

        return cal;
    }
    public Calendario getCalendarioDiaUtil(String data,Integer idshop){
        Calendario cal=null;
        try {
            Cursor c = getDb().rawQuery(DQL_GET_CALENDARIO_PROX_DIA_UTIL, new String[]{data, idshop.toString()});
            try {
                if (c.getCount() > 0) {
                    c.moveToNext();
                    cal = new Calendario();
                    cal.setDdata(DateHelper.toDate(c.getString(c.getColumnIndex("ddata"))));
                    cal.setDutil(c.getInt(c.getColumnIndex("dutil")));
                    cal.setFeriado(c.getInt(c.getColumnIndex("feriado")));
                    cal.setIdshop(c.getInt(c.getColumnIndex("idshop")));

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return cal;
    }
    public void save(Calendario calendario) {
        Calendario old = getCalendario(DateHelper.toStringSQLServer(calendario.getDdata()),calendario.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("ddata"  , DateHelper.toStringSQLServer(calendario.getDdata()));
        vl.put("dutil"  , calendario.getDutil());
        vl.put("feriado", calendario.getFeriado());
        vl.put("idshop" , calendario.getIdshop());


        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBCALENDARIO", "", vl);
        }else{
            getDb().update("TBCALENDARIO", vl, "ddata = ? and idshop = ?", new String[]{DateHelper.toStringSQLServer(calendario.getDdata()),String.valueOf(calendario.getIdshop())});
        }

    }

    public void removeAll(){
        getDb().delete("TBCALENDARIO", "", null);
    }

}
