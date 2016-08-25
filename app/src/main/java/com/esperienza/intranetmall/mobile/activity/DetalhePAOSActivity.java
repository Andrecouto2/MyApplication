package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.fragment.FragmentDetalheOrdemServico;
import com.esperienza.intranetmall.mobile.fragment.FragmentDetalheOrdemServicoPA;

import org.parceler.Parcels;

/**
 * Created by ThinkPad on 18/07/2016.
 */
public class DetalhePAOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalheospa);

        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaros2);

        OrdemServico os=  Parcels.unwrap(getIntent().getParcelableExtra("os"));

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("O.S: " + os.getId_os());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            FragmentDetalheOrdemServicoPA frag = new  FragmentDetalheOrdemServicoPA();
            frag.setArguments(getIntent().getExtras());
            // Adiciona o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.DetalheOSFragment2,frag).commit();
        }



    }
    @Override
    public void onResume(){
        super.onResume();
    }
    /*@Override
    public void onBackPressed()
    {
        super.onBackPressed();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        finish();
    }*/

}
