package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.webservices.ListaShoppingWs;

import java.util.List;

/**
 * Created by BONSUCESSO on 17/11/2015.
 */
public class BuscarListaShopAsync extends AsyncTask<Void, Integer,List<Cliente>> {

    public interface Action{
        void preExecute();
        //void getResult(List<Cliente> result);
        void postExecute(List<Cliente> result);

    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;


    public BuscarListaShopAsync(Context context, Action action){
        this.action = action;
        //this.dialog = new ProgressDialog(context);
        this.context = context;
        //this.dialog = new ProgressDialog(context);
        //this.dialog.setTitle("Alerta");
        //this.dialog.setMessage("Aguarde! Buscando.");
        //this.dialog.setCancelable(false);

    }

    @Override
    protected void onPreExecute() {
//        this.dialog.show();
        super.onPreExecute();
    }

    @Override
    protected List<Cliente> doInBackground(Void... params) {


        ListaShoppingWs ws = new ListaShoppingWs();



        return ws.getResult();

    }


    @Override
    protected void onPostExecute(List<Cliente> result) {
        //action.getResult(result);
        super.onPostExecute(result);
        action.postExecute(result);
        //this.dialog.dismiss();

    }

}
