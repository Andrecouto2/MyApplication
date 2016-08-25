package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.esperienza.intranetmall.mobile.entidade.Ordem;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.fragment.FragmentAberturaOrdemServicoSequencia;
import com.esperienza.intranetmall.mobile.webservices.CadastraOrdemServicoWs;

/**
 * Created by ThinkPad on 08/03/2016.
 */
public class CadastraOrdemServicoAsync extends AsyncTask<Void, Void,OrdemServico> {

    public interface Action{
        void preExecute();
        //void getResult(String result);
        void postExecute(OrdemServico result);
    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;
    private Ordem ordem;

    public CadastraOrdemServicoAsync(Context context,Ordem ordem ,Action action)
    {
        this.context=context;
        this.action=action;
        this.dialog = new ProgressDialog (context);
        this.ordem=ordem;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Ordem de Serviço");
        this.dialog.setMessage("Cadastrando ordem de serviço...");
        this.dialog.setCancelable(true);
        this.dialog.setIndeterminate(false);

        this.dialog.show();
    }

    @Override
    protected OrdemServico doInBackground(Void... params) {

        CadastraOrdemServicoWs  cadosws = new CadastraOrdemServicoWs();
        cadosws.addcomplexobject(ordem);
        return cadosws.getResult();
    }
    @Override
    protected void onPostExecute(OrdemServico result) {
        super.onPostExecute(result);
        action.postExecute(result);
        this.dialog.dismiss();

    }
}
