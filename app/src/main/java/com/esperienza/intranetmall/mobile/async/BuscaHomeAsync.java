package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.webservices.BuscaDadosHomeWs;

/**
 * Created by ThinkPad on 04/01/2016.
 */
public class BuscaHomeAsync extends AsyncTask<String, Integer,Home> {


    public interface Action {
        void preExecute();

        //void getResult(List<Cliente> result);
        void postExecute(Home result);
    }

        private Context context;
        private Action action;
        private ProgressDialog dialog;


        public BuscaHomeAsync(Context context, Action action){
            this.action = action;
            this.dialog = new ProgressDialog(context);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setTitle("Sistema");
            this.dialog.setMessage("Buscando dados do sistema...");
            this.dialog.setCancelable(true);



            this.dialog.show();
        }



    @Override
    protected Home doInBackground(String... params) {

        BuscaDadosHomeWs buscaDadosHomeWs = new BuscaDadosHomeWs();
        buscaDadosHomeWs.setIdShopping(params[0]);
        buscaDadosHomeWs.setUsuario(params[1]);
        buscaDadosHomeWs.setSenha(params[2]);

        return buscaDadosHomeWs.getResult();
    }
    @Override
    protected void onPostExecute(Home result) {
        //action.getResult(result);
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);
    }
}
