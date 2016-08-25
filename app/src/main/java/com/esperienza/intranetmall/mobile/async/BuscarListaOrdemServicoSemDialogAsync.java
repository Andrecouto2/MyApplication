package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.EntidadeRetornoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.webservices.ListaOrdemServicoUsuarioWs;

import java.util.List;

/**
 * Created by ThinkPad on 18/01/2016.
 */
public class BuscarListaOrdemServicoSemDialogAsync extends AsyncTask<String, Integer,EntidadeRetornoOS> {

    public interface Action{
        void preExecute();
        //void getResult(List<Cliente> result);
        void postExecute(EntidadeRetornoOS result);
    }

    private Context context;
    private Action action;
    //private ProgressDialog dialog;

    public BuscarListaOrdemServicoSemDialogAsync(Context context, Action action){
        this.context=context;
        this.action = action;
        //this.dialog = new ProgressDialog(context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.dialog.setTitle("Ordem de Serviço");
        //this.dialog.setMessage("Buscando ordem de serviço...");
        //this.dialog.setCancelable(true);
        //this.dialog.setIndeterminate(false);


        //this.dialog.show();
    }
    @Override
    protected EntidadeRetornoOS doInBackground(String... params) {

        ListaOrdemServicoUsuarioWs osws = new ListaOrdemServicoUsuarioWs();
        osws.setIdShop(params[0]);
        osws.setIdUser(params[1]);
        osws.setTipo(params[2]);

        return osws.getResult();
    }
    @Override
    protected void onPostExecute(EntidadeRetornoOS result) {
        //action.getResult(result);
        action.postExecute(result);
        //this.dialog.dismiss();
        super.onPostExecute(result);
    }
}
