package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoDAO;
import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.webservices.CadastraObsOsWs;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ThinkPad on 26/01/2016.
 */
public class CadastraObsOsAsync extends AsyncTask<String, Void,ObservacaoOS> {

    public interface Action{
        void preExecute();
        //void getResult(String result);
        void postExecute(ObservacaoOS result);

    }

    private Context context;
    private Action action;
    private ProgressDialog dialog;
    private View rootView;
    public int checkapr;

    public CadastraObsOsAsync(Context context,View rootView, CadastraObsOsAsync.Action action){
        this.context=context;
        this.rootView=rootView;
        this.action = action;
        this.dialog = new ProgressDialog (context);
        //this.dialog.findViewById(R.id.progress);
        //this.dialog = new ProgressDialog(context);
        //this.dialog.setTitle("Alerta");
        //this.dialog.setMessage("Aguarde! Buscando.");
        //this.dialog.setCancelable(false);

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Ordem de serviço");
        this.dialog.setMessage("Aguarde...");
        this.dialog.setCancelable(true);
        this.dialog.setIndeterminate(false);



        this.dialog.show();
    }
    @Override
    protected ObservacaoOS doInBackground(String... params) {

        CadastraObsOsWs cadastraObsOsWs = new CadastraObsOsWs();
        cadastraObsOsWs.setIdShopping(params[0]);
        cadastraObsOsWs.setIdUser(params[1]);
        cadastraObsOsWs.setIdOs(params[2]);
        cadastraObsOsWs.setObs(params[3]);
        cadastraObsOsWs.setAprovacao(params[4]);
        checkapr=Integer.parseInt(params[4]);

        return cadastraObsOsWs.getResult();
    }
    @Override
    protected void onPostExecute(ObservacaoOS result)
    {


        if(result!=null&&result.getIdcomentario()>0)
        {
            TableLayout tableCO = (TableLayout) rootView.findViewById(R.id.tableLayoutCO);
            final TableRow row = new TableRow(context);
            TableRow.LayoutParams llp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
            row.setLayoutParams(llp);
            row.setBackgroundColor(context.getResources().getColor(R.color.LIGHT_GRAY));
            TableRow.LayoutParams ltv = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            ltv.setMargins(40, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
            TextView tv = new TextView(context);
            tv.setLayoutParams(ltv);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setPadding(5, 5, 5, 5);
            String text="  " + DateHelper.toString(result.getDatacad()).substring(0, 10) + " às ";
            text=text+ result.getHoracad().substring(0, 5) + "\r\n  ";
            text=text+ AppHelper.getUsuario().getNomeresponsavel() + " - ";
            text=text+AppHelper.getUsuario().getEmpresa();
            tv.setText(text);
            row.addView(tv);

            final TableRow row2 = new TableRow(context);
            TableRow.LayoutParams llp2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
            row2.setLayoutParams(llp2);
            TextView tv2 = new TextView(context);
            tv2.setLayoutParams(ltv);
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv2.setPadding(5, 5, 5, 5);
            tv2.setText("  " + result.getObservacoes());
            row2.addView(tv2);

            tableCO.addView(row);
            tableCO.addView(row2);

            //aprovadores
            if (checkapr>0)
            {
                TableLayout tableAPR = (TableLayout) rootView.findViewById(R.id.tableLayoutApr);
                tableAPR.removeAllViews();
                AprovadoresOS apr = AprovadoresOSDAO.getNewInstance().getAprovadoresOS(result.getIduser(),result.getIdos(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                apr.setAcao(checkapr);
                AprovadoresOSDAO.getNewInstance().save(apr);


                buildtableAprovadores(AprovadoresOSDAO.getNewInstance().listAprovadoresOS(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop(),result.getIdos()),tableAPR);
            }
            // quadro status
            TextView textViewStatus = (TextView) rootView.findViewById(R.id.tstatus);
            CardView cardstatus = (CardView) rootView.findViewById(R.id.cardstatus);
            ImageView imgviewstatus = (ImageView) rootView.findViewById(R.id.img_status_detalhe);
            switch (Integer.parseInt(result.getUsuario().getTelefone1()))
            {
                case 1:
                    textViewStatus.setText("Em Aprovação");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#FFFFCC"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_ffffcc);
                    break;
                case 2:
                    textViewStatus.setText("Autorizado");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#B2E0FF"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_b2e0ff);
                    break;
                case 3:
                    textViewStatus.setText("Não Autorizado");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#FFCCCC"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_ffcccc);
                    break;
                case 4:
                    textViewStatus.setText("Em execução");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#CCCCCC"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_cccccc);
                    break;
                case 5:
                    textViewStatus.setText("Concluído");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#C1E0D1"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_c1e0d1);
                    break;
                case 6:
                    textViewStatus.setText("Expirado");
                    //cardstatus.setCardBackgroundColor(Color.parseColor("#FFCC99"));
                    //cardstatus.setMaxCardElevation(0.0f);
                    //cardstatus.setRadius(5.0f);
                    imgviewstatus.setImageResource(R.drawable.status_os_ffcc99);
                    break;
            }







        }
        action.postExecute(result);
        this.dialog.dismiss();
        super.onPostExecute(result);

    }
    public void buildtableAprovadores(List<AprovadoresOS> lapr,TableLayout tableAPR) {
        int count = lapr.size();

        boolean isaprovador = false;
        for (int i = 0; i < lapr.size(); i++) {
            if (lapr.get(i).getAcao() == 1) {
                isaprovador = true;
            }
        }
        if (count > 0) {
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int k = 0; k < 2; k++) {

                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setPadding(5, 5, 5, 5);
                //tv.setGravity(Gravity.CENTER);
                if (k == 0) {
                    tv.setText("Responsável");
                } else if (k == 1) {
                    tv.setText("Aprovação");
                }


                row.addView(tv);
            }
            tableAPR.addView(row);
        }
        //primeira linha
        for (int i = 0; i < count; i++) {

            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if (i % 2 == 0) {
                row.setBackgroundColor(context.getResources().getColor(R.color.LIGHT_GRAY));
            }

            for (int j = 0; j < 2; j++) {


                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setPadding(5, 5, 5, 5);
                //tv.setGravity(Gravity.CENTER);
                if (lapr.get(i).getAlcadas() != 1) {
                    if (j == 0) {
                        tv.setText(lapr.get(i).getNome());
                    } else if (j == 1) {
                        if (lapr.get(i).getAcao() < 3) {
                            tv.setText(getAcao(lapr.get(i).getAcao()));
                        } else {
                            //createRadioButton(row);
                        }

                    }
                    if (j == 0 || lapr.get(i).getAcao() < 3)
                        row.addView(tv);
                } else {
                    if (isaprovador) {
                        if (lapr.get(i).getAcao() == 1) {
                            if (j == 0) {
                                tv.setText(lapr.get(i).getNome());
                            } else if (j == 1) {
                                if (lapr.get(i).getAcao() < 3) {
                                    tv.setText(getAcao(lapr.get(i).getAcao()));
                                } else {
                                    //createRadioButton(row);
                                }

                            }
                            if (j == 0 || lapr.get(i).getAcao() < 3)
                                row.addView(tv);
                        }
                    } else {

                        if (j == 0) {

                            tv.setText(lapr.get(i).getNome());
                        } else if (j == 1) {
                            if (lapr.get(i).getAcao() < 3) {
                                tv.setText(getAcao(lapr.get(i).getAcao()));
                            } else {
                                //createRadioButton(row);
                            }

                        }
                        if (j == 0 || lapr.get(i).getAcao() < 3)
                            row.addView(tv);

                    }
                }
            }
            tableAPR.addView(row);
        }
    }
    public String getAcao(int acao)
    {
        String retorna="";
        switch (acao)
        {
            case 0:
                retorna="Aguardando Aprovação";
                break;
            case 1:
                retorna="Aprovado";
                break;
            case 2:
                retorna="Reprovado";
                break;

        }
        return retorna;
    }

}
