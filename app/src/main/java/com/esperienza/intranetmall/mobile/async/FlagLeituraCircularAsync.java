package com.esperienza.intranetmall.mobile.async;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.webservices.EnvioFuncionarioWs;
import com.esperienza.intranetmall.mobile.webservices.FlagLeituraCircularWs;

/**
 * Created by ThinkPad on 18/12/2015.
 */
public class FlagLeituraCircularAsync extends AsyncTask<String, Integer,String> {


public interface Action{
    void preExecute();
    //void getResult(String result);
    void postExecute(String result);

}

private Context context;
private Action action;
private ProgressDialog dialog;

    public FlagLeituraCircularAsync(Context context, FlagLeituraCircularAsync.Action action){
        this.context=context;
        this.action = action;
        this.dialog = new ProgressDialog(context);
        //this.dialog = new ProgressDialog(context);
        //this.dialog.setTitle("Alerta");
        //this.dialog.setMessage("Aguarde! Buscando.");
        //this.dialog.setCancelable(false);

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Circular");
        this.dialog.setMessage("Abrindo circular...");
        this.dialog.setCancelable(true);
        //this.dialog.setIndeterminate(false);


        this.dialog.show();
    }



    @Override
    protected String doInBackground(String... params) {

        String result=null;
        try {
            FlagLeituraCircularWs leituraCircularWs = new FlagLeituraCircularWs();
            leituraCircularWs.setIdUser(params[1]);
            leituraCircularWs.setIdShop(params[0]);
            leituraCircularWs.setCircular(params[2]);
            result=leituraCircularWs.getResult();

        }catch (Exception e)
        {
            Log.e("Erro envio fnc", "" + e.getMessage());
            return result;
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        //action.getResult(result);
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);
    }

}
