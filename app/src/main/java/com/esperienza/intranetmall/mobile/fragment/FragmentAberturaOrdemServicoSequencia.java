package com.esperienza.intranetmall.mobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.AberturaOSActivity;
import com.esperienza.intranetmall.mobile.activity.ArquivosActivity;
import com.esperienza.intranetmall.mobile.activity.MenuActivity;
import com.esperienza.intranetmall.mobile.activity.PessoasAutorizadasActivity;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaPessoasAutorizadasAdapter;
import com.esperienza.intranetmall.mobile.async.CadastraOrdemServicoAsync;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.ArquivoOSDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Arquivos;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.Ordem;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.PAutorizadas;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThinkPad on 10/02/2016.
 */
public class FragmentAberturaOrdemServicoSequencia extends Fragment{

    private ScrollView scrollView;
    public static OrdemServico os;
    private TipoServico ts;
    private String outros;
    private EditText  edtlojista;
    private EditText  edttelefone;
    private EditText  edtemail;
    private TextView  txtlojaluc;
    private TextView  txttiposervico;
    private TextView  txtdatainicio;
    private TextView  txtdatatermino;
    private TextView  txthorainicio;
    private TextView  txthoratermino;
    private TextView  txtaddarquivo;
    private TextView  txtaddpessoas;
    private TextView  txtqtdpesssoas;
    private TextView  txtqtdanexos;
    private ImageView imgbtnarquivo;
    private ImageView imgbtnpessoas;
    private EditText edttxtobs;
    private EditText edttextsolicitante;
    private Button btnprocessar;
    private CardView cardsolicitantedestino;
    private Spinner spinnersolicitante;
    private RadioGroup rg;
    private RadioButton rbloja;
    private RadioButton rbshop;
    private List<Integer> lojasid;
    private List<Integer> shopid;
    private LinearLayout linearprocessaos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        os=Parcels.unwrap(getArguments().getParcelable("os"));
        os.setPessoas(new ArrayList<PessoasAutorizadasOS>());
        ts=Parcels.unwrap(getArguments().getParcelable("ts"));
        outros=getArguments().getString("outros");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_abertura_ordemservico_sequencia, container, false);
        scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewAberturaOS2);
        Util.setInsets(getActivity(), scrollView);
        edtlojista = (EditText) rootview.findViewById(R.id.edtlojista);
        edttelefone = (EditText) rootview.findViewById(R.id.edttelefone);
        edtemail = (EditText) rootview.findViewById(R.id.edtemail);
        txtlojaluc = (TextView) rootview.findViewById(R.id.txtviewshopluc);
        txttiposervico = (TextView) rootview.findViewById(R.id.txtviewtiposervico);
        txtdatainicio = (TextView) rootview.findViewById(R.id.etxt_fromdate2);
        txtdatatermino = (TextView) rootview.findViewById(R.id.etxt_todate2);
        txthorainicio = (TextView) rootview.findViewById(R.id.etxt_fromtime2);
        txthoratermino = (TextView) rootview.findViewById(R.id.etxt_totime2);
        txtaddarquivo = (TextView) rootview.findViewById(R.id.txtviewarquivos);
        txtaddpessoas = (TextView) rootview.findViewById(R.id.txtviewpessoas);
        txtqtdpesssoas = (TextView) rootview.findViewById(R.id.textviewqtdpessoa);
        txtqtdanexos  =  (TextView) rootview.findViewById(R.id.textviewqtdanexo);
        imgbtnarquivo = (ImageView) rootview.findViewById(R.id.imgbtnarquivos);
        imgbtnpessoas = (ImageView) rootview.findViewById(R.id.imgbtnpessoas);
        edttextsolicitante = (EditText) rootview.findViewById(R.id.EditTextSolicitante);
        edttxtobs = (EditText) rootview.findViewById(R.id.EditTextObs);
        //btnprocessar = (Button) rootview.findViewById(R.id.btnprocessar);
        cardsolicitantedestino = (CardView) rootview.findViewById(R.id.cardsolicitantedestino);
        spinnersolicitante = (Spinner) rootview.findViewById(R.id.spinnersolicitante);
        rg = (RadioGroup) rootview.findViewById(R.id.rdgroupdestino);
        rbloja = (RadioButton) rootview.findViewById(R.id.radio_loja);
        rbshop = (RadioButton) rootview.findViewById(R.id.radio_shop);
        linearprocessaos = (LinearLayout) rootview.findViewById(R.id.linearprocessaos);

        events();
        init();
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        return rootview;
    }
    public void init()
    {
        if(AppHelper.getUsuario().getTipo()==1)
            cardsolicitantedestino.setVisibility(View.VISIBLE);

        edtlojista.setText(AppHelper.getUsuario().getNomeresponsavel());
        edttelefone.setText(AppHelper.getUsuario().getTelefone1()!=null&&!AppHelper.getUsuario().getTelefone1().toLowerCase().equals("null")?AppHelper.getUsuario().getTelefone1():"");
        edtemail.setText(AppHelper.getUsuario().getEmail());
        String loja=(AppHelper.getUsuario().getEmpresa()!=null?AppHelper.getUsuario().getEmpresa():"");
        String luc=(AppHelper.getUsuario().getLuc()!=null&&!AppHelper.getUsuario().getLuc().equals("")?"| LUC "+AppHelper.getUsuario().getLuc():"");
        if(!loja.equals("anyType{}"))
        {
            txtlojaluc.setText(loja+" "+luc);
        }
        else
        {
            txtlojaluc.setVisibility(View.GONE);
        }

        txttiposervico.setText(ts.getDescricao() + (outros.length() > 0 ? " - " + outros : ""));
        txtdatainicio.setText(DateHelper.toString(os.getDatainicio()));
        txtdatatermino.setText(DateHelper.toString(os.getDatafim()));
        txthorainicio.setText(os.getHorainicio());
        txthoratermino.setText(os.getHorafim());
        //aki
        if(ListaPessoasAutorizadasAdapter.copypositionArray!=null)
        {
            txtqtdpesssoas.setText(ListaPessoasAutorizadasAdapter.copypositionArray.size()>1?ListaPessoasAutorizadasAdapter.copypositionArray.size()+" pesssoas":ListaPessoasAutorizadasAdapter.copypositionArray.size()+" pesssoa");
        }
        else
        {
            txtqtdpesssoas.setText("0 pessoa");
        }
        if(ArquivosActivity.mDataSet!=null)
        {
            txtqtdanexos.setText(ArquivosActivity.mDataSet.size()>1?ArquivosActivity.mDataSet.size()+" anexos":ArquivosActivity.mDataSet.size()+" anexo");
        }
        else
        {
            txtqtdanexos.setText("0 anexo");
        }






    }
    public void events()
    {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radio_shop:
                        spinnersolicitante.setVisibility(View.VISIBLE);
                        List<String> shop =new ArrayList<>();
                        shopid = new ArrayList<>();


                        List<Usuario> lshop = UsuarioDAO.getNewInstance().getListTipoUsuario(1,AppHelper.getIdShop());

                        int counter=lshop.size();
                        for(int i=0;i<counter;i++)
                        {
                            shop.add(i,lshop.get(i).getNomeresponsavel()+" - "+ lshop.get(i).getLogin());
                            shopid.add(i,lshop.get(i).getIduser());
                        }


                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, shop);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinnersolicitante.setAdapter(dataAdapter);
                        break;
                    case R.id.radio_loja:
                        spinnersolicitante.setVisibility(View.VISIBLE);
                        List<String> lojas =new ArrayList<>();
                        lojasid =new ArrayList<>();
                        List<Usuario> llojas=UsuarioDAO.getNewInstance().getListTipoUsuario(2,AppHelper.getIdShop());
                        int counter2=llojas.size();
                        for(int j=0;j<counter2;j++)
                        {
                            lojas.add(j,llojas.get(j).getEmpresa()+" - "+llojas.get(j).getLogin());
                            lojasid.add(j,llojas.get(j).getIduser());
                        }
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, lojas);

                        // Drop down layout style - list view with radio button
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinnersolicitante.setAdapter(dataAdapter2);
                        break;

                }
            }
        });
        txtaddpessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PessoasAutorizadasActivity.class);
                startActivity(intent);
            }
        });
        txtaddarquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArquivosActivity.class);
                startActivity(intent);
            }
        });
        imgbtnpessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PessoasAutorizadasActivity.class);
                startActivity(intent);

            }
        });
        imgbtnarquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArquivosActivity.class);
                startActivity(intent);
            }
        });
       linearprocessaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ordem ordem = new Ordem();
                ordem.setIdShopping(String.valueOf(AppHelper.getIdShop()));
                ordem.setIduser(String.valueOf(AppHelper.getUsuario().getIduser()));
                ordem.setIdtipo(String.valueOf(ts.getIdtipo()));
                if (AppHelper.getUsuario().getTipo() == 2) {
                    ordem.setIddestino(String.valueOf(AppHelper.getUsuario().getIduser()));
                } else if (AppHelper.getUsuario().getTipo() == 1) {
                    if (rbloja.isChecked()) {
                        ordem.setIddestino(lojasid.get(spinnersolicitante.getSelectedItemPosition()).toString());
                    }else
                    if (rbshop.isChecked()) {
                        ordem.setIddestino(shopid.get(spinnersolicitante.getSelectedItemPosition()).toString());
                    }else
                    if(!rbloja.isChecked()&&!rbshop.isChecked())
                    {
                        ordem.setIddestino(String.valueOf(AppHelper.getUsuario().getIduser()));
                    }

                }

                ordem.setDatainicio(DateHelper.toStringSQLServer(os.getDatainicio()));
                ordem.setDatafim(DateHelper.toStringSQLServer(os.getDatafim()));
                ordem.setHorainicio(os.getHorainicio());
                ordem.setHorafim(os.getHorafim());
                ordem.setDescricao(edttxtobs.getText().toString());
                ordem.setNomesolicita(edttextsolicitante.getText().toString());
                ordem.setNomelojista(edtlojista.getText().toString());
                ordem.setEmail(edtemail.getText().toString());
                ordem.setTelefone(edttelefone.getText().toString());
                if (ts.getIdtipo() == 1) {
                    ordem.setDescricaotipo(outros);
                } else {
                    ordem.setDescricaotipo(ts.getDescricao());
                }


                Arquivos arquivos;
                int tam = ArquivosActivity.mDataSet.size();
                Arquivos[] listarquivos = new Arquivos[tam];
                for (int i = 0; i < tam; i++) {
                    arquivos = new Arquivos();
                    arquivos.setExtensao(ArquivosActivity.mDataSet.get(i).getExtensao());
                    arquivos.setFile(ArquivosActivity.mDataSet.get(i).getUrlarquivo());
                    listarquivos[i] = arquivos;
                }
                ordem.setArquivos_(listarquivos);

                PAutorizadas pAutorizadas;
                int tam1 = FragmentAberturaOrdemServicoSequencia.os.getPessoas().size();
                PAutorizadas[] listpAutorizadas = new PAutorizadas[tam1];
                for (int i = 0; i < tam1; i++) {
                    pAutorizadas = new PAutorizadas();
                    pAutorizadas.setNome(FragmentAberturaOrdemServicoSequencia.os.getPessoas().get(i).getNome());
                    pAutorizadas.setRg(FragmentAberturaOrdemServicoSequencia.os.getPessoas().get(i).getRg());
                    pAutorizadas.setEmpresa(FragmentAberturaOrdemServicoSequencia.os.getPessoas().get(i).getEmpresa());
                    listpAutorizadas[i] = pAutorizadas;
                }
                ordem.setPessoas_(listpAutorizadas);
                boolean checkobrig = false;
                String msgobrig = "";
                if (ts.getObrigatorioanexo() == 1) {
                    if (ordem.getArquivos_().length == 0) {
                        checkobrig = true;
                        msgobrig = "Obrigatório anexo de arquivo";
                    }

                }
                if (ts.getObrigatorioobs() == 1) {
                    if (edttxtobs.getText().toString().length() <= 0) {
                        msgobrig = msgobrig + "\nObrigatório adicionar a descrição";
                        checkobrig = true;
                    }
                }
                if (ordem.getPessoas_().length == 0) {
                    msgobrig = msgobrig + "\nObrigatório adicionar ao menos uma pessoa autorizada";
                    checkobrig = true;
                }
                if (edtlojista.getText().toString().length() == 0) {
                    msgobrig = msgobrig + "\nObrigatório adicionar lojista";
                    checkobrig = true;
                }
                if (edtemail.getText().toString().length() == 0) {
                    msgobrig = msgobrig + "\nObrigatório adicionar e-mail";
                    checkobrig = true;
                }
                if (edttelefone.getText().toString().length() == 0) {
                    msgobrig = msgobrig + "\nObrigatório adicionar telefone";
                    checkobrig = true;
                }
                if (edttextsolicitante.getText().toString().length() == 0) {
                    msgobrig = msgobrig + "\nObrigatório adicionar solicitante";
                    checkobrig = true;
                }
                if (AppHelper.getUsuario().getTipo() == 1 && (!rbshop.isChecked() && !rbloja.isChecked())) {
                    msgobrig = msgobrig + "\nObrigatório adicionar solicitante de destino";
                    checkobrig = true;
                }
                if (checkobrig) {
                    Toast.makeText(getActivity(), msgobrig, Toast.LENGTH_LONG).show();
                    return;
                }
                cadastraos(ordem);

            }
        });
    }
    public void cadastraos(Ordem ordem)
    {
        new CadastraOrdemServicoAsync(getActivity(), ordem, new CadastraOrdemServicoAsync.Action() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(OrdemServico result) {

               if(result!=null)
               {
                  List<AprovadoresOS> lapr=result.getAprovadores();
                  List<PessoasAutorizadasOS> lpa=result.getPessoas();
                  List<ArquivoOS> larq=result.getArquivos();

                   OrdemServicoDAO.getNewInstance().save(result);

                   for(int i=0;i<lapr.size();i++)
                   {
                       AprovadoresOSDAO.getNewInstance().save(lapr.get(i));
                   }
                   for(int j=0;j<lpa.size();j++)
                   {
                       PessoasAutorizadasOSDAO.getNewInstance().save(lpa.get(j));
                   }
                   for(int k=0;k<larq.size();k++)
                   {
                       ArquivoOSDAO.getNewInstance().save(larq.get(k));
                   }

                   //ListaOrdemServicoAdapter.mDataSet.add(0,result);
                   //ListaOrdemServicoAdapter adl= new ListaOrdemServicoAdapter();
                   //adl.notifyDataSetChanged();

                   //adl.notifyItemInserted(0);

                   try
                   {
                       if (PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorN(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop()).size() > 0) {
                           int count = PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorN(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop()).size();
                           List<PessoasAutorizadasOS> palist = PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorN(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop());
                           for (int i = 0; i < count; i++) {
                               PessoasAutorizadasOSDAO.getNewInstance().remove(palist.get(i));
                           }
                       }

                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }

                   ListaOrdemServicoAdapter loadpter = new ListaOrdemServicoAdapter();
                   FragmentListaOrdemServico.mDataset.add(0, result);
                   List<OrdemServico> servicoList=new ArrayList<>();
                   servicoList.addAll(FragmentListaOrdemServico.mDataset);
                   loadpter.setFilter(servicoList);
                   try
                   {
                      Home home = HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                       home.setAguardandoaprovacao(home.getAguardandoaprovacao()+1);
                      HomeDAO.getNewInstance().save(home);
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
                   getActivity().finish();

               }
               else
               {
                 Toast.makeText(getActivity(),"Erro no cadastro da ordem de serviço.",Toast.LENGTH_SHORT).show();
               }
            }

        }).execute();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            if(ListaPessoasAutorizadasAdapter.copypositionArray!=null)
            {
                txtqtdpesssoas.setText(ListaPessoasAutorizadasAdapter.copypositionArray.size()>1?ListaPessoasAutorizadasAdapter.copypositionArray.size()+" pesssoas":ListaPessoasAutorizadasAdapter.copypositionArray.size()+" pesssoa");
            }
            else
            {
                txtqtdpesssoas.setText("0 pessoa");
            }
            if(ArquivosActivity.mDataSet!=null)
            {
                txtqtdanexos.setText(ArquivosActivity.mDataSet.size()>1?ArquivosActivity.mDataSet.size()+" anexos":ArquivosActivity.mDataSet.size()+" anexo");
            }
            else
            {
                txtqtdanexos.setText("0 anexo");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(ListaPessoasAutorizadasAdapter.copypositionArray!=null)
        {
            ListaPessoasAutorizadasAdapter.copypositionArray.clear();

        }
        if(ArquivosActivity.mDataSet!=null)
        {
            ArquivosActivity.mDataSet.clear();
        }
    }




}
