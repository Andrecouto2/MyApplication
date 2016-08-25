package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.EntidadeCircular;
import com.esperienza.intranetmall.mobile.webservices.ListaCircularLeituraWs;
import com.esperienza.intranetmall.mobile.webservices.ListaFuncionariosWs;

import java.util.List;

/**
 * Created by ThinkPad on 17/12/2015.
 */
public class BuscarListaCircularAsync extends AsyncTask<String, Integer,EntidadeCircular> {

    public interface Action{
        void preExecute();
        //void getResult(List<Cliente> result);
        void postExecute(EntidadeCircular result);


    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;


    public BuscarListaCircularAsync(Context context, Action action){
        this.context=context;
        this.action = action;
        this.dialog = new ProgressDialog(context);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Circular");
        this.dialog.setMessage("Buscando lista de circulares...");
        this.dialog.setCancelable(true);
        this.dialog.setIndeterminate(false);


        this.dialog.show();
    }

    @Override
    protected EntidadeCircular doInBackground(String... params) {


        ListaCircularLeituraWs lcws = new ListaCircularLeituraWs();
        lcws.setIdUser(params[1]);
        lcws.setIdShop(params[0]);
        //lcws.setLoad(Integer.parseInt(params[2]));

        return lcws.getResult();

    }


    @Override
    protected void onPostExecute(EntidadeCircular result) {
        //action.getResult(result);
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);
    }
}


