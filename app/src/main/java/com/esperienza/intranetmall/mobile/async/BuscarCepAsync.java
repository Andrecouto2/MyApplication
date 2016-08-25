package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.esperienza.intranetmall.mobile.entidade.Endereco;
import com.esperienza.intranetmall.mobile.webservices.BuscarCepWs;


public class BuscarCepAsync  extends AsyncTask<String, Integer,Endereco> {




    public interface Action{
        void preExecute();
        void getResult(Endereco result);
        void postExecute(Endereco result);


    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;


    public BuscarCepAsync(Context context, String cep, Action action){
        this.action = action;
        this.dialog = new ProgressDialog(context);
        this.dialog.setTitle("Alerta");
        this.dialog.setMessage("Aguarde! Buscando CEP.");
        this.dialog.setCancelable(false);

    }

    @Override
    protected void onPreExecute() {
        this.dialog.show();
        super.onPreExecute();
    }

    @Override
    protected Endereco doInBackground(String... params) {


        BuscarCepWs ws = new BuscarCepWs();
        ws.setCep(params[0]);

        return ws.getResult();

    }


    @Override
    protected void onPostExecute(Endereco result) {
        action.getResult(result);
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);
    }


}
