package com.esperienza.intranetmall.mobile.fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintJob;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;


import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.MenuActivity;
import com.esperienza.intranetmall.mobile.adapter.ListaFuncionarioAdapter;
import com.esperienza.intranetmall.mobile.async.BuscarListaFuncionarioAsync;
import com.esperienza.intranetmall.mobile.async.LoginAsync;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.logger.LogFragment;
import com.esperienza.intranetmall.mobile.logger.LogWrapper;
import com.esperienza.intranetmall.mobile.logger.MessageOnlyLogFilter;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.fragment.FragmentDadosFuncionario;

import java.util.AbstractCollection;
import java.util.List;
//import de.timroes.swipetodismiss.SwipeDismissList;

/**
 * Created by ThinkPad on 30/11/2015.
 */
public class ListaFuncionariosFragment extends FragmentActivityBase {





    public ListaFuncionariosFragment(){}




    /*public View onCreate(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_lista_funcionarios, container, false);

        lv_funcionarios = (ListView) rootview.findViewById(R.id.lv_fnc);
        txtViewFncCad = (TextView) rootview.findViewById(R.id.txtviewFncCad);
        lv_funcionarios.setSelector(R.drawable.wizard_button_press);

        registerForContextMenu(lv_funcionarios);
        return rootview;
    }*/
    public static final String TAG = "MainActivity";


    // Whether the Log Fragment is currently shown


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_funcionarios);



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setTitle("Lista de Funcionários");


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadfnc, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.action_cadastrofuncionario);//rever


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                supportInvalidateOptionsMenu();
                return true;
            /*case R.id.action_novofnc:
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();AQUIIII
                Bundle bundle = new Bundle();
                bundle.putInt("idFuncionario", 0);

                FragmentDadosFuncionario fragmentDadosFuncionario = new FragmentDadosFuncionario();
                fragmentDadosFuncionario.setArguments(bundle);
                //ft.replace(R.id.container, fragmentDadosFuncionario);

                // ft.commit();
                fragmentTransaction.add(R.id.container, fragmentDadosFuncionario);
                fragmentTransaction.commit();



                return true;
            case R.id.action_sync:
                if(AppHelper.isInternetOnline()) {
                    getListaFnc(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()));
                }else{
                    Toast.makeText(this,"Checar conexão com a internet.",Toast.LENGTH_SHORT).show();
                }
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        //Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        //LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                //.findFragmentById(R.id.log_fragment);
        //msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }

    public void getListaFnc(String idshop,String usuario){

        new BuscarListaFuncionarioAsync(ListaFuncionariosFragment.this, new BuscarListaFuncionarioAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(List<Funcionario> result) {
                if(result!=null)
                {
                    FuncionarioDAO fncdao = new FuncionarioDAO();
                    for(int i=0;i<result.size();i++)
                    {
                        fncdao.save(result.get(i),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());

                    }
                    startActivity(new Intent(ListaFuncionariosFragment.this, ListaFuncionariosFragment.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Não há funcionários cadastrados.",Toast.LENGTH_SHORT).show();
                }

            }




        }).execute(idshop,usuario);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_contexto_item_funcionario, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_editar:
                Log.e("","");
                break;
            case R.id.menu_enviar:
                Log.e("","");
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}

