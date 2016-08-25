package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.dao.CircularDAO;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.LeitoresCircular;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.webservices.LeitoresCircularWs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 21/12/2015.
 */
public class LeitoresCircularAsync extends AsyncTask<String, Integer,List<LeitoresCircular>> {

    public interface Action{
        void preExecute();
        //void getResult(List<Cliente> result);
        void postExecute(List<LeitoresCircular> result);


    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;


    public LeitoresCircularAsync(Context context, Action action){
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
    protected List<LeitoresCircular> doInBackground(String... params) {
        LeitoresCircularWs leitoresCircularWs = new LeitoresCircularWs();
        ArrayList<List<LeitoresCircular>> result= new ArrayList<>();
        ArrayList<LeitoresCircular> retorno= new ArrayList<>();
        try
        {
            CircularDAO circularDAO = new CircularDAO();
            List<Circular> l = circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
            for(int i=0;i<l.size();i++)
            {
                leitoresCircularWs.setIdShop(String.valueOf(AppHelper.getIdShop()));
                leitoresCircularWs.setIdCircular(l.get(i).getIdcircular());


                result.add(leitoresCircularWs.getResult());
            }
            for(int i=0;i<result.size();i++)
            {
                for(int j=0;j<result.get(i).size();j++)
                {
                    retorno.add(result.get(i).get(j));
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return retorno;
    }
    @Override
    protected void onPostExecute(List<LeitoresCircular> result) {
        //action.getResult(result);
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);

    }

}
