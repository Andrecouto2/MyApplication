package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.dao.DAO;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/11/2015.
 */
public class RegraOrdemServicoDAO extends DAO {

    private final String DQL_GET_REGRAOS = "SELECT * FROM TBOSREGRA WHERE idosregra = ? and idshop = ?";
    private final String DQL_GET_REGRAOSPRDIASEMANA = "SELECT * FROM TBOSREGRA WHERE dia_semana = ? and idshop = ?";
    private final String DQL_LISTA_REGRAOS = "SELECT * FROM TBOSREGRA WHERE idshop = ?";

    public static RegraOrdemServicoDAO getNewInstance(){
        return new RegraOrdemServicoDAO();
    }

    public RegraOrdemServico getRegraOS(Integer id,Integer idshop){
        RegraOrdemServico r = null;
        Cursor c = getDb().rawQuery(DQL_GET_REGRAOS, new String[]{id.toString(),idshop.toString()});
        if(c.getCount() > 0){
            c.moveToNext();
            r = new RegraOrdemServico();
            r.setIdosregra(c.getInt(c.getColumnIndex("idosregra")));
            r.setIdshop(c.getInt(c.getColumnIndex("idshop")));
            r.setDia_semana(c.getInt(c.getColumnIndex("dia_semana")));
            r.setPermissao_dia(c.getInt(c.getColumnIndex("permissao_dia")));
            r.setHora_limite(c.getString(c.getColumnIndex("hora_limite")));
            r.setSoma_dia_hora_ate_limite(c.getInt(c.getColumnIndex("soma_dia_hora_ate_limite")));
            r.setHorario_ate_limite(c.getString(c.getColumnIndex("horario_ate_limite")));
            r.setSoma_dia_hora_apos_limite(c.getInt(c.getColumnIndex("soma_dia_hora_apos_limite")));
            r.setHorario_apos_limite(c.getString(c.getColumnIndex("horario_apos_limite")));
            r.setHorario_dia_posterior(c.getString(c.getColumnIndex("horario_dia_posterior")));
        }
        c.close();
        return r;
    }
    public RegraOrdemServico getRegraOSPordiaSemana(Integer diasemana,Integer idshop){
        RegraOrdemServico r = null;
        Cursor c = getDb().rawQuery(DQL_GET_REGRAOSPRDIASEMANA, new String[]{diasemana.toString(),idshop.toString()});
        if(c.getCount() > 0){
            c.moveToNext();
            r = new RegraOrdemServico();
            r.setIdosregra(c.getInt(c.getColumnIndex("idosregra")));
            r.setIdshop(c.getInt(c.getColumnIndex("idshop")));
            r.setDia_semana(c.getInt(c.getColumnIndex("dia_semana")));
            r.setPermissao_dia(c.getInt(c.getColumnIndex("permissao_dia")));
            r.setHora_limite(c.getString(c.getColumnIndex("hora_limite")));
            r.setSoma_dia_hora_ate_limite(c.getInt(c.getColumnIndex("soma_dia_hora_ate_limite")));
            r.setHorario_ate_limite(c.getString(c.getColumnIndex("horario_ate_limite")));
            r.setSoma_dia_hora_apos_limite(c.getInt(c.getColumnIndex("soma_dia_hora_apos_limite")));
            r.setHorario_apos_limite(c.getString(c.getColumnIndex("horario_apos_limite")));
            r.setHorario_dia_posterior(c.getString(c.getColumnIndex("horario_dia_posterior")));
        }
        c.close();
        return r;
    }
    /*public List<RegraOrdemServico> listRegraOS(){
        List<RegraOrdemServico> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_LISTA_REGRAOS , null);
        if(c.getCount() > 0){
            while (c.moveToNext()) {
                l.add(getRegraOS(c.getInt(c.getColumnIndex("idosregra"))));
            }
        }
        c.close();
        return l;
    }*/
    public void save(RegraOrdemServico regraOrdemServico) {
        RegraOrdemServico old = getRegraOS(regraOrdemServico.getIdosregra(),regraOrdemServico.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idosregra", regraOrdemServico.getIdosregra());
        vl.put("idshop",regraOrdemServico.getIdshop());
        vl.put("dia_semana",regraOrdemServico.getDia_semana());
        vl.put("permissao_dia ",regraOrdemServico.getPermissao_dia());
        vl.put("hora_limite",regraOrdemServico.getHora_limite());
        vl.put("soma_dia_hora_ate_limite",regraOrdemServico.getSoma_dia_hora_ate_limite());
        vl.put("horario_ate_limite",regraOrdemServico.getHorario_ate_limite());
        vl.put("soma_dia_hora_apos_limite",regraOrdemServico.getSoma_dia_hora_apos_limite());
        vl.put("horario_apos_limite",regraOrdemServico.getHorario_apos_limite());
        vl.put("horario_dia_posterior",regraOrdemServico.getHorario_dia_posterior());



        if(old == null){
            vl.put("idosregra", regraOrdemServico.getIdosregra());
            getDb().insert("TBOSREGRA", "", vl);
        }else{
            getDb().update("TBOSREGRA", vl, "idosregra = ? and idshop = ?", new String[]{String.valueOf(regraOrdemServico.getIdosregra()),String.valueOf(regraOrdemServico.getIdshop())});
        }

    }
    public void remove(RegraOrdemServico regraOrdemServico){
        //if(valid(circular.getIdcircular()))
        getDb().delete("TBOSREGRA", "idosregra = ? and idshop = ?", new String[]{String.valueOf(regraOrdemServico.getIdosregra()),String.valueOf(regraOrdemServico.getIdshop())});
    }
    public void removeAll(){
        getDb().delete("TBOSREGRA", "", null);
    }
}
