package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 28/01/2016.
 */
public class AprovadoresOSDAO extends DAO{

    private final String DQL_GET_APROVADORES = "SELECT * FROM TBAPROVADORES WHERE iduser = ? and idos = ? and idusermobile = ? and idshop = ?";
    private final String DQL_GET_APROVADORES_POR_OS = "SELECT * FROM TBAPROVADORES WHERE idos = ? and idusermobile = ? and idshop = ? ORDER BY ordem ";
    private final String DQL_LISTA_APROVADORES = "SELECT * FROM TBAPROVADORES";

    public static AprovadoresOSDAO getNewInstance(){
        return new AprovadoresOSDAO();
    }

    public AprovadoresOS getAprovadoresOS(Integer iduser,Integer idos,Integer idusermobile,Integer idshop)
    {
        AprovadoresOS apr=null;
        Cursor c = getDb().rawQuery(DQL_GET_APROVADORES, new String[]{iduser.toString(),idos.toString(),idusermobile.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0)
            {
                c.moveToNext();
                apr = new AprovadoresOS();
                apr.setIduser(c.getInt(c.getColumnIndex("iduser")));
                apr.setIdos(c.getInt(c.getColumnIndex("idos")));
                apr.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                apr.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                apr.setAcao(c.getInt(c.getColumnIndex("acao")));
                apr.setAlcadas(c.getInt(c.getColumnIndex("alcadas")));
                apr.setOrdem(c.getInt(c.getColumnIndex("ordem")));
                apr.setSuplente(c.getInt(c.getColumnIndex("suplente")));
                apr.setEmail(c.getString(c.getColumnIndex("email")));
                apr.setNome(c.getString(c.getColumnIndex("nome")));

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



        return apr;
    }
    public List<AprovadoresOS> listAprovadoresOS(Integer idusermobile,Integer idshop,Integer idos){
        List<AprovadoresOS> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_APROVADORES_POR_OS, new String[]{idos.toString(),idusermobile.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0)
            {
                while (c.moveToNext())
                {
                    l.add(getAprovadoresOS(c.getInt(c.getColumnIndex("iduser")),idos,idusermobile,idshop));
                }
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

        return l;
    }
    public void save(AprovadoresOS aprovadoresOS) {
        AprovadoresOS old = getAprovadoresOS(aprovadoresOS.getIduser(),aprovadoresOS.getIdos(),aprovadoresOS.getIdusermobile(),aprovadoresOS.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("iduser", aprovadoresOS.getIduser());
        vl.put("idos", aprovadoresOS.getIdos());
        vl.put("idusermobile",aprovadoresOS.getIdusermobile());
        vl.put("idshop",aprovadoresOS.getIdshop());
        vl.put("acao", aprovadoresOS.getAcao());
        vl.put("alcadas", aprovadoresOS.getAlcadas());
        vl.put("ordem", aprovadoresOS.getOrdem());
        vl.put("suplente",aprovadoresOS.getSuplente());
        vl.put("email", aprovadoresOS.getEmail());
        vl.put("nome", aprovadoresOS.getNome());
        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBAPROVADORES", "", vl);
        }else{
            getDb().update("TBAPROVADORES", vl, "iduser = ? and idos = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(aprovadoresOS.getIduser()),String.valueOf(aprovadoresOS.getIdos()),String.valueOf(aprovadoresOS.getIdusermobile()), String.valueOf(aprovadoresOS.getIdshop())});
        }

    }
    public void removeAll(){
        getDb().delete("TBAPROVADORES","", null);
    }

}
