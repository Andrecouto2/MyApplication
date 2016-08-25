package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 04/02/2016.
 */
public class OrdemServicoSetorDAO extends DAO {

    private final String DQL_GET_TBSETOROSPOR_ID = "SELECT * FROM TBTIPOSERVICO WHERE idsetoros = ? and idshop = ?";
    private final String DQL_GET_TBSETOROSTIPO = "SELECT * FROM TBSETOROS a INNER JOIN TBTIPOSERVICO b ON a.idsetoros=b.idsetoros ";
    private final String DQL_LISTA_TBSETOROS = "SELECT * FROM TBSETOROS WHERE idshop = ?";
    private final String DQL_GET_TBSETOROS = "SELECT * FROM TBSETOROS WHERE idsetoros = ? and idshop = ?";

    public static OrdemServicoSetorDAO getNewInstance(){ return new OrdemServicoSetorDAO();}

    public List<OrdemServicoSetor> getListaOrdemServicoSetor(Integer idshop)
    {
        List<OrdemServicoSetor> lsetoros= new ArrayList<>();
        OrdemServicoSetor setoros;
        try {

            Cursor c = getDb().rawQuery(DQL_LISTA_TBSETOROS, new String[]{idshop.toString()});
            try {
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        setoros = new OrdemServicoSetor();
                        setoros.setIdordemservicosetor(c.getInt(c.getColumnIndex("idsetoros")));
                        setoros.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                        setoros.setTitulo(c.getString(c.getColumnIndex("titulo")));
                        lsetoros.add(setoros);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return lsetoros;
    }
    public List<OrdemServicoSetor> getListaOrdemServicoSetorComTipos(Integer idshop)
    {
        List<OrdemServicoSetor> lsetoros=getListaOrdemServicoSetor(idshop);
        List<TipoServico> ltiposervico;
        TipoServico tipoServico=null;
        int count = lsetoros.size();
        for(int i=0;i<count;i++)
        {
            ltiposervico= new ArrayList<>();
            Cursor c = getDb().rawQuery(DQL_GET_TBSETOROSPOR_ID, new String[]{String.valueOf(lsetoros.get(i).getIdordemservicosetor()),idshop.toString()});
            try
            {
                if (c.getCount() > 0)
                {
                    while (c.moveToNext())
                    {
                        tipoServico = new TipoServico();
                        tipoServico.setIdtipo(c.getInt(c.getColumnIndex("idtipo")));
                        tipoServico.setDescricao(c.getString(c.getColumnIndex("descricao")));
                        tipoServico.setIddepto(c.getInt(c.getColumnIndex("iddepto")));
                        tipoServico.setIdordemservicosetor(c.getInt(c.getColumnIndex("idsetoros")));
                        tipoServico.setObs(c.getString(c.getColumnIndex("obs")));
                        tipoServico.setObrigatorioobs(c.getInt(c.getColumnIndex("obrigobs")));
                        tipoServico.setObrigatorioanexo(c.getInt(c.getColumnIndex("obriganexo")));
                        tipoServico.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                        tipoServico.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                        //tipoServico.setForafuncionamento(c.getInt(c.getColumnIndex("funcionamento")));

                        ltiposervico.add(tipoServico);

                    }
                    lsetoros.get(i).setTipoServicos(ltiposervico);

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
        }
        return lsetoros;
    }
    public OrdemServicoSetor getOrdemServicoSetor(Integer idsetoros,Integer idshop)
    {
        OrdemServicoSetor servicoSetor=null;
        Cursor c = getDb().rawQuery(DQL_GET_TBSETOROS, new String[]{idsetoros.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0)
            {
                c.moveToNext();
                servicoSetor=new OrdemServicoSetor();
                servicoSetor.setIdordemservicosetor(c.getInt(c.getColumnIndex("idsetoros")));
                servicoSetor.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                servicoSetor.setTitulo(c.getString(c.getColumnIndex("titulo")));
                servicoSetor.setIdshop(c.getInt(c.getColumnIndex("idshop")));

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
        return servicoSetor;
    }
    public void save(OrdemServicoSetor oss) {
        OrdemServicoSetor old = getOrdemServicoSetor(oss.getIdordemservicosetor(),oss.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idsetoros", oss.getIdordemservicosetor());
        vl.put("idshop",oss.getIdshop());
        vl.put("ativo", oss.getAtivo());
        vl.put("titulo", oss.getTitulo());
     ;
        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBSETOROS", "", vl);
        }else{
            getDb().update("TBSETOROS", vl, "idsetoros = ? and idshop = ?", new String[]{String.valueOf(oss.getIdordemservicosetor()),String.valueOf(oss.getIdshop())});
        }

    }
    public void removeAll(){
        getDb().delete("TBSETOROS", "", null);
    }


}
