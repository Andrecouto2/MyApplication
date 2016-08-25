package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BONSUCESSO on 13/11/2015.
 */
public class OrdemServicoDAO extends DAO{

    private final String DQL_GET_ORDEMSERVICO = "SELECT * FROM TBORDEMSERVICO WHERE id_os = ? and idusermobile = ? and idshop = ?";
    private final String DQL_LISTA_ORDEMSERVICO = "SELECT * FROM TBORDEMSERVICO WHERE idusermobile = ? and idshop = ? order by id_os desc";
    private final String DQL_COUNT_STATUS = "SELECT COUNT(*) AS qnt FROM TBORDEMSERVICO WHERE idusermobile = ? and idshop = ? status = ? ";

    public static OrdemServicoDAO getNewInstance(){
        return new  OrdemServicoDAO();
    }

    public int getCountStatus(Integer iduser,Integer idshop,Integer status)
    {
        Cursor c = getDb().rawQuery(DQL_COUNT_STATUS , new String[]{iduser.toString(),idshop.toString(),status.toString()});
        if(c.getCount() > 0)
        {
            int aux=0;
            while (c.moveToNext())
            {

                aux= c.getInt(c.getColumnIndex("qnt"));

            }
            return aux;
        }
        return 0;
    }

    public OrdemServico getOrdemServico(Integer id,Integer iduser,Integer idshop){
        OrdemServico os = null;
        Cursor c = getDb().rawQuery(DQL_GET_ORDEMSERVICO, new String[]{id.toString(),iduser.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                os = new OrdemServico();
                os.setId_os(c.getInt(c.getColumnIndex("id_os")));
                os.setIduser(c.getInt(c.getColumnIndex("iduser")));
                os.setIdtipo(c.getInt(c.getColumnIndex("idtipo")));
                os.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                os.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                os.setDatacad(DateHelper.toDate(c.getString(c.getColumnIndex("datacad"))));
                os.setHoracad(c.getString(c.getColumnIndex("horacad")));
                os.setDatainicio(DateHelper.toDate(c.getString(c.getColumnIndex("datainicio"))));
                os.setHorainicio(c.getString(c.getColumnIndex("horainicio")));
                os.setDatafim(DateHelper.toDate(c.getString(c.getColumnIndex("datafim"))));
                os.setHorafim(c.getString(c.getColumnIndex("horafim")));
                os.setStatus(c.getInt(c.getColumnIndex("status")));
                os.setNomelojista(c.getString(c.getColumnIndex("nomelojista")));
                os.setNomesolicita(c.getString(c.getColumnIndex("nomesolicita")));
                os.setTelefone(c.getString(c.getColumnIndex("telefone")));
                os.setEmail(c.getString(c.getColumnIndex("email")));
                os.setDescricao(c.getString(c.getColumnIndex("descricao")));
                os.setInicial(DateHelper.toDate(c.getString(c.getColumnIndex("inicial"))));
                os.setTermino(DateHelper.toDate(c.getString(c.getColumnIndex("final"))));
                os.setIddestino(c.getInt(c.getColumnIndex("iddestino")));
                os.setEmail2(c.getString(c.getColumnIndex("email2")));
                os.setIdWiseit(c.getString(c.getColumnIndex("idWiseit")));
                os.setCodItemOcorrencia(c.getString(c.getColumnIndex("codItemOcorrencia")));
                os.setAnomesdia(c.getString(c.getColumnIndex("anomesdia")));
                os.setObservacoes(c.getInt(c.getColumnIndex("observacoes")));

            }
        }finally {
            c.close();
        }

        return os;
    }
    public List<OrdemServico> listOrdemServico(Integer idusermobile,Integer idshop){
        List<OrdemServico> l = new ArrayList<OrdemServico>();
        try {
            Cursor c = getDb().rawQuery(DQL_LISTA_ORDEMSERVICO, new String[]{String.valueOf(idusermobile), String.valueOf(idshop)});
            try {
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        l.add(getOrdemServico(c.getInt(c.getColumnIndex("id_os")), idusermobile, idshop));
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

        return l;
    }
    public void save(OrdemServico ordemservico) {
        OrdemServico old = getOrdemServico(ordemservico.getId_os(),ordemservico.getIdusermobile(),ordemservico.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("id_os", ordemservico.getId_os());
        vl.put("iduser", ordemservico.getIduser());
        vl.put("idtipo", ordemservico.getIdtipo());
        vl.put("idtipo", ordemservico.getIdtipo());
        vl.put("datacad", DateHelper.toStringSQLServer(ordemservico.getDatacad()));
        vl.put("horacad",ordemservico.getHoracad());
        vl.put("datainicio", DateHelper.toStringSQLServer(ordemservico.getDatainicio()));
        vl.put("horainicio",ordemservico.getHorainicio());
        vl.put("datafim", DateHelper.toStringSQLServer(ordemservico.getDatafim()));
        vl.put("horafim",ordemservico.getHorafim());
        vl.put("status", ordemservico.getStatus());
        vl.put("nomelojista",ordemservico.getNomelojista());
        vl.put("nomesolicita", ordemservico.getNomesolicita());
        vl.put("telefone",ordemservico.getTelefone());
        vl.put("email", ordemservico.getEmail());
        vl.put("descricao", ordemservico.getDescricao());
        vl.put("inicial",DateHelper.toStringSQLServer(ordemservico.getInicial()));
        vl.put("final", DateHelper.toStringSQLServer(ordemservico.getTermino()));
        vl.put("iddestino",ordemservico.getIddestino());
        vl.put("email2", ordemservico.getEmail2());
        vl.put("idWiseit",ordemservico.getIdWiseit());
        vl.put("codItemOcorrencia", ordemservico.getCodItemOcorrencia());
        vl.put("anomesdia",ordemservico.getAnomesdia());
        vl.put("idusermobile",ordemservico.getIdusermobile());
        vl.put("idshop",ordemservico.getIdshop());
        vl.put("observacoes",ordemservico.getObservacoes());

        if(old == null){
            vl.put("id_os", ordemservico.getId_os());
            getDb().insert("TBORDEMSERVICO", "", vl);
        }else{
            getDb().update("TBORDEMSERVICO", vl, "id_os = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(ordemservico.getId_os()),String.valueOf(ordemservico.getIdusermobile()),String.valueOf(ordemservico.getIdshop())});
        }

    }
    public void remove(OrdemServico ordemservico){
        //if(valid(circular.getIdcircular()))
        getDb().delete("TBORDEMSERVICO", "id_os = ?", new String[]{String.valueOf(ordemservico.getId_os())});
    }
    public void removeAll(){
        getDb().delete("TBORDEMSERVICO", "", null);
    }
}
