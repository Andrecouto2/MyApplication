package com.esperienza.intranetmall.mobile.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.fonts.CpfEditText;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.ValidaCPF;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 07/12/2015.
 */
public class FragmentDadosBasicosFuncionario extends Fragment {

    private EditText edt_cpf;
    private EditText edt_nome;
    private EditText edt_doc_numero;
    private EditText edt_data_nasc;
    private EditText edt_naturalidade;
    private EditText edt_nomemae;
    private EditText edt_nomepai;
    private EditText edt_cargo;
    private EditText edt_telefone;
    private EditText edt_data_adm;
    private EditText edt_data_dem;
    private ImageButton imgbtn_buscacpf;
    private ImageButton imgbtn_datanasc;
    private ImageButton imgbtn_dataadm;
    private ImageButton imgbtn_datadem;
    private Spinner spn_sexo;
    private boolean verificarequest;
    private  TextView txtviewerrosexo;
    private String[] list_sexo;
    private String vDate;

    public FragmentDadosBasicosFuncionario(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_dadosbasicosfuncionarios, container, false);
        FragmentDadosFuncionario.viewdb=rootview;

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        //ScrollView scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewDadosBasicos);
        //scrollView.setClipToPadding(false);
        //setInsets(getActivity(), scrollView);

        edt_cpf = (com.esperienza.intranetmall.mobile.fonts.CpfEditText) rootview.findViewById(R.id.edt_cpf);
        edt_nome = (EditText) rootview.findViewById(R.id.edt_nome);
        edt_doc_numero = (EditText) rootview.findViewById(R.id.edt_doc_numero);
        edt_data_nasc = (EditText) rootview.findViewById(R.id.edt_data_nasc);
        edt_naturalidade = (EditText) rootview.findViewById(R.id.edt_naturalidade);
        edt_nomemae = (EditText) rootview.findViewById(R.id.edt_nomemae);
        edt_nomepai = (EditText) rootview.findViewById(R.id.edt_nomepai);
        edt_cargo = (EditText) rootview.findViewById(R.id.edt_cargo);
        edt_telefone = (EditText) rootview.findViewById(R.id.edt_telefone);
        edt_data_adm = (EditText) rootview.findViewById(R.id.edt_data_adm);
        edt_data_dem = (EditText) rootview.findViewById(R.id.edt_data_dem);
        imgbtn_datanasc = (ImageButton) rootview.findViewById(R.id.imageButtonNasc);
        imgbtn_dataadm = (ImageButton) rootview.findViewById(R.id.imageButtonAdm);
        imgbtn_datadem = (ImageButton) rootview.findViewById(R.id.imageButtondem);
        //imgbtn_buscacpf = (ImageButton) rootview.findViewById(R.id.btn_buscar_cpf);
        spn_sexo = (Spinner) rootview.findViewById(R.id.sp_sexo);
        txtviewerrosexo = (TextView) rootview.findViewById(R.id.tvInvisibleErrorSexo);
        list_sexo = getResources().getStringArray(R.array.list_sexo);



        setListener();
        if(FragmentDadosFuncionario.funcionario.getStatusEnvio()>0)
        {
            PreencheTela();
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

        return rootview;
    }
    private void mostraData(View v) {
        FragmentDataDialog date = new FragmentDataDialog();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        if(v.getId()==R.id.imageButtonNasc) vDate="nasc";
        if(v.getId()==R.id.imageButtonAdm) vDate="adm";
        if(v.getId()==R.id.imageButtondem) vDate="dem";
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String sDate=String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear + 1) + "/"
                    + String.valueOf(year);

            if (vDate=="nasc"){
                edt_data_nasc.setText(sDate);
                try {
                    FragmentDadosFuncionario.funcionario.setDatanasc(DateHelper.toDate(sDate));
                }catch(Exception e){
                    Log.e("INTRAMALL", "Erro ao converter edt_data_emissao " + edt_data_nasc.getText().toString());
                }
            }
            if (vDate=="adm"){
                edt_data_adm.setText(sDate);
                try {
                   FragmentDadosFuncionario.funcionario.setData_admissao(DateHelper.toDate(sDate));
                }catch (Exception e){
                    Log.e("INTRAMALL", "Erro ao converter edt_data_vencimento " + edt_data_adm.getText().toString());
                }

            }

