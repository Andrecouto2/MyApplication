package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

import static com.esperienza.intranetmall.mobile.util.DateHelper.parseHHMM;

/**
 * Created by BONSUCESSO on 12/11/2015.
 */
public class UsuarioDAO extends DAO {

    final String DQL_SELECT_USUARIO = "SELECT * FROM TBUSUARIO ";
    final String DQL_SELECT_USUARIOPORID = "SELECT * FROM TBUSUARIO WHERE iduser = ? and idshop = ?";
    final String DQL_SELECT_USUARIOPORLOGIN = "SELECT * FROM TBUSUARIO WHERE login = ? and senha = ?";
    final String DQL_SELECT_USUARIO_TIPO = "SELECT * FROM TBUSUARIO WHERE tipo = ? and idshop = ? ORDER BY nome ";
    final String DQL_SELECT_USUARIO_TIPO2="SELECT * FROM TBUSUARIO WHERE tipo = ? and idshop = ? ORDER BY empresax";
    public static UsuarioDAO getNewInstance(){
        return new UsuarioDAO();
    }

    public Usuario getUsuario(){
        Usuario u = null;
        Cursor c = getDb().rawQuery(DQL_SELECT_USUARIO, null);
        try{
            if(c.getCount() > 0){
                c.moveToNext();
                u = new Usuario();
                u.setIduser(c.getInt(c.getColumnIndex("iduser")));
                u.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                u.setEmpresa(c.getString(c.getColumnIndex("empresax")));
                u.setLuc(c.getString(c.getColumnIndex("luc")));
                u.setContrato(c.getString(c.getColumnIndex("contrato")));
                u.setLogin(c.getString(c.getColumnIndex("login")));
                u.setSenha(c.getString(c.getColumnIndex("senha")));
                u.setContadordeacesso(c.getInt(c.getColumnIndex("contadordeacesso")));
                u.setDataacesso(DateHelper.toDate(c.getString(c.getColumnIndex("dataacesso"))));
                //u.setHoraacesso();
                u.setAtivo_inativo(c.getInt(c.getColumnIndex("ativo_inativo")));
                u.setNomeresponsavel(c.getString(c.getColumnIndex("nome")));
                u.setTelefone1(c.getString(c.getColumnIndex("telefone")));
                u.setTelefone2(c.getString(c.getColumnIndex("telefone2")));
                u.setEmail(c.getString(c.getColumnIndex("email")));
                u.setRsocial(c.getString(c.getColumnIndex("email2")));
                u.setPiso(c.getString(c.getColumnIndex("piso")));
                u.setDepto(c.getString(c.getColumnIndex("depto")));
                u.setPrimeiroacesso(c.getInt(c.getColumnIndex("primeiroacesso")));
                u.setId_cracha_tipo(c.getInt(c.getColumnIndex("idcrachatipo")));
                u.setCodpessoa(c.getInt(c.getColumnIndex("codpessoa")));
                u.setImglogoshop(c.getString(c.getColumnIndex("imglogoshop")));
                u.setTipo(c.getInt(c.getColumnIndex("tipo")));
        }

        }finally {
            c.close();
        }
        return u;

    }
    public Usuario getUsuario(Integer iduser,Integer idshop){
        Usuario u = null;

        Cursor c = getDb().rawQuery(DQL_SELECT_USUARIOPORID, new String[]{iduser.toString(),idshop.toString()});
        try
        {

            if(c.getCount() > 0){
                c.moveToNext();
                u = new Usuario();
                u.setIduser(c.getInt(c.getColumnIndex("iduser")));
                u.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                u.setEmpresa(c.getString(c.getColumnIndex("empresax")));
                u.setLuc(c.getString(c.getColumnIndex("luc")));
                u.setContrato(c.getString(c.getColumnIndex("contrato")));
                u.setLogin(c.getString(c.getColumnIndex("login")));
                u.setSenha(c.getString(c.getColumnIndex("senha")));
                u.setContadordeacesso(c.getInt(c.getColumnIndex("contadordeacesso")));
                u.setDataacesso(DateHelper.toDate(c.getString(c.getColumnIndex("dataacesso"))));
                //u.setHoraacesso();
                u.setAtivo_inativo(c.getInt(c.getColumnIndex("ativo_inativo")));
                u.setNomeresponsavel(c.getString(c.getColumnIndex("nome")));
                u.setTelefone1(c.getString(c.getColumnIndex("telefone")));
                u.setTelefone2(c.getString(c.getColumnIndex("telefone2")));
                u.setEmail(c.getString(c.getColumnIndex("email")));
                u.setRsocial(c.getString(c.getColumnIndex("email2")));
                u.setPiso(c.getString(c.getColumnIndex("piso")));
                u.setDepto(c.getString(c.getColumnIndex("depto")));
                u.setPrimeiroacesso(c.getInt(c.getColumnIndex("primeiroacesso")));
                u.setId_cracha_tipo(c.getInt(c.getColumnIndex("idcrachatipo")));
                u.setCodpessoa(c.getInt(c.getColumnIndex("codpessoa")));
                u.setImglogoshop(c.getString(c.getColumnIndex("imglogoshop")));
                u.setTipo(c.getInt(c.getColumnIndex("tipo")));

            }




        }finally {
            c.close();
        }
        return u;

    }
    public List<Usuario> getListTipoUsuario(Integer tipo,Integer idshop){
        Usuario u = null;
        List<Usuario> luser = new ArrayList<>();
        if(tipo==1) {
            Cursor c = getDb().rawQuery(DQL_SELECT_USUARIO_TIPO, new String[]{tipo.toString(),idshop.toString()});
            try {


                while (c.moveToNext()) {
                    u = new Usuario();
                    u.setIduser(c.getInt(c.getColumnIndex("iduser")));
                    u.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                    u.setEmpresa(c.getString(c.getColumnIndex("empresax")));
                    u.setLuc(c.getString(c.getColumnIndex("luc")));
                    u.setContrato(c.getString(c.getColumnIndex("contrato")));
                    u.setLogin(c.getString(c.getColumnIndex("login")));
                    u.setSenha(c.getString(c.getColumnIndex("senha")));
                    u.setContadordeacesso(c.getInt(c.getColumnIndex("contadordeacesso")));
                    u.setDataacesso(DateHelper.toDate(c.getString(c.getColumnIndex("dataacesso"))));
                    //u.setHoraacesso();
                    u.setAtivo_inativo(c.getInt(c.getColumnIndex("ativo_inativo")));
                    u.setNomeresponsavel(c.getString(c.getColumnIndex("nome")));
                    u.setTelefone1(c.getString(c.getColumnIndex("telefone")));
                    u.setTelefone2(c.getString(c.getColumnIndex("telefone2")));
                    u.setEmail(c.getString(c.getColumnIndex("email")));
                    u.setRsocial(c.getString(c.getColumnIndex("email2")));
                    u.setPiso(c.getString(c.getColumnIndex("piso")));
                    u.setDepto(c.getString(c.getColumnIndex("depto")));
                    u.setPrimeiroacesso(c.getInt(c.getColumnIndex("primeiroacesso")));
                    u.setId_cracha_tipo(c.getInt(c.getColumnIndex("idcrachatipo")));
                    u.setCodpessoa(c.getInt(c.getColumnIndex("codpessoa")));
                    u.setImglogoshop(c.getString(c.getColumnIndex("imglogoshop")));
                    u.setTipo(c.getInt(c.getColumnIndex("tipo")));
                    luser.add(u);
                }


            } finally {
                c.close();
            }
        }else if(tipo==2)
        {
            Cursor c = getDb().rawQuery(DQL_SELECT_USUARIO_TIPO2, new String[]{tipo.toString(),idshop.toString()});
            try {


                while (c.moveToNext()) {
                    u = new Usuario();
                    u.setIduser(c.getInt(c.getColumnIndex("iduser")));
                    u.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                    u.setEmpresa(c.getString(c.getColumnIndex("empresax")));
                    u.setLuc(c.getString(c.getColumnIndex("luc")));
                    u.setContrato(c.getString(c.getColumnIndex("contrato")));
                    u.setLogin(c.getString(c.getColumnIndex("login")));
                    u.setSenha(c.getString(c.getColumnIndex("senha")));
                    u.setContadordeacesso(c.getInt(c.getColumnIndex("contadordeacesso")));
                    u.setDataacesso(DateHelper.toDate(c.getString(c.getColumnIndex("dataacesso"))));
                    //u.setHoraacesso();
                    u.setAtivo_inativo(c.getInt(c.getColumnIndex("ativo_inativo")));
                    u.setNomeresponsavel(c.getString(c.getColumnIndex("nome")));
                    u.setTelefone1(c.getString(c.getColumnIndex("telefone")));
                    u.setTelefone2(c.getString(c.getColumnIndex("telefone2")));
                    u.setEmail(c.getString(c.getColumnIndex("email")));
                    u.setRsocial(c.getString(c.getColumnIndex("email2")));
                    u.setPiso(c.getString(c.getColumnIndex("piso")));
                    u.setDepto(c.getString(c.getColumnIndex("depto")));
                    u.setPrimeiroacesso(c.getInt(c.getColumnIndex("primeiroacesso")));
                    u.setId_cracha_tipo(c.getInt(c.getColumnIndex("idcrachatipo")));
                    u.setCodpessoa(c.getInt(c.getColumnIndex("codpessoa")));
                    u.setImglogoshop(c.getString(c.getColumnIndex("imglogoshop")));
                    u.setTipo(c.getInt(c.getColumnIndex("tipo")));
                    luser.add(u);
                }


            } finally {
                c.close();
            }
        }
        return luser;

    }
    public void save(Usuario u){
        ContentValues vlusuario = new ContentValues();
        Usuario old = getUsuario(u.getIduser(),u.getIdshop());

        if(u.getIduser()!=0)
        {
            vlusuario.put("iduser",u.getIduser());
        }
        if(u.getIdshop()!=0)
        {
            vlusuario.put("idshop",u.getIdshop());
        }
        if(u.getEmpresa()!=null&&!u.getEmpresa().toString().equals("anyType{}"))
        {
            vlusuario.put("empresax",u.getEmpresa());
        }else if(old!=null&&old.getEmpresa()!=null&&!old.getEmpresa().toString().equals("anyType{}"))
        {
            vlusuario.put("empresax",old.getEmpresa());
        }
        if(u.getLogin()!=null)
        {
            vlusuario.put("login",u.getLogin());
        }
        else if(old!=null&&old.getLogin()!=null)
        {
            vlusuario.put("login",old.getLogin());
        }
        if(u.getLuc()!=null)
        {
            vlusuario.put("luc",u.getLuc());
        }
        else if(old!=null&&old.getLuc()!=null)
        {
            vlusuario.put("luc",old.getLuc());
        }

        vlusuario.put("contrato",u.getContrato());
        vlusuario.put("login",u.getLogin());
        vlusuario.put("senha",u.getSenha());
        vlusuario.put("contadordeacesso",u.getContadordeacesso());
        vlusuario.put("dataacesso", DateHelper.toStringSQLServer(u.getDataacesso()));
        vlusuario.put("ativo_inativo",u.getAtivo_inativo());
        vlusuario.put("nome",u.getNomeresponsavel());
        vlusuario.put("telefone",u.getTelefone1());
        vlusuario.put("telefone2",u.getTelefone2());
        vlusuario.put("email",u.getEmail());
        vlusuario.put("email2",u.getEmail2());
        vlusuario.put("piso",u.getPiso());
        vlusuario.put("depto",u.getDepto());
        vlusuario.put("primeiroacesso",u.getPrimeiroacesso());
        vlusuario.put("idcrachatipo",u.getId_cracha_tipo());
        vlusuario.put("codpessoa",u.getCodpessoa());
        if(u.getImglogoshop()!=null)
        {
            vlusuario.put("imglogoshop",u.getImglogoshop());
        }
        else if(old!=null&&old.getImglogoshop()!=null)
        {
            vlusuario.put("imglogoshop",old.getImglogoshop());
        }
        else
        {
            vlusuario.put("imglogoshop","");
        }

        if(u.getTipo()>0)
        {
            vlusuario.put("tipo",u.getTipo());
        }else if(old!=null&&old.getTipo()>0)
        {
            vlusuario.put("tipo",old.getTipo());
        }
        else
        {
            vlusuario.put("tipo",0);
        }





        if(old == null){
            vlusuario.put("iduser", u.getIduser());
            getDb().insert("TBUSUARIO", "", vlusuario);
        } else {
            getDb().update("TBUSUARIO", vlusuario, "iduser = ? and idshop = ?", new String[]{String.valueOf(u.getIduser()),String.valueOf(u.getIdshop())});
        }


    }
    public void saveOS(Usuario u){
        ContentValues vlusuario = new ContentValues();
        Usuario old = getUsuario(u.getIduser(),u.getIdshop());

        vlusuario.put("iduser",u.getIduser());
        vlusuario.put("idshop",u.getIdshop());
        if(u.getEmpresa()!=null&&!u.getEmpresa().equals("anyType{}"))
        {
            vlusuario.put("empresax",u.getEmpresa());
        }else if(old!=null&&old.getEmpresa()!=null)
        {
            vlusuario.put("empresax",old.getEmpresa());
        }
        vlusuario.put("nome",u.getNomeresponsavel());
        //vlusuario.put("email",u.getEmail());
        //vlusuario.put("telefone",u.getTelefone1());
        if(u.getTipo()>0)
        {
            vlusuario.put("tipo",u.getTipo());
        }else if(old!=null&&old.getTipo()>0)
        {
            vlusuario.put("tipo",old.getTipo());
        }
        else
        {
            vlusuario.put("tipo",0);
        }

        if(u.getLuc()!=null&&!u.getLuc().equals("anyType{}"))
        {
            vlusuario.put("luc",u.getLuc());
        }

        vlusuario.put("login",u.getLogin());

        Log.e("Teste", "ID: " + u.getIduser() + "Nome: " + u.getNomeresponsavel() + "Empresa: " + u.getEmpresa());

        if(old == null){
            vlusuario.put("iduser",u.getIduser());
            getDb().insert("TBUSUARIO","", vlusuario);
        }else{
            getDb().update("TBUSUARIO", vlusuario, "iduser = ? and idshop = ?",new String[]{String.valueOf(u.getIduser()),String.valueOf(u.getIdshop())});
        }


    }
    public void removeAll(){
        getDb().delete("TBUSUARIO","", null);
    }

}
