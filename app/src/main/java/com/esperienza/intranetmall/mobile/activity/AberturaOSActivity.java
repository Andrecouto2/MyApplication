package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.ListaPessoasAutorizadasAdapter;
import com.esperienza.intranetmall.mobile.fragment.FragmentAberturaOrdemServico;
import com.esperienza.intranetmall.mobile.fragment.FragmentAberturaOrdemServicoSequencia;

/**
 * Created by ThinkPad on 03/02/2016.
 */

public class AberturaOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_os);




        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaros);



        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setTitle("O.S: " + os.getId_os());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }


        if(savedInstanceState == null) {
            // Cria o fragment com o mesmo Bundle (args) da intent
            FragmentAberturaOrdemServico frag = new  FragmentAberturaOrdemServico();
            frag.setArguments(getIntent().getExtras());
            // Adiciona o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.AberturaFragment,frag).commit();
        }



    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(FragmentAberturaOrdemServicoSequencia.os!=null)
        {
            if(FragmentAberturaOrdemServicoSequencia.os.getPessoas()!=null)
                FragmentAberturaOrdemServicoSequencia.os.getPessoas().clear();
        }
        if(ListaPessoasAutorizadasAdapter.copypositionArray!=null)
        ListaPessoasAutorizadasAdapter.copypositionArray.clear();
        if(ArquivosActivity.mDataSet!=null)
            ArquivosActivity.mDataSet.clear();
    }

}
