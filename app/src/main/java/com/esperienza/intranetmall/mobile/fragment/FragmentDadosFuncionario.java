package com.esperienza.intranetmall.mobile.fragment;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.CamposObrigatoriosFncDAO;
import com.esperienza.intranetmall.mobile.adapter.ViewPageFuncionalidadeAdapter;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.entidade.CamposObrigatoriosFnc;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Prefs;

import org.parceler.Parcels;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ThinkPad on 07/12/2015.
 */
public class FragmentDadosFuncionario extends Fragment{

    private static Context context;
    private ViewPager mViewPager;
    private ViewPageFuncionalidadeAdapter mTabsAdapter;
    public static Funcionario funcionario;
    public static View viewdb;
    public static View viewend;
    public static View viewfoto;
    private int IdFuncionario;


    public FragmentDadosFuncionario(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.funcionario_tabs,
                container, false);
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        mTabsAdapter = new ViewPageFuncionalidadeAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mTabsAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        int cor = ContextCompat.getColor(getContext(), R.color.grey_300);
        int cor2 = ContextCompat.getColor(getContext(), R.color.paletaazulclaro);
        int cor3 = ContextCompat.getColor(getContext(), R.color.white);
        // Cor branca no texto (o fundo azul foi definido no layout)
        tabLayout.setTabTextColors(cor, cor2);
        tabLayout.setBackgroundColor(cor3);

