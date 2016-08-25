package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BONSUCESSO on 13/11/2015.
 */
public class FuncionarioDAO extends DAO{

          private final String DQL_GET_FUNCIONARIO = "SELECT * FROM TBFUNCIONARIO WHERE idfnc = ? and iduser =  ? and idshop = ? order by nome_lojista";
          private final String DQL_LISTA_FUNCIONARIO_STATUS = "SELECT * FROM TBFUNCIONARIO WHERE id_cracha_tipo = ? and iduser =  ? and idshop = ? order by nome_lojista";
          private final String DQL_LISTA_FUNCIONARIO = "SELECT * FROM TBFUNCIONARIO WHERE iduser =  ? and idshop = ? order by nome_lojista";
          private final String DQL_MAX_ID ="SELECT MAX(idfnc) as max FROM TBFUNCIONARIO WHERE iduser = ? and idshop = ?";
          private final String DQL_GET_FUNCIONARIOS_SHOP = "SELECT * FROM TBFUNCIONARIO WHERE idshop = ? order by nome_lojista";

    public static FuncionarioDAO getNewInstance(){
        return new FuncionarioDAO();
    }

    public Funcionario getFuncionario(Integer id,Integer iduser,Integer idshop){
        Funcionario f = null;
        Cursor c = getDb().rawQuery(DQL_GET_FUNCIONARIO, new String[]{id.toString(),iduser.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                f = new Funcionario();
                f.setIdfnc(c.getInt(c.getColumnIndex("idfnc")));
                f.setNome_lojista(c.getString(c.getColumnIndex("nome_lojista")));
                f.setRg(c.getString(c.getColumnIndex("rg")));
                f.setCpf(c.getString(c.getColumnIndex("cpf")));
                f.setDatanasc(DateHelper.toDate(c.getString(c.getColumnIndex("datanasc"))));
                f.setStatus(c.getInt(c.getColumnIndex("status")));
                f.setIduser(c.getInt(c.getColumnIndex("iduser")));
                f.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                f.setCargo_lojista(c.getString(c.getColumnIndex("cargo_lojista")));
                f.setDatacadastro(c.getString(c.getColumnIndex("datacad")) != null ? DateHelper.toDate(c.getString(c.getColumnIndex("datacad"))) : null);
                f.setCodfoto(c.getString(c.getColumnIndex("codfoto")));
                f.setData_demissao(c.getString(c.getColumnIndex("data_dem")) != null ? DateHelper.toDate(c.getString(c.getColumnIndex("data_dem"))) : null);
                f.setData_admissao(c.getString(c.getColumnIndex("data_adm")) != null ? DateHelper.toDate(c.getString(c.getColumnIndex("data_adm"))) : null);
                f.setTelefone(c.getString(c.getColumnIndex("telefone")));
                f.setFiliacao_mae(c.getString(c.getColumnIndex("filiacao_mae")));
                f.setFiliacao_pai(c.getString(c.getColumnIndex("filiacao_pai")));
                f.setNaturalidade(c.getString(c.getColumnIndex("naturalidade")));
                f.setEndereco(c.getString(c.getColumnIndex("endereco")));
                f.setNumero(c.getInt(c.getColumnIndex("numero")));
                f.setComplemento(c.getString(c.getColumnIndex("complemento")));
                f.setBairro(c.getString(c.getColumnIndex("bairro")));
                f.setCep(c.getString(c.getColumnIndex("cep")));
                f.setCidade(c.getString(c.getColumnIndex("cidade")));
                f.setUf(c.getString(c.getColumnIndex("uf")));
                f.setModelo(c.getString(c.getColumnIndex("modelo")));
                f.setCor(c.getString(c.getColumnIndex("cor")));
                f.setPlaca(c.getString(c.getColumnIndex("placa")));
                f.setMarca(c.getString(c.getColumnIndex("marca")));
                f.setSexo(c.getString(c.getColumnIndex("sexo")));
                f.setValidade(c.getString(c.getColumnIndex("validade")) != null ? DateHelper.toDate(c.getString(c.getColumnIndex("validade"))) : null);
                f.setId_cracha_tipo(c.getInt(c.getColumnIndex("id_cracha_tipo")));
                f.setDescricaoCracha(c.getString(c.getColumnIndex("descricao_cracha")));
                f.setStatusEnvio(c.getInt(c.getColumnIndex("statusEnvio")));
                f.setImagem(c.getString(c.getColumnIndex("imagem")));

            }
        }finally {
            c.close();
        }

        return f;
    }
    public List<Funcionario> listFuncionarioStatus(Integer idStatus,Integer iduser,Integer idshop){
        List<Funcionario> l = new ArrayList<Funcionario>();
        Cursor c = getDb().rawQuery(DQL_LISTA_FUNCIONARIO_STATUS , new String[]{idStatus.toString(),iduser.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    l.add(getFuncionario(c.getInt(c.getColumnIndex("idfnc")), iduser, idshop));
                }
            }
        }finally {
            c.close();
        }

