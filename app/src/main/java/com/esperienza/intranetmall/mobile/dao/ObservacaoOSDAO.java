package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 14/01/2016.
 */
public class ObservacaoOSDAO extends DAO {

    private final String DQL_GET_OBSERVACAO = "SELECT * FROM TBOBSERVACAOOS WHERE idcomentario = ? and idusermobile = ? and idshop = ?";
    private final String DQL_GET_OBSERVACAO_POR_OS = "SELECT * FROM TBOBSERVACAOOS WHERE idos = ? and idusermobile = ? and idshop = ?";
    private final String DQL_LISTA_OBSERVACAO = "SELECT * FROM TBOBSERVACAOOS";

    public static ObservacaoOSDAO getNewInstance(){
        return new ObservacaoOSDAO();
    }

    public ObservacaoOS getObservacaoOS(Integer idobs,Integer idusermobile,Integer idshop){
        ObservacaoOS obs=null;
        Cursor c = getDb().rawQuery(DQL_GET_OBSERVACAO, new String[]{idobs.toString(),idusermobile.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0)
            {
                c.moveToNext();
                obs = new ObservacaoOS();
                obs.setIduser(c.getInt(c.getColumnIndex("idcomentario")));
                obs.setIdos(c.getInt(c.getColumnIndex("idos")));
                obs.setIduser(c.getInt(c.getColumnIndex("iduser")));
                obs.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                obs.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                obs.setDatacad(DateHelper.toDate(c.getString(c.getColumnIndex("datacad"))));
                obs.setHoracad(c.getString(c.getColumnIndex("horacad")));
                obs.setObservacoes(c.getString(c.getColumnIndex("observacoes")));
            }
        }finally {
            c.close();
        }

        return obs;
    }
    public List<ObservacaoOS> listObservacaoOSPorOS(Integer idos,Integer idusermobile,Integer idshop){
        List<ObservacaoOS> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_OBSERVACAO_POR_OS, new String[]{idos.toString(),idusermobile.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0){
                while (c.moveToNext()) {
                    l.add(getObservacaoOS(c.getInt(c.getColumnIndex("idcomentario")),idusermobile,idshop));
                }
            }
        }
        finally
        {
            c.close();
        }

        return l;
    }
    public void save(ObservacaoOS observacaoOS) {
        ObservacaoOS old = getObservacaoOS(observacaoOS.getIdcomentario(),observacaoOS.getIdusermobile(),observacaoOS.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idcomentario", observacaoOS.getIdcomentario());
        vl.put("idos", observacaoOS.getIdos());
        vl.put("iduser",observacaoOS.getIduser());
        vl.put("idusermobile",observacaoOS.getIdusermobile());
        vl.put("idshop",observacaoOS.getIdshop());
        vl.put("datacad",DateHelper.toString(observacaoOS.getDatacad()));
        vl.put("horacad",observacaoOS.getHoracad());
        vl.put("observacoes",observacaoOS.getObservacoes());

        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBOBSERVACAOOS", "", vl);
        }else{
            getDb().update("TBOBSERVACAOOS", vl, "idcomentario = ? and idos = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(observacaoOS.getIdcomentario()),String.valueOf(observacaoOS.getIdos()),String.valueOf(observacaoOS.getIdusermobile()),String.valueOf(observacaoOS.getIdshop())});
        }

    }
    public void removeAll(){
        getDb().delete("TBOBSERVACAOOS", "", null);
    }


}
