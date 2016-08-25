package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 14/01/2016.
 */
public class ArquivoOSDAO extends DAO {


    private final String DQL_GET_ARQUIVO = "SELECT * FROM TBARQUIVOOS WHERE idarquivo = ? and idusermobile = ? and idshop = ?";
    private final String DQL_GET_ARQUIVO_POR_OS = "SELECT * FROM TBARQUIVOOS WHERE idos = ? and idusermobile = ? and idshop = ?";
    private final String DQL_LISTA_ARQUIVO = "SELECT * FROM TBARQUIVOOS";

    public static ArquivoOSDAO getNewInstance(){
        return new ArquivoOSDAO();
    }

    public ArquivoOS getArquivoOS(Integer idarquivo,Integer idusermobile,Integer idshop){
        ArquivoOS arq=null;
        Cursor c = getDb().rawQuery(DQL_GET_ARQUIVO, new String[]{idarquivo.toString(),idusermobile.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                arq = new ArquivoOS();
                arq.setIdarquivo(c.getInt(c.getColumnIndex("idarquivo")));
                arq.setIdos(c.getInt(c.getColumnIndex("idos")));
                arq.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                arq.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                arq.setDatablob(c.getBlob(c.getColumnIndex("blobdata")));
                arq.setIduser(c.getInt(c.getColumnIndex("iduser")));
                arq.setCodgerador(c.getString(c.getColumnIndex("codgerador")));
                arq.setUrlarquivo(c.getString(c.getColumnIndex("urlarquivo")));
                arq.setExtensao(c.getString(c.getColumnIndex("extensao")));

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

        return arq;
    }

    public List<ArquivoOS> listArquivoOSPorOS(Integer idos,Integer idusermobile,Integer idshop){
        List<ArquivoOS> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_ARQUIVO_POR_OS, new String[]{idos.toString(),idusermobile.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0){
                while (c.moveToNext()) {
                    l.add(getArquivoOS(c.getInt(c.getColumnIndex("idarquivo")),idusermobile,idshop));
                }
            }
        }
        finally
        {
            c.close();
        }

        return l;
    }
    public void save(ArquivoOS arquivoOS) {
        ArquivoOS old = getArquivoOS(arquivoOS.getIdarquivo(),arquivoOS.getIdusermobile(),arquivoOS.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idarquivo", arquivoOS.getIdarquivo());
        vl.put("idos", arquivoOS.getIdos());
        vl.put("iduser",arquivoOS.getIduser());
        vl.put("idusermobile",arquivoOS.getIdusermobile());
        vl.put("idshop",arquivoOS.getIdshop());
        vl.put("codgerador",arquivoOS.getCodgerador());
        vl.put("urlarquivo",arquivoOS.getUrlarquivo());
        vl.put("extensao",arquivoOS.getExtensao());

        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBARQUIVOOS", "", vl);
        }else{
            getDb().update("TBARQUIVOOS", vl, "idarquivo = ? and idos = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(arquivoOS.getIdarquivo()),String.valueOf(arquivoOS.getIdos()),String.valueOf(arquivoOS.getIdusermobile()),String.valueOf(arquivoOS.getIdshop())});
        }

    }
    public void removeAll(){
        getDb().delete("TBARQUIVOOS", "", null);
    }
}
