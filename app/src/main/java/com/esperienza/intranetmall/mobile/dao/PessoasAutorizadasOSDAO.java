package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 14/01/2016.
 */
public class PessoasAutorizadasOSDAO extends DAO {

    private final String DQL_GET_PESSOASAUTORIZADASOS = "SELECT * FROM TBPESSOASAUTORIZADASOS WHERE idpa = ? and idos = ? and iduser = ? and idshop = ?";
    private final String DQL_GET_PESSOASAUTORIZADAS_POR_OS = "SELECT * FROM TBPESSOASAUTORIZADASOS WHERE idos = ? and iduser = ? and idshop = ?";
    private final String DQL_LISTA_PESSOASAUTORIZADAS_RG = "SELECT DISTINCT nome, rg, empresa FROM TBPESSOASAUTORIZADASOS WHERE nome = ? and rg = ? and empresa = ?";
    private final String DQL_LISTA_PA_LOJA ="SELECT DISTINCT nome, rg, empresa FROM TBPESSOASAUTORIZADASOS WHERE idos IN (SELECT id_os FROM TBORDEMSERVICO WHERE iduser = ? and idshop = ? ) ORDER BY empresa asc, nome asc";
    private final String DQL_LISTA_PA_LOJA_RETORNO ="SELECT DISTINCT nome, rg, empresa FROM TBPESSOASAUTORIZADASOS WHERE (iduser = ?) OR idos IN (SELECT id_os FROM TBORDEMSERVICO WHERE iduser = ? and idshop = ? ) ORDER BY empresa asc, nome asc";
    private final String DQL_LISTA_PA_MAX="SELECT MAX(idpa) as cmax FROM TBPESSOASAUTORIZADASOS WHERE idshop = ?";
    private final String DQL_LISTA_PA_N="SELECT * FROM TBPESSOASAUTORIZADASOS WHERE (idos = 0 or idos = null) and iduser = ? and idshop = ?";

    public static PessoasAutorizadasOSDAO getNewInstance(){
        return new PessoasAutorizadasOSDAO();
    }
    public int getMax(Integer idshop)
    {
        int retorno=0;
        Cursor c = getDb().rawQuery(DQL_LISTA_PA_MAX, new String[]{idshop.toString()});
        try
        {
            if(c.getCount()>0)
            {
                c.moveToNext();
                retorno=c.getInt(c.getColumnIndex("cmax"));
            }

        }
        finally
        {
            c.close();
        }

        return retorno;
    }

    public PessoasAutorizadasOS getPessoasAutorizadasOS(Integer idpa,Integer idos,Integer iduser,Integer idshop){
        PessoasAutorizadasOS pa=null;
        Cursor c = getDb().rawQuery(DQL_GET_PESSOASAUTORIZADASOS, new String[]{idpa.toString(),idos.toString(),iduser.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0)
            {
                c.moveToNext();
                pa=new PessoasAutorizadasOS();
                pa.setId(c.getInt(c.getColumnIndex("idpa")));
                pa.setIdos(c.getInt(c.getColumnIndex("idos")));
                pa.setIduser(c.getInt(c.getColumnIndex("iduser")));
                pa.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                pa.setNome(c.getString(c.getColumnIndex("nome")));
                pa.setRg(c.getString(c.getColumnIndex("rg")));
                pa.setEmpresa(c.getString(c.getColumnIndex("empresa")));

            }
        }
        finally
        {
            c.close();
        }

        return pa;
    }

    public PessoasAutorizadasOS getPessoasAutorizadasOS(String nome,String rg,String empresa){
        PessoasAutorizadasOS pa=null;
        Cursor c = getDb().rawQuery(DQL_LISTA_PESSOASAUTORIZADAS_RG, new String[]{nome,rg,empresa});
        try
        {
            if(c.getCount() > 0)
            {
                c.moveToNext();
                pa=new PessoasAutorizadasOS();
                //pa.setId(c.getInt(c.getColumnIndex("idpa")));
                //pa.setIdos(c.getInt(c.getColumnIndex("idos")));
                //pa.setIduser(c.getInt(c.getColumnIndex("iduser")));
                //pa.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                pa.setNome(c.getString(c.getColumnIndex("nome")));
                pa.setRg(c.getString(c.getColumnIndex("rg")));
                pa.setEmpresa(c.getString(c.getColumnIndex("empresa")));

            }
        }
        finally
        {
            c.close();
        }

        return pa;
    }
    public List<PessoasAutorizadasOS> listPessoasAutorizadasOSPorOS(Integer paos,Integer iduser,Integer idshop){
        List<PessoasAutorizadasOS> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_GET_PESSOASAUTORIZADAS_POR_OS, new String[]{paos.toString(),iduser.toString(),idshop.toString()});
        try
        {
            if(c.getCount() > 0){
                while (c.moveToNext()) {
                    l.add(getPessoasAutorizadasOS(c.getInt(c.getColumnIndex("idpa")),c.getInt(c.getColumnIndex("idos")),c.getInt(c.getColumnIndex("iduser")),c.getInt(c.getColumnIndex("idshop"))));
                }
            }
        }
        finally
        {
            c.close();
        }