        tabLayout.setupWithViewPager(mViewPager);
        int tabIdx = Prefs.getInteger(getContext(), "tabIdx");
        mViewPager.setCurrentItem(tabIdx);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Salva o índice da página/tab selecionada
                Prefs.setInteger(getContext(), "tabIdx", mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        context=getActivity();
        Bundle bundle = this.getArguments();
        IdFuncionario = bundle.getInt("idFuncionario", 0);
        FuncionarioDAO fncdao = new FuncionarioDAO();

        if(IdFuncionario>0)
        {
            funcionario = Parcels.unwrap(bundle.getParcelable("fnc"));
        }
        else
        {
            funcionario = new Funcionario();
            funcionario.setStatusEnvio(0);
            funcionario.setStatus(1);
            funcionario.setIdfnc(fncdao.MaxIdFnc(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()) + 1);
            funcionario.setIduser(AppHelper.getUsuario().getIduser());
            funcionario.setIdshop(AppHelper.getIdShop());
            funcionario.setDatacadastro(DateHelper.toDate(DateHelper.getDateTime()));


        }




        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onDetach() {
        //   Toast.makeText(getActivity(), "Saindo da tela", Toast.LENGTH_SHORT).show();
        super.onDetach();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menufnc, menu);


    }
    public  void Salvar()
    {
        FuncionarioDAO fncdao = new FuncionarioDAO();

        if(funcionario.getSexo()!=null&&funcionario.getSexo().toString().equals("Selecione"))
        {
            funcionario.setSexo(null);
        }
        if(funcionario.getUf()!=null&&funcionario.getUf().toString().equals("Selecione"))
        {
              funcionario.setUf(null);
        }
        if (funcionario.getCpf() != null) {
            try {
                if (funcionario.getStatusEnvio()==0)
                {
                    funcionario.setStatusEnvio(1);
                }else if(funcionario.getStatusEnvio()==3)
                {
                    funcionario.setStatusEnvio(2);
                }
                if(AppHelper.getUsuario().getTipo()==1)
                {
                    fncdao.save(funcionario,funcionario.getIduser(),funcionario.getIdfnc(),AppHelper.getIdShop());
                }
                else
                {
                    fncdao.save(funcionario,AppHelper.getUsuario().getIduser(),funcionario.getIdfnc(),AppHelper.getIdShop());
                }

                Toast();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                RecyclerViewFragment fragment = new RecyclerViewFragment();
                FragmentListaFuncionarioAdm frag = new FragmentListaFuncionarioAdm();
                if(AppHelper.getUsuario().getTipo()==1)
                {
                    transaction.replace(R.id.container, frag);
                }
                else
                {
                    transaction.replace(R.id.container, fragment);
                }

                transaction.commit();
            } catch (Exception e) {
                Log.e("inMall", "ERRO SALVAR CC FRAGDADOSFUNCIONARIo" + e.getMessage());
            }
        }



    }
    public static void Toast(){
        Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                if(checkObrigatorioEditText())
                {
                    Salvar();
                }
                else
                {
                    Toast.makeText(getActivity(),"Formulário possui campos obrigatórios.",Toast.LENGTH_LONG).show();
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static boolean checkObrigatorioEditText()
    {
        boolean retorno = true;
        if(viewdb!=null&&viewend!=null&&viewfoto!=null) {

            com.esperienza.intranetmall.mobile.fonts.CpfEditText edt_cpf = (com.esperienza.intranetmall.mobile.fonts.CpfEditText) viewdb.findViewById(R.id.edt_cpf);
            EditText edt_nome = (EditText) viewdb.findViewById(R.id.edt_nome);
            EditText edt_doc_numero = (EditText) viewdb.findViewById(R.id.edt_doc_numero);
            EditText edt_data_nasc = (EditText) viewdb.findViewById(R.id.edt_data_nasc);
            EditText edt_naturalidade = (EditText) viewdb.findViewById(R.id.edt_naturalidade);
            EditText edt_nomemae = (EditText) viewdb.findViewById(R.id.edt_nomemae);
            EditText edt_nomepai = (EditText) viewdb.findViewById(R.id.edt_nomepai);
            EditText edt_cargo = (EditText) viewdb.findViewById(R.id.edt_cargo);
            EditText edt_telefone = (EditText) viewdb.findViewById(R.id.edt_telefone);
            EditText edt_data_adm = (EditText) viewdb.findViewById(R.id.edt_data_adm);
            EditText edt_data_dem = (EditText) viewdb.findViewById(R.id.edt_data_dem);
            EditText edt_cep = (EditText) viewend.findViewById(R.id.edt_cep);
            EditText edt_end = (EditText) viewend.findViewById(R.id.edt_endereco);
            EditText edt_numero = (EditText) viewend.findViewById(R.id.edt_num);
            EditText edt_complemento = (EditText) viewend.findViewById(R.id.edt_complemento);
            EditText edt_bairro = (EditText) viewend.findViewById(R.id.edt_bairro);
            EditText edt_municipio = (EditText) viewend.findViewById(R.id.edt_municipio);
            TextView txtviewerrosexo = (TextView) viewdb.findViewById(R.id.tvInvisibleErrorSexo);
            TextView txtviewerroruf = (TextView) viewend.findViewById(R.id.tvInvisibleErrorUf);

            Spinner spn_sexo = (Spinner) viewdb.findViewById(R.id.sp_sexo);
            Spinner sp_uf = (Spinner) viewend.findViewById(R.id.sp_uf_endereco);

            ImageView imgviewfoto = (ImageView) viewfoto.findViewById(R.id.img_fnc);


            List<CamposObrigatoriosFnc> listcof = CamposObrigatoriosFncDAO.getNewInstance().getCamposObrigatorio(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop());

            if (funcionario.getNome_lojista() == null || funcionario.getNome_lojista().isEmpty()) {
                if (listcof.get(0).getObrigatorio() == 1) {
                    edt_nome.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }

            if (funcionario.getCargo_lojista() == null || funcionario.getCargo_lojista().isEmpty()) {
                if (listcof.get(1).getObrigatorio() == 1) {
                    edt_cargo.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }

            if (funcionario.getRg() == null || funcionario.getRg().isEmpty()) {
                if (listcof.get(2).getObrigatorio() == 1) {
                    edt_doc_numero.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getCpf() == null || funcionario.getCpf().replace(" ", "").replace(".", "").replace("-", "").isEmpty()) {
                if (listcof.get(3).getObrigatorio() == 1) {
                    edt_cpf.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }

            if (funcionario.getDatanasc() == null || funcionario.getDatanasc().toString().isEmpty()) {
                if (listcof.get(4).getObrigatorio() == 1) {
                    edt_data_nasc.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getNaturalidade() == null || funcionario.getNaturalidade().isEmpty()) {
                if (listcof.get(5).getObrigatorio() == 1) {
                    edt_naturalidade.setError("Campo Obrigatório.");
                    retorno = false;
                }

            }
            if (funcionario.getEndereco() == null || funcionario.getEndereco().isEmpty()) {
                if (listcof.get(6).getObrigatorio() == 1) {
                    edt_end.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getNumero() == 0) {
                if (listcof.get(7).getObrigatorio() == 1) {
                    edt_numero.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getBairro() == null || funcionario.getBairro().isEmpty()) {
                if (listcof.get(8).getObrigatorio() == 1) {
                    edt_bairro.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getCep() == null || funcionario.getCep().isEmpty()) {
                if (listcof.get(9).getObrigatorio() == 1) {
                    edt_cep.setError("Campo Obrigatório.");
                    retorno = false;
                }

            }
            if (funcionario.getCidade() == null || funcionario.getCidade().isEmpty()) {
                if (listcof.get(10).getObrigatorio() == 1) {
                    edt_municipio.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }

            if (funcionario.getData_admissao() == null || funcionario.getData_admissao().toString().isEmpty()) {
                if (listcof.get(11).getObrigatorio() == 1) {
                    edt_data_adm.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getTelefone() == null || funcionario.getTelefone().isEmpty()) {
                if (listcof.get(12).getObrigatorio() == 1) {
                    edt_telefone.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getFiliacao_pai() == null || funcionario.getFiliacao_pai().isEmpty()) {
                if (listcof.get(13).getObrigatorio() == 1) {
                    edt_nomepai.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getFiliacao_mae() == null || funcionario.getFiliacao_mae().isEmpty()) {
                if (listcof.get(14).getObrigatorio() == 1) {
                    edt_nomemae.setError("Campo Obrigatório.");
                    retorno = false;
                }

            }
            if (funcionario.getData_demissao() == null || funcionario.getData_demissao().toString().isEmpty()) {
                if (listcof.get(17).getObrigatorio() == 1) {
                    edt_data_dem.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getComplemento() == null || funcionario.getComplemento().isEmpty()) {
                if (listcof.get(19).getObrigatorio() == 1) {
                    edt_complemento.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getSexo() != null && funcionario.getSexo().toString().equals("Selecione")) {
                if (listcof.get(16).getObrigatorio() == 1) {
                    txtviewerrosexo.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getUf() != null && funcionario.getUf().toString().equals("Selecione")) {
                if (listcof.get(18).getObrigatorio() == 1) {
                    txtviewerroruf.setError("Campo Obrigatório.");
                    retorno = false;
                }
            }
            if (funcionario.getImagem() == null || funcionario.getImagem().isEmpty()) {
                if (listcof.get(15).getObrigatorio() == 1) {
                    imgviewfoto.setImageResource(R.drawable.ic_warning_black_48dp);
                    imgviewfoto.setDrawingCacheEnabled(true);
                }

            }
        }
        else
        {
            retorno=false;
        }

        return retorno;
    }
    public static boolean checkObrigatorioSpinner()
    {
        boolean retorno=true;




        return retorno;
    }
    public static boolean checkObrigatoriaImageView()
    {

        return true;
    }
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        }*/


}
