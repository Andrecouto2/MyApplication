package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.webservices.CadastraDispositivoWs;

/**
 * Created by ThinkPad on 28/12/2015.
 */
public class CadastraDispositivoAsync extends AsyncTask<String, Integer,Dispositivo> {


    public interface Action{
        void preExecute();
        //void getResult(String result);
        void postExecute(Dispositivo result);

    }

    private Context context;
    private Action action;
    private ProgressBar dialog;

    public CadastraDispositivoAsync(Context context, CadastraDispositivoAsync.Action action){
        this.context=context;
        this.action = action;
        this.dialog = new ProgressBar(context);
        //this.dialog.findViewById(R.id.progress);
        //this.dialog = new ProgressDialog(context);
        //this.dialog.setTitle("Alerta");
        //this.dialog.setMessage("Aguarde! Buscando.");
        //this.dialog.setCancelable(false);

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.dialog.setTitle("Dispositivo");
        //this.dialog.setMessage("Cadastrando dispositivo...");
        //this.dialog.setCancelable(true);
        //this.dialog.setIndeterminate(false);

         this.dialog.setVisibility(View.VISIBLE);

        //this.dialog.show();
    }

    @Override
    protected Dispositivo doInBackground(String... params) {
        Dispositivo result=null;
        try {
            CadastraDispositivoWs cadDispws = new CadastraDispositivoWs();

            cadDispws.setIdShopping(params[0]);
            cadDispws.setUsuario(params[1]);
            cadDispws.setSenha(params[2]);
            cadDispws.setIdDev(params[3]);

            result = new Dispositivo();
            result = cadDispws.getResult();


        }catch (Exception e)
        {
            Log.e("Erro cad disp", "" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Dispositivo result) {
        //action.getResult(result);
        action.postExecute(result);
        //this.dialog.dismiss();

        super.onPostExecute(result);
    }


}
