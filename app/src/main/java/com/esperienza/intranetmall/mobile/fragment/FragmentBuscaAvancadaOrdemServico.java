package com.esperienza.intranetmall.mobile.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.DetalheOSActivity;
import com.esperienza.intranetmall.mobile.activity.PesquisaAvancadaActivity;
import com.esperienza.intranetmall.mobile.dao.TipoServicoDAO;
import com.esperienza.intranetmall.mobile.entidade.EntidadeBuscaAvancadaOs;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 13/07/2016.
 */
public class FragmentBuscaAvancadaOrdemServico extends Fragment {

    private LinearLayout linearservicos;
    private LinearLayout linearrelatorio;
    private EntidadeBuscaAvancadaOs ebos= new EntidadeBuscaAvancadaOs();
    private CheckBox cbtodos;
    private CheckBox cbemaprovacao;
    private CheckBox cbautorizado;
    private CheckBox cbnaoautorizado;
    private CheckBox cbemexecucao;
    private CheckBox cbconcluido;
    private CheckBox cbexpirado;
    private TextView tviewdatainicio;
    private TextView tviewdatafinal;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_busca_avancada_os, container, false);

        linearservicos = (LinearLayout) rootview.findViewById(R.id.linearservicos);
        linearrelatorio = (LinearLayout) rootview.findViewById(R.id.linearrelatorio);
        //cboxdefault = (CheckBox) rootview.findViewById(R.id.cboxdefault);
        cbtodos = (CheckBox) rootview.findViewById(R.id.cboxstatustodos);
        cbautorizado = (CheckBox) rootview.findViewById(R.id.cboxstatusautorizado);
        cbemaprovacao = (CheckBox) rootview.findViewById(R.id.cboxstatusemaprovacao);
        cbnaoautorizado = (CheckBox) rootview.findViewById(R.id.cboxstatusnaoautorizado);
        cbemaprovacao = (CheckBox) rootview.findViewById(R.id.cboxstatusemaprovacao);
        cbemexecucao = (CheckBox) rootview.findViewById(R.id.cboxstatusemexecucao);
        cbconcluido = (CheckBox) rootview.findViewById(R.id.cboxstatusconcluido);
        cbexpirado = (CheckBox) rootview.findViewById(R.id.cboxstatusexpirado);
        tviewdatainicio = (TextView) rootview.findViewById(R.id.txt_data_inicial);
        tviewdatafinal = (TextView) rootview.findViewById(R.id.txt_data_final);




        buildLinearServicos();
        events();

        return rootview;
    }
    public void buildLinearServicos()
    {
        List<TipoServico> lts = TipoServicoDAO.getNewInstance().getListaTipoServico(AppHelper.getIdShop());
        TipoServico tp = new TipoServico();
        tp.setIdtipo(0);
        tp.setDescricao("Todos");
        lts.add(0,tp);
        int tam=lts.size();

        int dpValue = 16; // margin in dips
        float d = getActivity().getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(margin,0,0,0);
        /*CheckBox cboxdefault= new CheckBox(getActivity());
        cboxdefault.setId(101010);
        cboxdefault.setText("Todos");
        cboxdefault.setLayoutParams(llp);*/
        /*cboxdefault.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    limparseleciona(true);
                }
                else
                {
                    limparseleciona(false);
                }
            }
        });*/
        //linearservicos.addView(cboxdefault);
        for(int i=0;i<tam;i++)
        {
            final CheckBox cbox = new CheckBox(getActivity());
            cbox.setId(lts.get(i).getIdtipo());
            cbox.setText(lts.get(i).getDescricao().toString());
            cbox.setLayoutParams(llp);
            cbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if ( ((CheckBox)v).isChecked() ) {

                        if(!ebos.getListatiposervico().contains(new Integer(cbox.getId())))
                        {
                            ebos.getListatiposervico().add(new Integer(cbox.getId()));
                        }
                    }
                    else
                    {
                        if(ebos.getListatiposervico().contains(new Integer(cbox.getId())))
                        {
                            ebos.getListatiposervico().remove(new Integer(cbox.getId()));
                        }
                    }
                }
            });
            if(i==0)
            {
                cbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if ( ((CheckBox)v).isChecked() ) {
                        limparseleciona(true);

                    }
                    else
                    {
                        limparseleciona(false);

                    }
                }
            });
            }


            linearservicos.addView(cbox);

        }
    }
    public void limparseleciona(Boolean checkec)
    {
        int count = linearservicos.getChildCount();
        View v = null;
        for(int i=0; i<count; i++) {
            v = linearservicos.getChildAt(i);
            CheckBox cb=null;
            try
            {
                cb = (CheckBox) v;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if(cb!=null)
            {
                if(checkec)
                {
                    cb.setChecked(true);
                    if(!ebos.getListatiposervico().contains(new Integer(cb.getId()))&&cb.getId()!=0)
                    {
                        ebos.getListatiposervico().add(new Integer(cb.getId()));
                    }
                }
                else
                {
                    cb.setChecked(false);
                    if(ebos.getListatiposervico().contains(new Integer(cb.getId())))
                    {
                        ebos.getListatiposervico().remove(new Integer(cb.getId()));
                    }

                }
            }
        }
    }
    public void events()
    {
        cbtodos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    limparselecionadostatus(true);
                    if(!ebos.getListasstatus().contains(new Integer(1)))
                    {
                        ebos.getListasstatus().add(new Integer(1));
                    }
                    if(!ebos.getListasstatus().contains(new Integer(2)))
                    {
                        ebos.getListasstatus().add(new Integer(2));
                    }
                    if(!ebos.getListasstatus().contains(new Integer(3)))
                    {
                        ebos.getListasstatus().add(new Integer(3));
                    }
                    if(!ebos.getListasstatus().contains(new Integer(4)))
                    {
                        ebos.getListasstatus().add(new Integer(4));
                    }
                    if(!ebos.getListasstatus().contains(new Integer(5)))
                    {
                        ebos.getListasstatus().add(new Integer(5));
                    }
                    if(!ebos.getListasstatus().contains(new Integer(6)))
                    {
                        ebos.getListasstatus().add(new Integer(6));
                    }
                }
                else
                {
                    limparselecionadostatus(false);
                    ebos.getListasstatus().removeAll(ebos.getListasstatus());
                }
            }
        });
        cbconcluido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(5)))
                    {
                        ebos.getListasstatus().add(new Integer(5));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(5)))
                    {
                        ebos.getListasstatus().remove(new Integer(5));
                    }
                }
            }
        });
        cbemaprovacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(1)))
                    {
                        ebos.getListasstatus().add(new Integer(1));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(1)))
                    {
                        ebos.getListasstatus().remove(new Integer(1));
                    }
                }
            }
        });
        cbemexecucao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(4)))
                    {
                        ebos.getListasstatus().add(new Integer(4));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(4)))
                    {
                        ebos.getListasstatus().remove(new Integer(4));
                    }
                }
            }
        });
        cbnaoautorizado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(3)))
                    {
                        ebos.getListasstatus().add(new Integer(3));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(3)))
                    {
                        ebos.getListasstatus().remove(new Integer(3));
                    }
                }
            }
        });
        cbautorizado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(2)))
                    {
                        ebos.getListasstatus().add(new Integer(2));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(2)))
                    {
                        ebos.getListasstatus().remove(new Integer(2));
                    }
                }
            }
        });
        cbexpirado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( ((CheckBox)v).isChecked() ) {
                    if(!ebos.getListasstatus().contains(new Integer(6)))
                    {
                        ebos.getListasstatus().add(new Integer(6));
                    }
                }
                else
                {
                    if(ebos.getListasstatus().contains(new Integer(6)))
                    {
                        ebos.getListasstatus().remove(new Integer(6));
                    }
                }
            }
        });
        tviewdatainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     fromDatePickerDialog.show();
            }
        });
        tviewdatafinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     toDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

              if(!tviewdatafinal.getText().toString().equals("--"))
              {
                  if(DateHelper.pastlimitdate2(dateFormatter.format(newDate.getTime()),tviewdatafinal.getText().toString()))
                  {
                       Toast.makeText(getActivity(),"Data início maior do que data final",Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      tviewdatainicio.setText(dateFormatter.format(newDate.getTime()));
                      ebos.setDatainicial(DateHelper.toStringSQLServer(newDate.getTime()));
                  }
              }
              else
              {
                  tviewdatainicio.setText(dateFormatter.format(newDate.getTime()));
                  ebos.setDatainicial(DateHelper.toStringSQLServer(newDate.getTime()));
              }




            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.setTitle("Data Início");
        //set data minima



        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if(!tviewdatainicio.getText().toString().equals("--"))
                {
                    if(!DateHelper.pastlimitdate(dateFormatter.format(newDate.getTime()),tviewdatainicio.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Data final menor do que data início.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        tviewdatafinal.setText(dateFormatter.format(newDate.getTime()));
                        ebos.setDatafinal(DateHelper.toStringSQLServer(newDate.getTime()));
                    }
                }
                else
                {
                    tviewdatafinal.setText(dateFormatter.format(newDate.getTime()));
                    ebos.setDatafinal(DateHelper.toStringSQLServer(newDate.getTime()));
                }



            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.setTitle("Data Término");

        linearrelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ebos.setIdshop(AppHelper.getIdShop());
                ebos.setIduser(AppHelper.getUsuario().getIduser());
                if(ebos.getDatainicial()!=null&&ebos.getDatafinal()!=null)
                {
                    if(AppHelper.isInternetOnline())
                    {
                        Intent intent = new Intent(getContext(), PesquisaAvancadaActivity.class);
                        intent.putExtra("ebos", Parcels.wrap(ebos));
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Sem conexão com a internet.",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),"Selecione período para a pesquisa.",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public void limparselecionadostatus(Boolean checked)
    {
        if(checked)
        {
            //cbtodos.setChecked(true);
            cbautorizado.setChecked(true);
            cbexpirado.setChecked(true);
            cbconcluido.setChecked(true);
            cbemexecucao.setChecked(true);
            cbemaprovacao.setChecked(true);
            cbnaoautorizado.setChecked(true);
            cbconcluido.setChecked(true);


        }
        else
        {
            //cbtodos.setChecked(false);
            cbautorizado.setChecked(false);
            cbexpirado.setChecked(false);
            cbconcluido.setChecked(false);
            cbemexecucao.setChecked(false);
            cbemaprovacao.setChecked(false);
            cbnaoautorizado.setChecked(false);
            cbconcluido.setChecked(false);

        }
    }
}
