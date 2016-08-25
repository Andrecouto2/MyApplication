package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 04/02/2016.
 */
public class TipoServicoDAO extends DAO {

    private final String DQL_GET_TIPOSERVICO = "SELECT * FROM TBTIPOSERVICO WHERE idtipo = ? and idshop = ?";
    private final String DQL_GET_LIST_TIPOSERVICO = "SELECT * FROM TBTIPOSERVICO WHERE idshop = ? order by descricao";

    public static TipoServicoDAO getNewInstance(){ return new TipoServicoDAO();}

    public TipoServico getTipoServico(Integer idtiposervico,Integer idshop){
        TipoServico ts=null;
        Cursor c = getDb().rawQuery(DQL_GET_TIPOSERVICO, new String[]{idtiposervico.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0)
            {
                c.moveToNext();
                ts=new TipoServico();
                ts.setIdtipo(c.getInt(c.getColumnIndex("idtipo")));
                ts.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                ts.setDescricao(c.getString(c.getColumnIndex("descricao")));
                ts.setIddepto(c.getInt(c.getColumnIndex("iddepto")));
                ts.setIdordemservicosetor(c.getInt(c.getColumnIndex("idsetoros")));
                ts.setObs(c.getString(c.getColumnIndex("obs")));
                ts.setObrigatorioobs(c.getInt(c.getColumnIndex("obrigobs")));
                ts.setObrigatorioanexo(c.getInt(c.getColumnIndex("obriganexo")));
                ts.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                ts.setForafuncionamento(c.getInt(c.getColumnIndex("funcionamento")));

            }
        }
        finally
        {
            c.close();
        }

        return ts;
    }
    public List<TipoServico> getListaTipoServico(Integer idshop)
    {
        List<TipoServico> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_LIST_TIPOSERVICO, new String[]{idshop.toString()});
        try
        {
            if(c.getCount() > 0) {
                while (c.moveToNext()) {
                        l.add(getTipoServico(c.getInt(c.getColumnIndex("idtipo")),idshop));
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
    public void save(TipoServico ts) {
        TipoServico old = getTipoServico(ts.getIdtipo(),ts.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idtipo", ts.getIdtipo());
        vl.put("idshop", ts.getIdshop());
        vl.put("descricao", ts.getDescricao());
        vl.put("iddepto", ts.getIddepto());
        vl.put("idsetoros", ts.getIdordemservicosetor());
        vl.put("obs", ts.getObs());
        vl.put("obrigobs", ts.getObrigatorioobs());
        vl.put("obriganexo", ts.getObrigatorioanexo());
        vl.put("ativo", ts.getAtivo());
        vl.put("funcionamento", ts.getForafuncionamento());

        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBTIPOSERVICO", "", vl);
        }else{
            getDb().update("TBTIPOSERVICO", vl, "idtipo = ? and idshop = ?", new String[]{String.valueOf(ts.getIdtipo()),String.valueOf(ts.getIdshop())});
        }

    }
    public void removeAll(){
        getDb().delete("TBTIPOSERVICO", "", null);
    }
}
