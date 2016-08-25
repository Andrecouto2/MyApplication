package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.fragment.FragmentAberturaOrdemServicoSequencia;
import com.esperienza.intranetmall.mobile.fragment.FragmentPessoasAutorizadas;

/**
 * Created by ThinkPad on 18/02/2016.
 */
public class PessoasAutorizadasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas_autorizadas);
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
            getSupportActionBar().setTitle("Pessoas Autorizadas");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
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
            FragmentPessoasAutorizadas frag = new FragmentPessoasAutorizadas();
            //frag.setArguments(getIntent().getExtras());
            // Adiciona o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.pessoasautorizadas,frag).commit();
        }



    }


}