        return l;
    }
    public List<PessoasAutorizadasOS> listPessoasAutorizadasOSPorN(Integer iduser,Integer idshop){
        List<PessoasAutorizadasOS> l = new ArrayList<>();
        try {
            Cursor c = getDb().rawQuery(DQL_LISTA_PA_N, new String[]{iduser.toString(), idshop.toString()});
            try {
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        l.add(getPessoasAutorizadasOS(c.getInt(c.getColumnIndex("idpa")),c.getInt(c.getColumnIndex("idos")),c.getInt(c.getColumnIndex("iduser")),c.getInt(c.getColumnIndex("idshop"))));
                    }
                }
            } finally {
                c.close();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return l;
    }

    public List<PessoasAutorizadasOS> listPessoasAutorizadasOSLoja(Integer iduser,Integer idshop){
        List<PessoasAutorizadasOS> l = new ArrayList<>();
        try{
            Cursor c = getDb().rawQuery(DQL_LISTA_PA_LOJA, new String[]{iduser.toString(),idshop.toString()});
            try
            {
                if(c.getCount() > 0){
                    while (c.moveToNext()) {
                        l.add(getPessoasAutorizadasOS(c.getString(c.getColumnIndex("nome")),c.getString(c.getColumnIndex("rg")), c.getString(c.getColumnIndex("empresa"))));
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

        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return l;
    }
    public List<PessoasAutorizadasOS> listPessoasAutorizadasOSLojaRetorno(Integer iduser,Integer idshop){
        List<PessoasAutorizadasOS> l = new ArrayList<>();
        try{
            Cursor c = getDb().rawQuery(DQL_LISTA_PA_LOJA_RETORNO, new String[]{iduser.toString(),iduser.toString(),idshop.toString()});
            try
            {
                if(c.getCount() > 0){
                    while (c.moveToNext()) {
                        l.add(getPessoasAutorizadasOS(c.getString(c.getColumnIndex("nome")),c.getString(c.getColumnIndex("rg")), c.getString(c.getColumnIndex("empresa"))));
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

        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return l;
    }
    /*public List<PessoasAutorizadasOS> listPessoasAutorizadasOS(){
        List<PessoasAutorizadasOS> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_LISTA_PESSOASAUTORIZADAS, null);
        try
        {
            if(c.getCount() > 0)
            {
                while (c.moveToNext()) {
                    l.add(getPessoasAutorizadasOS(c.getInt(c.getColumnIndex("idpa")),c.getInt(c.getColumnIndex("idshop"))));
                }
            }
        }
        finally
        {
            c.close();
        }

        return l;
    }*/
    public void save(PessoasAutorizadasOS pessoasAutorizadasOS) {
        PessoasAutorizadasOS old = getPessoasAutorizadasOS(pessoasAutorizadasOS.getId(),pessoasAutorizadasOS.getIdos(),pessoasAutorizadasOS.getIduser(),pessoasAutorizadasOS.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("idpa", pessoasAutorizadasOS.getId());
        vl.put("idos", pessoasAutorizadasOS.getIdos());
        if(pessoasAutorizadasOS.getIduser()!=0)
        vl.put("iduser",pessoasAutorizadasOS.getIduser());
        if(pessoasAutorizadasOS.getIdshop()!=0)
        vl.put("idshop",pessoasAutorizadasOS.getIdshop());
        vl.put("nome",pessoasAutorizadasOS.getNome());
        vl.put("rg",pessoasAutorizadasOS.getRg());
        vl.put("empresa",pessoasAutorizadasOS.getEmpresa());


        if(old == null){
            //vl.put("idarquivo", arquivoOS.getIdarquivo());
            getDb().insert("TBPESSOASAUTORIZADASOS", "", vl);
        }else{
            getDb().update("TBPESSOASAUTORIZADASOS", vl, "idpa = ? and idos = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(pessoasAutorizadasOS.getId()),String.valueOf(pessoasAutorizadasOS.getIdos()),String.valueOf(pessoasAutorizadasOS.getIduser()),String.valueOf(pessoasAutorizadasOS.getIdshop())});
        }

    }
    public void remove(PessoasAutorizadasOS pa){
        //if(valid(circular.getIdcircular()))
        getDb().delete("TBPESSOASAUTORIZADASOS", "idpa = ? and idos = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(pa.getId()),String.valueOf(pa.getIdos()),String.valueOf(pa.getIduser()),String.valueOf(pa.getIdshop())});
    }
    public void removeAll(){
        getDb().delete("TBPESSOASAUTORIZADASOS", "", null);
    }




}
