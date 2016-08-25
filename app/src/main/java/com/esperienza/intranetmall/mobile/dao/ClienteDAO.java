package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;

import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BONSUCESSO on 16/11/2015.
 */
public class ClienteDAO extends  DAO{

    private final String DQL_GET_CLIENTE = "SELECT * FROM TBCLIENTE WHERE idcliente = ?";
    private final String DQL_GET_CLIENTE_POR_NOME = "SELECT * FROM TBCLIENTE WHERE nome = ?";
    private final String DQL_LISTA_CLIENTE = "SELECT * FROM TBCLIENTE ";//WHERE ativo = 1
    private final String DQL_UPDATE_STATUS_SHOPS = "UPDATE TBCLIENTE SET ativo = 0";

    public static ClienteDAO getNewInstance(){
        return new ClienteDAO();
    }

    public void updateStatusShop()
    {
        Cursor c = getDb().rawQuery(DQL_UPDATE_STATUS_SHOPS, null);
        try
        {
             int i=c.getCount();
        }
        finally
        {
            c.close();
        }

    }

    public Cliente getCliente(Integer id){
        Cliente cl = null;
        try {
            Cursor c = getDb().rawQuery(DQL_GET_CLIENTE, new String[]{id.toString()});
            try {
                if (c.getCount() > 0) {
                    c.moveToNext();
                    cl = new Cliente();
                    cl.setIdcliente(c.getInt(c.getColumnIndex("idcliente")));
                    cl.setNome(c.getString(c.getColumnIndex("nome")));
                    cl.setImg_cliente(c.getString(c.getColumnIndex("img_cliente")));
                    cl.setLink_cliente(c.getString(c.getColumnIndex("link_cliente")));
                    cl.setAtivo_cracha(c.getInt(c.getColumnIndex("ativo_cracha")));
                    cl.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                    cl.setData_cliente(DateHelper.toDate(c.getString(c.getColumnIndex("data_criacao"))));

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                c.close();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return cl;
    }
    public Cliente getClientePorNome(String nome){
        Cliente cl = null;
        Cursor c = getDb().rawQuery(DQL_GET_CLIENTE_POR_NOME, new String[]{nome});
        try{
            if(c.getCount() > 0){
                c.moveToNext();
                cl = new Cliente();
                cl.setIdcliente(c.getInt(c.getColumnIndex("idcliente")));
                cl.setNome(c.getString(c.getColumnIndex("nome")));
                cl.setImg_cliente(c.getString(c.getColumnIndex("img_cliente")));
                cl.setLink_cliente(c.getString(c.getColumnIndex("link_cliente")));
                cl.setAtivo(c.getInt(c.getColumnIndex("ativo")));
                cl.setAtivo_cracha(c.getInt(c.getColumnIndex("ativo_cracha")));
                cl.setData_cliente(DateHelper.toDate(c.getString(c.getColumnIndex("data_criacao"))));

            }
        }finally {
            c.close();
        }


        return cl;
    }
    public List<Cliente> listCliente(){
        List<Cliente> l = new ArrayList<Cliente>();
        try {
            Cursor c = getDb().rawQuery(DQL_LISTA_CLIENTE, null);
            try {
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        l.add(getCliente(c.getInt(c.getColumnIndex("idcliente"))));
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
    public void save(Cliente cliente) {
        Cliente old = getCliente(cliente.getIdcliente());
        ContentValues vl = new ContentValues();
        vl.put("idcliente", cliente.getIdcliente());
        vl.put("nome",cliente.getNome());
        vl.put("img_cliente",cliente.getImg_cliente());
        vl.put("link_cliente",cliente.getLink_cliente());
        vl.put("ativo",cliente.getAtivo());
        vl.put("ativo_cracha",cliente.getAtivo_cracha());
        vl.put("data_criacao",DateHelper.toStringSQLServer(cliente.getData_cliente()));



        if(old == null){
            vl.put("idcliente", cliente.getIdcliente());
            getDb().insert("TBCLIENTE", "", vl);
        }else{
            getDb().update("TBCLIENTE", vl, "idcliente = ?", new String[]{String.valueOf(cliente.getIdcliente())});
        }

    }
    public void saveFromServer(Cliente cliente) {
        try {
            Cliente old = getCliente(cliente.getIdcliente());
            ContentValues vl = new ContentValues();
            vl.put("idcliente", cliente.getIdcliente());
            vl.put("nome", cliente.getNome());
            vl.put("ativo_cracha",cliente.getAtivo_cracha());
            vl.put("img_cliente",cliente.getImg_cliente());
            if (old == null) {
                vl.put("idcliente", cliente.getIdcliente());
                getDb().insert("TBCLIENTE", "", vl);
            } else {
                getDb().update("TBCLIENTE", vl, "idcliente = ?", new String[]{String.valueOf(cliente.getIdcliente())});
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void remove(Funcionario funcionario){
        //if(valid(circular.getIdcircular()))
        getDb().delete("TBCLIENTE", "idcliente = ?", new String[]{String.valueOf(funcionario.getIdfnc())});
    }
    public void removeAll(){
        getDb().delete("TBCLIENTE", "", null);
    }



}