        return l;
    }
    public List<Funcionario> listFuncionariosShop(Integer idshop){
        List<Funcionario> l = new ArrayList<Funcionario>();
        Cursor c = getDb().rawQuery(DQL_GET_FUNCIONARIOS_SHOP, new String[]{idshop.toString()});
        try {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    l.add(getFuncionario(c.getInt(c.getColumnIndex("idfnc")), c.getInt(c.getColumnIndex("iduser")), idshop));
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            c.close();
        }

        return l;
    }
    public List<Funcionario> listFuncionario(Integer iduser,Integer idshop){
        List<Funcionario> l = new ArrayList<Funcionario>();
        try {
            Cursor c = getDb().rawQuery(DQL_LISTA_FUNCIONARIO, new String[]{iduser.toString(), idshop.toString()});
            try {
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        l.add(getFuncionario(c.getInt(c.getColumnIndex("idfnc")), iduser, idshop));
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
    public int MaxIdFnc(Integer iduser,Integer idshop)
    {
        Cursor c = getDb().rawQuery(DQL_MAX_ID , new String[]{iduser.toString(),idshop.toString()});
        if(c.getCount() > 0)
        {
            int aux=0;
            while (c.moveToNext())
            {

                aux= c.getInt(c.getColumnIndex("max"));

            }
            return aux;
        }
        return 0;
    }


    public void save(Funcionario funcionario,Integer iduser,Integer idshop) {
        Funcionario old = getFuncionario(funcionario.getIdfnc(),iduser,idshop);
        ContentValues vl = new ContentValues();
        vl.put("idfnc", funcionario.getIdfnc());
        vl.put("nome_lojista",funcionario.getNome_lojista());
        vl.put("rg",funcionario.getRg());
        vl.put("cpf",funcionario.getCpf());
        vl.put("datanasc",DateHelper.toString(funcionario.getDatanasc()));
        vl.put("status",funcionario.getStatus());
        vl.put("iduser",funcionario.getIduser());
        if(funcionario.getIdshop()==0)
        {
            vl.put("idshop",idshop);
        }
        else
        {
            vl.put("idshop",funcionario.getIdshop());
        }
        vl.put("cargo_lojista",funcionario.getCargo_lojista());
        vl.put("datacad",DateHelper.toString(funcionario.getDatacadastro()));
        vl.put("codfoto",funcionario.getCodfoto());
        vl.put("data_dem",DateHelper.toString(funcionario.getData_demissao()));
        vl.put("data_adm",DateHelper.toString(funcionario.getData_admissao()));
        vl.put("telefone",funcionario.getTelefone());
        vl.put("filiacao_pai",funcionario.getFiliacao_pai());
        vl.put("filiacao_mae",funcionario.getFiliacao_mae());
        vl.put("naturalidade",funcionario.getNaturalidade());
        vl.put("endereco",funcionario.getEndereco());
        vl.put("numero",funcionario.getNumero());
        vl.put("complemento",funcionario.getComplemento());
        vl.put("bairro",funcionario.getBairro());
        vl.put("cep",funcionario.getCep());
        vl.put("cidade",funcionario.getCidade());
        vl.put("uf",funcionario.getUf());
        vl.put("modelo",funcionario.getModelo());
        vl.put("cor",funcionario.getCor());
        vl.put("placa",funcionario.getPlaca());
        vl.put("marca",funcionario.getMarca());
        vl.put("sexo",funcionario.getSexo());
        vl.put("validade",DateHelper.toString(funcionario.getValidade()));
        vl.put("id_cracha_tipo",funcionario.getId_cracha_tipo());
        vl.put("descricao_cracha",funcionario.getDescricaoCracha());
        vl.put("statusEnvio",funcionario.getStatusEnvio());
        vl.put("imagem",funcionario.getImagem());

        if(old == null){
            vl.put("idfnc", funcionario.getIdfnc());
            getDb().insert("TBFUNCIONARIO", null, vl);
        }else{
            getDb().update("TBFUNCIONARIO", vl, "idfnc = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(funcionario.getIdfnc()),iduser.toString(),idshop.toString()});
        }

    }
    public void save(Funcionario funcionario,Integer iduser,Integer idfncold,Integer idshop) {
        Funcionario old = getFuncionario(idfncold,iduser,idshop);
        ContentValues vl = new ContentValues();
        vl.put("idfnc", funcionario.getIdfnc());
        vl.put("nome_lojista",funcionario.getNome_lojista());
        vl.put("rg",funcionario.getRg());
        vl.put("cpf",funcionario.getCpf());
        vl.put("datanasc",DateHelper.toString(funcionario.getDatanasc()));
        vl.put("status",funcionario.getStatus());
        vl.put("iduser",funcionario.getIduser());
        if(funcionario.getIdshop()==0)
        {
            vl.put("idshop",idshop);
        }
        else {
            vl.put("idshop",funcionario.getIdshop());
        }

        vl.put("cargo_lojista",funcionario.getCargo_lojista());
        vl.put("datacad",DateHelper.toString(funcionario.getDatacadastro()));
        vl.put("codfoto",funcionario.getCodfoto());
        vl.put("data_dem",DateHelper.toString(funcionario.getData_demissao()));
        vl.put("data_adm",DateHelper.toString(funcionario.getData_admissao()));
        vl.put("telefone",funcionario.getTelefone());
        vl.put("filiacao_pai",funcionario.getFiliacao_pai());
        vl.put("filiacao_mae",funcionario.getFiliacao_mae());
        vl.put("naturalidade",funcionario.getNaturalidade());
        vl.put("endereco",funcionario.getEndereco());
        vl.put("numero",funcionario.getNumero());
        vl.put("complemento",funcionario.getComplemento());
        vl.put("bairro",funcionario.getBairro());
        vl.put("cep",funcionario.getCep());
        vl.put("cidade",funcionario.getCidade());
        vl.put("uf",funcionario.getUf());
        vl.put("modelo",funcionario.getModelo());
        vl.put("cor",funcionario.getCor());
        vl.put("placa",funcionario.getPlaca());
        vl.put("marca",funcionario.getMarca());
        vl.put("sexo",funcionario.getSexo());
        vl.put("validade",DateHelper.toString(funcionario.getValidade()));
        vl.put("id_cracha_tipo",funcionario.getId_cracha_tipo());
        vl.put("descricao_cracha",funcionario.getDescricaoCracha());
        vl.put("statusEnvio",funcionario.getStatusEnvio());
        vl.put("imagem",funcionario.getImagem());

        if(old == null){
            vl.put("idfnc", funcionario.getIdfnc());
            getDb().insert("TBFUNCIONARIO", null, vl);
        }else{
            getDb().update("TBFUNCIONARIO", vl, "idfnc = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(idfncold),iduser.toString(),idshop.toString()});
        }

    }
    public int remove(Funcionario funcionario,Integer iduser,Integer idshop){
        //if(valid(circular.getIdcircular()))
        return getDb().delete("TBFUNCIONARIO", "idfnc = ? and iduser = ? and idshop = ?", new String[]{String.valueOf(funcionario.getIdfnc()),iduser.toString(),idshop.toString()});
    }
    public void removeAll(){
        getDb().delete("TBFUNCIONARIO", "", null);
    }
}