            if (vDate=="dem"){
                edt_data_dem.setText(sDate);
                try {
                    FragmentDadosFuncionario.funcionario.setData_demissao(DateHelper.toDate(sDate));
                }catch(Exception e){
                    Log.e("INTRAMALL", "Erro ao converter edt_data_nascimento " + edt_data_dem.getText().toString());
                }

            }

        }
    };
    public static void setInsets(Activity context, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true)*2, config.getPixelInsetRight(), config.getPixelInsetBottom());
    }
    private void setListener()
    {
        // Listeners datas
        imgbtn_datanasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraData(v);
            }
        });

        imgbtn_dataadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraData(v);
            }
        });

        imgbtn_datadem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraData(v);
            }
        });
        // Listeners textviews
        edt_cpf.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setCpf(edt_cpf.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_nome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_cargo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_data_adm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_data_dem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_data_nasc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_doc_numero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_naturalidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_nomemae.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_nomepai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });
        edt_telefone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String aux = edt_cpf.getText().toString().replace(".", "").replace("-", "").replace(" ", "");
                if (aux.length() > 0) {
                    if (!ValidaCPF.ValidaCPF(edt_cpf.getText().toString())) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //edt_cpf.setError("CPF inválido.", getActivity().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        } else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){

                            //edt_cpf.setError("CPF inválido.", getActivity().getResources().getDrawable(R.drawable.icone_alerta_vermelho_app_on_48x48));
                            edt_cpf.setError("CPF inválido.");
                            edt_cpf.requestFocus();
                        }

                    } else {
                        edt_cpf.setError(null);
                    }
                }

            }


        });

        edt_nome.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setNome_lojista(edt_nome.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_doc_numero.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setRg(edt_doc_numero.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_data_nasc.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setDatanasc(DateHelper.toDate(edt_data_nasc.getText().toString()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_naturalidade.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setNaturalidade(edt_naturalidade.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_cargo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setCargo_lojista(edt_cargo.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_telefone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setTelefone(edt_telefone.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_data_adm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setData_admissao(DateHelper.toDate(edt_data_adm.getText().toString()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_data_dem.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setData_demissao(DateHelper.toDate(edt_data_dem.getText().toString()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edt_nomemae.addTextChangedListener(new TextWatcher() {

        public void afterTextChanged(Editable s) {

            FragmentDadosFuncionario.funcionario.setFiliacao_mae(edt_nomemae.getText().toString());
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        });
        edt_nomepai.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                FragmentDadosFuncionario.funcionario.setFiliacao_pai(edt_nomepai.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        // Listeners spinners
        spn_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                FragmentDadosFuncionario.funcionario.setSexo(spn_sexo.getSelectedItem().toString());
                if(!spn_sexo.getSelectedItem().toString().equals("Selecione"))
                    txtviewerrosexo.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


    }
    public void PreencheTela()
    {
        try {
            edt_data_nasc.setText(FragmentDadosFuncionario.funcionario.getDatanasc()!=null&&!FragmentDadosFuncionario.funcionario.getDatanasc().toString().equals("null")?DateHelper.toString(FragmentDadosFuncionario.funcionario.getDatanasc()):"");
        }catch(Exception e){
            Log.e("inMall", "Erro ao converter edt_data_nasc " + edt_data_nasc.getText().toString());
        }
        try {
            edt_data_adm.setText(FragmentDadosFuncionario.funcionario.getData_admissao()!=null&&!FragmentDadosFuncionario.funcionario.getData_admissao().toString().equals("null")?DateHelper.toString(FragmentDadosFuncionario.funcionario.getData_admissao()):"");
        }catch(Exception e){
            Log.e("inMall", "Erro ao converter edt_data_adm " + edt_data_adm.getText().toString());
        }
        try {
            edt_data_dem.setText(FragmentDadosFuncionario.funcionario.getData_demissao()!=null&&!FragmentDadosFuncionario.funcionario.getData_demissao().toString().equals("null")?DateHelper.toString(FragmentDadosFuncionario.funcionario.getData_demissao()):"");
        }catch(Exception e){
            Log.e("inMall", "Erro ao converter edt_data_dem " + edt_data_dem.getText().toString());
        }
        edt_cpf.setText(FragmentDadosFuncionario.funcionario.getCpf()!=null&&!FragmentDadosFuncionario.funcionario.getCpf().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getCpf().toString():"");
        edt_nome.setText(FragmentDadosFuncionario.funcionario.getNome_lojista()!=null&&!FragmentDadosFuncionario.funcionario.getNome_lojista().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getNome_lojista().toString():"");
        edt_doc_numero.setText(FragmentDadosFuncionario.funcionario.getRg()!=null&&!FragmentDadosFuncionario.funcionario.getRg().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getRg().toString():"");
        edt_naturalidade.setText(FragmentDadosFuncionario.funcionario.getNaturalidade()!=null&&!FragmentDadosFuncionario.funcionario.getNaturalidade().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getNaturalidade().toString():"");
        edt_nomemae.setText(FragmentDadosFuncionario.funcionario.getFiliacao_mae()!=null&&!FragmentDadosFuncionario.funcionario.getFiliacao_mae().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getFiliacao_mae().toString():"");
        edt_nomepai.setText(FragmentDadosFuncionario.funcionario.getFiliacao_pai()!=null&&!FragmentDadosFuncionario.funcionario.getFiliacao_pai().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getFiliacao_pai().toString():"");
        edt_cargo.setText(FragmentDadosFuncionario.funcionario.getCargo_lojista()!=null&&!FragmentDadosFuncionario.funcionario.getCargo_lojista().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getCargo_lojista().toString():"");
        edt_telefone.setText(FragmentDadosFuncionario.funcionario.getTelefone()!=null&&!FragmentDadosFuncionario.funcionario.getTelefone().toString().equals("anyType{}")?FragmentDadosFuncionario.funcionario.getTelefone().toString():"");
        spn_sexo.setSelection(Arrays.asList(list_sexo).indexOf(FragmentDadosFuncionario.funcionario.getSexo() != null&&! FragmentDadosFuncionario.funcionario.getSexo().toString().equals("anyType{}")? FragmentDadosFuncionario.funcionario.getSexo().toString() : "Selecione"));
    }

    }
