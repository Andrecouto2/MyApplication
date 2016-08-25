package com.esperienza.intranetmall.mobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.async.BuscarCepAsync;
import com.esperienza.intranetmall.mobile.entidade.Endereco;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.Arrays;
import java.util.List;

/**
 * Created by ThinkPad on 07/12/2015.
 */
public class FragmentEnderecoFuncionario extends Fragment{

    private EditText edt_cep;
    private EditText edt_end;
    private EditText edt_numero;
    private EditText edt_complemento;
    private EditText edt_bairro;
    private EditText edt_municipio;
    private Spinner sp_uf;
    private String[] list_uf;
    private ImageView imgviewcep;
    private TextView txtviewerroruf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_enderecofuncionario, container, false);
        FragmentDadosFuncionario.viewend=rootview;

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        //ScrollView scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewEnderecoFnc);
        //scrollView.setClipToPadding(false);
        //setInsets(getActivity(), scrollView);

        edt_cep = (EditText) rootview.findViewById(R.id.edt_cep);
        edt_end = (EditText) rootview.findViewById(R.id.edt_endereco);
        edt_numero = (EditText)  rootview.findViewById(R.id.edt_num);
        edt_complemento = (EditText) rootview.findViewById(R.id.edt_complemento);
        edt_bairro = (EditText) rootview.findViewById(R.id.edt_bairro);
        edt_municipio = (EditText) rootview.findViewById(R.id.edt_municipio);
        sp_uf = (Spinner) rootview.findViewById(R.id.sp_uf_endereco);
        imgviewcep = (ImageView) rootview.findViewById(R.id.imgbtn_buscar_cep);
        list_uf = getResources().getStringArray(R.array.ufs);
        txtviewerroruf = (TextView) rootview.findViewById(R.id.tvInvisibleErrorUf);

        if(FragmentDadosFuncionario.funcionario.getStatusEnvio()>0)
        {
            preencheTela();
        }
        else
        {
            List<View> form_elements = rootview.getTouchables();
            for (View element : form_elements){
                if (element instanceof EditText){
                    ((EditText) element).setText("");
                } else if (element instanceof Spinner){
                    ((Spinner) element).setSelection(0);
                }
                // and so forth...
            }
        }

        setListener();

        return rootview;
    }
    private void setListener()
    {
        // Listeners textviews
        edt_cep.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setCep(edt_cep.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_end.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setEndereco(edt_end.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_numero.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try{
                    FragmentDadosFuncionario.funcionario.setNumero(Integer.valueOf(edt_numero.getText().toString()));
                }catch (Exception e)
                {
                    Log.e("Fragment Endereço Funcionario"+getClass().toString(),"Erro conversao número");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_complemento.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setComplemento(edt_complemento.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_bairro.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setBairro(edt_bairro.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_municipio.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setCidade(edt_municipio.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        // Listeners spinners
        sp_uf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                FragmentDadosFuncionario.funcionario.setUf(sp_uf.getSelectedItem().toString());
                if(!sp_uf.getSelectedItem().toString().equals("Selecione"))
                    txtviewerroruf.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        imgviewcep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppHelper.isInternetOnline())
                {
                    if (!edt_cep.getText().toString().equals(""))
                    {
                        getCEP(edt_cep.getText().toString().replace("-",""));
                    } else
                    {
                        Toast.makeText(getActivity(), "Insira o cep.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void getCEP(String cep){

        new BuscarCepAsync(getActivity(),cep, new BuscarCepAsync.Action() {
            @Override
            public void preExecute() {
            }


            @Override
            public void postExecute(Endereco result) {

            }

            @Override
            public void getResult(Endereco result) {
                if (result!=null){
                    FragmentDadosFuncionario.funcionario.setCidade(result.getMunicipio());
                    FragmentDadosFuncionario.funcionario.setBairro(result.getBairro());
                    FragmentDadosFuncionario.funcionario.setUf(result.getUf());
                    FragmentDadosFuncionario.funcionario.setEndereco(result.getLogradouro());

                    preencheTela();

                }else {
                    Toast.makeText(getActivity(), "Cep não encontrado", Toast.LENGTH_LONG).show();
                }


            }




        }).execute(cep);

    }
    public void preencheTela()
    {
        sp_uf.setSelection(Arrays.asList(list_uf).indexOf(FragmentDadosFuncionario.funcionario.getUf() != null&&! FragmentDadosFuncionario.funcionario.getUf().toString().equals("anyType{}")? FragmentDadosFuncionario.funcionario.getUf().toString() : "Selecione"));
        edt_cep.setText(FragmentDadosFuncionario.funcionario.getCep() != null&&!FragmentDadosFuncionario.funcionario.getCep().toString().equals("anyType{}") ? FragmentDadosFuncionario.funcionario.getCep().toString() : "");
        edt_end.setText(FragmentDadosFuncionario.funcionario.getEndereco() != null&&!FragmentDadosFuncionario.funcionario.getEndereco().toString().equals("anyType{}") ? FragmentDadosFuncionario.funcionario.getEndereco().toString() : "");

        try{
            edt_numero.setText(FragmentDadosFuncionario.funcionario.getNumero() != 0? String.valueOf(FragmentDadosFuncionario.funcionario.getNumero()) : "");
        }
        catch (Exception e)
        {
            Log.e("Fragment Endereço Funcionario","Erro conversão de número");
        }

        edt_bairro.setText(FragmentDadosFuncionario.funcionario.getBairro() != null&&! FragmentDadosFuncionario.funcionario.getBairro().toString().equals("anyType{}")? FragmentDadosFuncionario.funcionario.getBairro().toString() : "");
        edt_complemento.setText(FragmentDadosFuncionario.funcionario.getComplemento() != null&&!FragmentDadosFuncionario.funcionario.getComplemento().toString().equals("anyType{}") ? FragmentDadosFuncionario.funcionario.getComplemento().toString() : "");
        edt_municipio.setText(FragmentDadosFuncionario.funcionario.getCidade() != null&&!FragmentDadosFuncionario.funcionario.getCidade().toString().equals("anyType{}") ? FragmentDadosFuncionario.funcionario.getCidade().toString() : "");


    }
    public static void setInsets(Activity context, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true)*2, config.getPixelInsetRight(), config.getPixelInsetBottom());
    }
}
