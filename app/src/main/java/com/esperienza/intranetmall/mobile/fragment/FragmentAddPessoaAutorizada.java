package com.esperienza.intranetmall.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Util;

/**
 * Created by ThinkPad on 22/02/2016.
 */
public class FragmentAddPessoaAutorizada extends Fragment{

    private ScrollView scrollviewaddpa;
    private EditText edttextnome;
    private EditText edttextrg;
    private EditText edttextempresa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_add_pessoa_autorizada, container, false);

        scrollviewaddpa = (ScrollView) rootview.findViewById(R.id.scrollViewAddPA);
        //Util.setInsets(getActivity(),scrollviewaddpa);

        edttextnome = (EditText) rootview.findViewById(R.id.nome);
        edttextrg = (EditText) rootview.findViewById(R.id.rg);
        edttextempresa = (EditText) rootview.findViewById(R.id.empresa);

        setHasOptionsMenu(true);
        //AKI

        return rootview;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menufnc, menu);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                Salvar();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Salvar()
    {
        try
        {
            PessoasAutorizadasOS pa = new PessoasAutorizadasOS();
            pa.setNome(edttextnome.getText().toString());
            pa.setRg(edttextrg.getText().toString());
            pa.setEmpresa(edttextempresa.getText().toString());
            pa.setId(PessoasAutorizadasOSDAO.getNewInstance().getMax(AppHelper.getIdShop())+1);
            pa.setIdshop(AppHelper.getIdShop());
            pa.setIduser(AppHelper.getUsuario().getIduser());
            pa.setIdos(0);
            PessoasAutorizadasOSDAO.getNewInstance().save(pa);

            Toast.makeText(getActivity(),"Salvo com sucesso!",Toast.LENGTH_SHORT).show();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentPessoasAutorizadas fragment = new FragmentPessoasAutorizadas();
            transaction.replace(R.id.pessoasautorizadas, fragment);
            transaction.commit();
        }
        catch (Exception e)
        {
            Log.e("inMall", "ERRO SALVAR PESSOAS AUT" + e.getMessage());
        }
    }

}
