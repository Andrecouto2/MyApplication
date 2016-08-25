package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.LeitoresCircular;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 21/12/2015.
 */
public class LeitoresCircularDAO extends DAO {

    private final String DQL_GET_LEITORESCIRCULAR = "SELECT * FROM TBLEITORESCIRCULAR WHERE iduser = ? and idcircular = ? and idusermobile = ? and idshop = ?";
    private final String DQL_LISTA_LEITORESCIRCULARMAX = "SELECT MAX(idleitorescircular) as c FROM TBLEITORESCIRCULAR WHERE idusermobile = ? and idshop = ?";
    private final String DQL_LISTA_LEITORESPORCIRCULAR = "SELECT * FROM TBLEITORESCIRCULAR WHERE idcircular = ? and idusermobile = ? and idshop = ?";


    public static LeitoresCircularDAO getNewInstance(){
        return new LeitoresCircularDAO();
    }
    public int getmax(Integer idusermobile,Integer idshop)
    {
        Cursor c = getDb().rawQuery(DQL_LISTA_LEITORESCIRCULARMAX, new String[]{idusermobile.toString(),idshop.toString()});
        int retorno=0;
        try{
            if (c.getCount() > 0) {
                c.moveToNext();
                retorno = c.getInt(c.getColumnIndex("c"));
            }
        }finally {
            c.close();
        }
        return retorno;
    }

    public LeitoresCircular getLeitorCircular(Integer iduser,Integer idcircular,Integer idusermobile,Integer idshop){
        LeitoresCircular l = null;
        Cursor c = getDb().rawQuery(DQL_GET_LEITORESCIRCULAR, new String[]{iduser.toString(),idcircular.toString(),idusermobile.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                l = new LeitoresCircular();

                //l.setIdleitorcircular(c.getInt(c.getColumnIndex("idleitorescircular")));
                l.setNome(c.getString(c.getColumnIndex("nome")));
                l.setIduser(c.getInt(c.getColumnIndex("iduser")));
                l.setIdcircular(c.getInt(c.getColumnIndex("idcircular")));
                //.setRetorno(c.getInt(c.getColumnIndex("retorno")));
                l.setEmpresa(c.getString(c.getColumnIndex("empresa")));
                l.setData_acessou(c.getString(c.getColumnIndex("data_acessou")) != null ? DateHelper.toDate(c.getString(c.getColumnIndex("data_acessou"))) : null);
                l.setIdusermobile(c.getInt(c.getColumnIndex("idusermobile")));
                l.setIdshop(c.getInt(c.getColumnIndex("idshop")));

            }
        }
            finally{
            c.close();
        }

        return l;
    }

    public List<LeitoresCircular> listLeitoresPorCircular(Integer idcircular,Integer idusermobile,Integer idshop ){
        List<LeitoresCircular> l = new ArrayList<>();
        Cursor c = getDb().rawQuery(DQL_LISTA_LEITORESPORCIRCULAR , new String[]{idcircular.toString(),idusermobile.toString(),idshop.toString()});
        try {

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    l.add(getLeitorCircular(c.getInt(c.getColumnIndex("iduser")),idcircular,idusermobile,idshop));
                }
            }
        }
        finally {
            c.close();
        }

        return l;
    }
    public void save(LeitoresCircular leitor,Integer idusermobile,Integer idshop) {

        LeitoresCircular old = getLeitorCircular(leitor.getIduser(),leitor.getIdcircular(),idusermobile,idshop);
        ContentValues vl = new ContentValues();
        //vl.put("idleitorescircular", leitor.getIdleitorcircular());
        vl.put("iduser",leitor.getIduser());
        vl.put("empresa",leitor.getEmpresa());
        vl.put("nome",leitor.getNome());
        vl.put("idcircular",leitor.getIdcircular());
        vl.put("data_acessou ", DateHelper.toString(leitor.getData_acessou()));
        vl.put("idusermobile",leitor.getIdusermobile());
        vl.put("idshop",leitor.getIdshop());

        if(old == null){
            vl.put("iduser", leitor.getIduser());
            getDb().insert("TBLEITORESCIRCULAR","", vl);
        }else{
            getDb().update("TBLEITORESCIRCULAR", vl, "iduser = ? and idcircular = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(leitor.getIduser()),String.valueOf(leitor.getIdcircular()),String.valueOf(leitor.getIdusermobile()),String.valueOf(leitor.getIdshop())});
        }

    }
    public void remove(LeitoresCircular leitor){
        //if(valid(circular.getIdcircular()))
        getDb().delete("TBLEITORESCIRCULAR", "iduser = ? and idcircular = ? and idusermobile = ? and idshop = ?", new String[]{String.valueOf(leitor.getIduser()),String.valueOf(leitor.getIdcircular()),String.valueOf(leitor.getIdusermobile()),String.valueOf(leitor.getIdshop())});
    }
    public void removeAll(){
        getDb().delete("TBLEITORESCIRCULAR", null, null);
    }
}
