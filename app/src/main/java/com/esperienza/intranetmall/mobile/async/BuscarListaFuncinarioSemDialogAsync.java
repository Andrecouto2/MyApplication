package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.webservices.ListaFuncionariosWs;

import java.util.List;

/**
 * Created by ThinkPad on 04/04/2016.
 */
public class BuscarListaFuncinarioSemDialogAsync extends AsyncTask<String, Integer,List<Funcionario>> {

    public interface Action{
        void preExecute();
        //void getResult(List<Cliente> result);
        void postExecute(List<Funcionario> result);


    }

    private Context context;
    private Action action;
   // private ProgressDialog dialog;


    public BuscarListaFuncinarioSemDialogAsync(Context context, Action action){
        this.action = action;
        //this.dialog = new ProgressDialog(context);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.dialog.setTitle("Funcionários");
        //this.dialog.setMessage("Buscando lista de funcionários...");
        //this.dialog.setCancelable(true);
        //this.dialog.setIndeterminate(false);


        //this.dialog.show();
    }

    @Override
    protected List<Funcionario> doInBackground(String... params) {


        ListaFuncionariosWs lfnc = new ListaFuncionariosWs();
        lfnc.setIdShop(params[1]);
        lfnc.setIdUser(params[0]);

        return lfnc.getResult();

    }


    @Override
    protected void onPostExecute(List<Funcionario> result) {
        //action.getResult(result);
        super.onPostExecute(result);
        action.postExecute(result);
        //this.dialog.dismiss();

    }
}
