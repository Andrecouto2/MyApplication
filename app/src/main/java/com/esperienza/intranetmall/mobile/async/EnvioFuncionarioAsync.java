package com.esperienza.intranetmall.mobile.async;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.webservices.EnvioFuncionarioWs;

import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by ThinkPad on 14/12/2015.
 */
public class EnvioFuncionarioAsync extends AsyncTask<String, Integer,String> {

    public AsyncTask<String, Integer, String> executeStart(String... params){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            return executePostHoneycomb(params);
        }else{
            return super.execute(params);
        }
    }




    public interface Action{
        void preExecute();
        //void getResult(String result);
        void postExecute(String result);

    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;

    public EnvioFuncionarioAsync(Context context, EnvioFuncionarioAsync.Action action){
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
        this.dialog.setTitle("Funcionários");
        this.dialog.setMessage("Enviando dados do funcionário...");
        this.dialog.setCancelable(true);
        //this.dialog.setIndeterminate(false);


        this.dialog.show();
    }



    @Override
    protected String doInBackground(String... params) {

        String result=null;
        try {
            EnvioFuncionarioWs envioFncws = new EnvioFuncionarioWs();
            envioFncws.setIdShopping(params[0]);
            envioFncws.setIdUser(params[1]);
            envioFncws.setIdCad(params[2]);
            FuncionarioDAO fncdao = new FuncionarioDAO();
            Funcionario fnc = fncdao.getFuncionario(Integer.valueOf(params[2]), Integer.valueOf(params[1]), AppHelper.getIdShop());
            if (fnc.getStatusEnvio() == 1) {
                envioFncws.setIdCad("0");
            }
            envioFncws.setStatus_cad(String.valueOf(fnc.getStatus()));
            envioFncws.setDataAdm(DateHelper.toString(fnc.getData_admissao()));
            envioFncws.setNomeLojista(fnc.getNome_lojista());
            envioFncws.setDataDem(DateHelper.toString(fnc.getData_demissao()));
            envioFncws.setCargoLojista(fnc.getCargo_lojista());
            envioFncws.setTelefone(fnc.getTelefone());
            envioFncws.setRg(fnc.getRg());
            envioFncws.setCpf(fnc.getCpf());
            envioFncws.setFiliacaoPai(fnc.getFiliacao_pai());
            envioFncws.setDatanasc(DateHelper.toString(fnc.getDatanasc()));
            envioFncws.setFiliacao_mae(fnc.getFiliacao_mae());
            envioFncws.setNaturalidade(fnc.getNaturalidade());
            envioFncws.setEndereco(fnc.getEndereco());
            envioFncws.setNumero(fnc.getNumero()!=0?String.valueOf(fnc.getNumero()):"");
            envioFncws.setComplemento(fnc.getComplemento());
            envioFncws.setBairro(fnc.getBairro());
            envioFncws.setCep(fnc.getCep());
            envioFncws.setCidade(fnc.getCidade());
            envioFncws.setUf(fnc.getUf());
            envioFncws.setSexo(fnc.getSexo());
            envioFncws.setValidade("01/06/2016");//Rever
            envioFncws.setModelo(fnc.getModelo());
            envioFncws.setMarca(fnc.getMarca());
            envioFncws.setCor(fnc.getCor());
            envioFncws.setPlaca(fnc.getPlaca());
            envioFncws.setImagem(fnc.getImagem());

            result = envioFncws.getResult();


        }catch (Exception e)
        {
            Log.e("Erro envio fnc",""+e.getMessage());
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
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private AsyncTask<String, Integer, String> executePostHoneycomb(String... params){
        return super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }
}
