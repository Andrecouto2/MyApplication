package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.entidade.EntidadeBuscaAvancadaOs;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.Ordem;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.webservices.BuscaOsAvancadaWs;

import java.util.List;

/**
 * Created by ThinkPad on 18/07/2016.
 */
public class BuscarOrdemServicoPesquisaAvancadaAsync extends AsyncTask<Void, Void,List<OrdemServico>> {

    public interface Action{
        void preExecute();
        //void getResult(String result);
        void postExecute(List<OrdemServico> result);
    }
    private Context context;
    private Action action;
    private ProgressDialog dialog;
    private EntidadeBuscaAvancadaOs ebos;
    public BuscarOrdemServicoPesquisaAvancadaAsync(Context context,EntidadeBuscaAvancadaOs ebos,Action action)
    {
        this.context=context;
        this.action=action;
        this.dialog = new ProgressDialog (context);
        this.ebos=ebos;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Ordem de Serviço");
        this.dialog.setMessage("Pesquisando ordem de serviço...");
        this.dialog.setCancelable(true);
        this.dialog.setIndeterminate(false);

        this.dialog.show();
    }
    @Override
    protected List<OrdemServico> doInBackground(Void... params) {

        BuscaOsAvancadaWs ws = new BuscaOsAvancadaWs();
        ws.setIdUser(String.valueOf(ebos.getIduser()));
        ws.setIdShop(String.valueOf(ebos.getIdshop()));
        ws.setDataInicial(ebos.getDatainicial());
        ws.setDataFinal(ebos.getDatafinal());
        ws.addcomplexobjectPA(ebos);

        return ws.getResult();
    }
    @Override
    protected void onPostExecute(List<OrdemServico> result) {
        super.onPostExecute(result);
        action.postExecute(result);
        this.dialog.dismiss();

    }
}
