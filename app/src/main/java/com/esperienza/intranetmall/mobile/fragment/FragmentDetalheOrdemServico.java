package com.esperienza.intranetmall.mobile.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.ArquivoOsActivity;
import com.esperienza.intranetmall.mobile.activity.DetalheOSActivity;
import com.esperienza.intranetmall.mobile.adapter.GridViewOSPAAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.async.CadastraObsOsAsync;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.ArquivoOSDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.ObservacaoOSDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.dao.TipoServicoDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 19/01/2016.
 */
public class FragmentDetalheOrdemServico extends Fragment{

    private TextView textViewSolicitante;
    //private TextView textViewOS;
    private TextView textViewdataos;
    private TextView textViewhoraos;
    private TextView textViewloja;
    private TextView textViewlojista;
    private TextView textViewtelefone;
    private TextView textViewservico;
    private TextView textViewdatainicio;
    private TextView textViewhorainicio;
    private TextView textViewdatafim;
    private TextView textViewhorafim;
    private TextView textViewdetalhesservico;
    private ScrollView scrollViewOS;
    private OrdemServico os;
    private GridViewOSPAAdapter mAdapterPA;
    private TableLayout tablePA;
    private TableLayout tableARQ;
    private TableLayout tableCO;
    private TableLayout tableAPR;
    private List<ArquivoOS> listarq;
    private CardView cardViewComentario;
    private CardView cardstatus;
    private TextView textViewStatus;
    private Button btnenviaobs;
    private EditText editobs;
    private ImageView imgstatus;
    private View rootView;
    private int checked=0;




    public FragmentDetalheOrdemServico(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getParcelable("os")!=null)
        os= Parcels.unwrap(getArguments().getParcelable("os"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_ordemservico,
                container, false);
        textViewSolicitante = (TextView) rootView.findViewById(R.id.tsolicitante);
        //textViewOS = (TextView) rootView.findViewById(R.id.tos);
        textViewhoraos = (TextView) rootView.findViewById(R.id.tHora);
        textViewdataos = (TextView) rootView.findViewById(R.id.tData);
        textViewloja = (TextView) rootView.findViewById(R.id.tLoja);
        textViewlojista = (TextView) rootView.findViewById(R.id.tLojista);
        textViewtelefone = (TextView) rootView.findViewById(R.id.tTelefone);
        textViewservico = (TextView) rootView.findViewById(R.id.tservico);
        textViewdatainicio = (TextView) rootView.findViewById(R.id.tdatainicio);
        textViewhorainicio = (TextView) rootView.findViewById(R.id.thorainicio);
        textViewdatafim = (TextView) rootView.findViewById(R.id.tdatafim);
        textViewhorafim = (TextView) rootView.findViewById(R.id.thorafim);
        textViewdetalhesservico = (TextView) rootView.findViewById(R.id.tdetalheservico);
        scrollViewOS = (ScrollView) rootView.findViewById(R.id.scrollViewOSDetalhe);
        tablePA = (TableLayout) rootView.findViewById(R.id.tableLayoutPA);
        tableARQ = (TableLayout) rootView.findViewById(R.id.tableLayoutARQ);
        tableCO = (TableLayout) rootView.findViewById(R.id.tableLayoutCO);
        tableAPR = (TableLayout) rootView.findViewById(R.id.tableLayoutApr);
        cardViewComentario = (CardView) rootView.findViewById(R.id.cardcomentario);
        cardstatus = (CardView) rootView.findViewById(R.id.cardstatus);
        textViewStatus = (TextView) rootView.findViewById(R.id.tstatus);
        btnenviaobs = (Button) rootView.findViewById(R.id.btnenviarobs);
        editobs = (EditText) rootView.findViewById(R.id.editobs);
        imgstatus = (ImageView) rootView.findViewById(R.id.img_status_detalhe);
        checked=0;


        if(os.getObservacoes()==1)
        {
            cardViewComentario.setVisibility(View.VISIBLE);
        }


        Util.setInsets(getActivity(), scrollViewOS);
        events();
        //init();

        return rootView;
    }
    public void init()
    {
        textViewSolicitante.setText(os.getNomesolicita().equals("anyType{}")?"":os.getNomesolicita());
        //textViewOS.setText(String.valueOf(os.getId_os()));
        textViewdataos.setText(DateHelper.toString(os.getDatacad()));
        textViewhoraos.setText(os.getHoracad().substring(0, 5));
        //String luc=" | LUC "+AppHelper.getUsuario().getLuc();
        String luc="";
            Usuario udestino=os.getUserdestino();
            if(udestino==null||udestino.getEmpresa().equals("anyType{}"))
            {
                udestino= UsuarioDAO.getNewInstance().getUsuario(os.getIddestino(),AppHelper.getIdShop());
            }

            if(udestino!=null)
            {
                if(udestino.getLuc()!=null) {
                    if (!udestino.getLuc().equals("null") && !udestino.getLuc().equals("anyType{}"))
                        luc = " | LUC " + udestino.getLuc();
                }

                textViewloja.setText(udestino.getEmpresa()!=null&&!udestino.getEmpresa().equals("anyType{}")?udestino.getEmpresa():"" + luc);
            }
            else
            {
                textViewloja.setVisibility(View.GONE);
            }


        textViewlojista.setText(os.getNomelojista());
        textViewtelefone.setText(os.getTelefone()!=null&&!os.getTelefone().equals("anyType{}")?os.getTelefone():"");
        textViewservico.setText(getServico(os.getIdtipo()));
        textViewdetalhesservico.setText(os.getDescricao().equals("anyType{}")?"":os.getDescricao());
        textViewdatainicio.setText(DateHelper.toString(os.getDatainicio()));
        textViewdatafim.setText(DateHelper.toString(os.getDatafim()));
        textViewhorainicio.setText(os.getHorainicio().substring(0, 5));
        textViewhorafim.setText(os.getHorafim().substring(0,5));

        switch (os.getStatus())
        {
            case 1:
                textViewStatus.setText("Em Aprovação");
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_ffffcc));
                break;
            case 2:
                textViewStatus.setText("Autorizado");
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_b2e0ff));
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                break;
            case 3:
                textViewStatus.setText("Não Autorizado");
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_ffcccc));
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                break;
            case 4:
                textViewStatus.setText("Em execução");
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_cccccc));
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                break;
            case 5:
                textViewStatus.setText("Concluído");
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_c1e0d1));
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                break;
            case 6:
                textViewStatus.setText("Expirado");
                imgstatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.status_os_ffcc99));
                cardstatus.setMaxCardElevation(0.0f);
                cardstatus.setRadius(5.0f);
                break;
        }
        buildtablepa();
        buildtablearq();
        buildtableco();
        buildtableAprovadores();







    }
    public String getServico(int id)
    {
        String result="";
        if(TipoServicoDAO.getNewInstance().getTipoServico(id,AppHelper.getIdShop())!=null)
        {
            return TipoServicoDAO.getNewInstance().getTipoServico(id,AppHelper.getIdShop()).getDescricao();
        }

        switch (id) {

        case 1:
        result="Outros serviços";
        break;
        case 2:
        result="Contagem de Estoque/Balanço";
        break;
        case 3:
        result="Promoções / Liquidações";
        break;
        case 4:
        result="Fechamento para balanço";
        break;
        case 5:
        result="Adesivos em vitrine";
        break;
        case 6:
            result="Eventos";
        break;
        case 7:
            result="Vendas de ingressos";
        break;
        case 8:
            result="Promoções/Liquidações";
        break;
        case 9:
            result="Merchandising";
        break;
        case 10:
            result="Manutenção de Equipamentos";
        break;
        case 11:
            result="Ar condicionado";
        break;
        case 12:
            result="Manutenção elétrica";
        break;
        case 13:
            result="Troca de lâmpadas ";
        break;
        case 14:
            result="Hidráulica";
        break;
        case 15:
            result="Acesso a DG (telefonia)";
        break;
        case 16:
            result="Manutenção no mall";
        break;
        case 17:
            result="Reforma Loja/Instalação de Móveis";
        break;
        case 18:
            result="Pequenos Reparos/ Pinturas";
        break;
        case 19:
            result="Substituições/Manutenção de luminosos/Letreiros";
        break;
        case 20:
            result="Reformas de lojas";
        break;
        case 21:
            result="Inicio de obras";
        break;
        case 22:
            result="Movimentação de mobiliários do mall";
        break;
        case 23:
            result="Paisagismo";
        break;
        case 24:
            result="Entrega de mercadoria";
        break;
        case 25:
            result="Carga e Descarga";
        break;
        case 26:
            result="Entrada de colaboradores";
        break;
        case 27:
            result="Cadastro de novo funcionário envolvendo Joalherias";
        break;
        case 28:
            result="Acesso ao shopping após o horário permitido";
        break;
        case 29:
            result="Manutenções em CFTV, alarmes";
        break;
        case 30:
            result="Entrada e retirada tapume";
        break;
        case 31:
            result="Entrada e saída de lojas";
        break;
        case 32:
            result="Entrada de Quiosques";
        break;
        case 33:
            result="Saída de Quiosques";
        break;
        case 34:
            result="Entrada de equipamento ( lojas que estão saindo )";
        break;
        case 35:
            result="Saída de equipamento ( lojas que estão saindo )";
        break;
        case 36:
            result="Entrada de mídias";
        break;
        case 37:
        //holder.servicoOS.setText("Outros serviços");
        break;
        case 38:
            result="Feirão";
        break;
        case 39:
            result="Entregas e/ou trocas de caçambas";
        break;
        case 40:
            result="Vagas de estacionamento";
        break;
        case 41:
            result="Manutenção geral no estacionamento";
        break;
        case 42:
            result="Solicitação de vistoria";
        break;
        case 43:
            result="Outros serviços";
        break;
        case 44:
            result="Limpeza em Geral de Lojas";
        break;
        case 45:
            result="Entrada de Funcionários para Treinamento";
        break;
        case 47:
            result="Promoções/Liquidações";
        break;
        case 48:
            result="Adesivo no mall";
        break;
        case 49:
            result="Entrada Especial";
        break;
        case 52:
            result="Entrada de funcionario temporario";
        break;
        case 53:
            result="Projetos Especiais";
        break;
        case 54:
            result="Evento de Marketing";
        break;
    }
        return result;
    }
    public void buildtablepa()
    {
        int count=os.getPessoas().size();
        List<PessoasAutorizadasOS> listpa=os.getPessoas();

        if(count>0)
        {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            row.setBackgroundResource(R.drawable.linecircular2);
            row.setWeightSum(1f);
            for(int k=0;k<3;k++)
            {
                TableRow.LayoutParams lp;
                TableRow.LayoutParams lp2;
                lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.30f);
                lp2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.40f);

                TextView tv = new TextView(getActivity());
                if(k==0)
                {
                    tv.setLayoutParams(lp2);
                }
                else
                {
                    tv.setLayoutParams(lp);
                }
                tv.setPadding(2, 2, 2, 2);
                if(k==0)
                {
                    tv.setText("Nome");
                }
                else if(k==1)
                {
                    tv.setText("Empresa");
                } else if(k==2)
                {
                    tv.setText("RG");
                }


                row.addView(tv);
            }
            tablePA.addView(row);
        }

        for(int i=0;i<count;i++)
        {

            TableRow row = new TableRow(getActivity());
            row.setWeightSum(1f);
            /*row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));*/
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0,  0.33f));
            /*if(i % 2==0)
            {
                //row.setBackgroundColor(getResources().getColor(R.color.LIGHT_GRAY));
            }*/


            row.setBackgroundResource(R.drawable.linecircular2);
            /*ShapeDrawable border = new ShapeDrawable(new RectShape());
            border.getPaint().setStyle(Paint.Style.STROKE);
            border.getPaint().setColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                row.setBackground(border);
            }*/


            for (int j = 0; j < 3; j++) {


                TextView tv = new TextView(getActivity());
                TableRow.LayoutParams lp;
                TableRow.LayoutParams lp2;
                lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.30f);
                lp2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.40f);
                if(j==0)
                {
                    tv.setLayoutParams(lp2);
                }
                else
                {
                    tv.setLayoutParams(lp);
                }
               //tv.setGravity(Gravity.CENTER);
                tv.setPadding(2, 2, 2, 2);
                if(j==0)
                {
                    tv.setText(listpa.get(i).getNome());
                }
                else if(j==1)
                {
                        tv.setText(listpa.get(i).getEmpresa());
                } else if(j==2)
                {
                        tv.setText(listpa.get(i).getRg());
                }
                    row.addView(tv);
                }



            tablePA.addView(row);
            }

        }

    public void buildtablearq()
    {
        int count = os.getArquivos().size();
        listarq = os.getArquivos();
        for(int i=0;i<count;i++)
        {
            final int k=i;
            final TableRow row = new TableRow(getActivity());

            LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
            row.setLayoutParams(llp);
            row.setClickable(true);
            TextView tv = new TextView(getActivity());
            Drawable img = ContextCompat.getDrawable(getActivity(),R.drawable.arquivos_anexados_os_interna);
            switch (AppHelper.getSizeScreen())
            {
                case "small":
                    img.setBounds( 0, 0, 14, 14 );
                    break;
                case "normal":
                    img.setBounds( 0, 0, 64, 64 );
                    break;
                case "large":
                    img.setBounds( 0, 0, 78, 78 );
                    break;
                case "xlarge":
                    img.setBounds( 0, 0, 94, 94 );
                    break;
            }

            tv.setCompoundDrawables( img, null, null, null );

            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv.setPadding(5, 5, 5, 5);
            String cod = new String("COD: "+os.getId_os()+"-"+listarq.get(i).getIdarquivo());
            SpannableString content = new SpannableString(cod);
            content.setSpan(new UnderlineSpan(), 0, cod.length(), 0);
            tv.setText(content);
            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //abrirArquivo(listarq.get(k).getUrlarquivo() + listarq.get(k).getIdarquivo() + "." + listarq.get(k).getExtensao());
                    Intent intent = new Intent(getActivity(), ArquivoOsActivity.class);
                    intent.putExtra("arquivos",Parcels.wrap(listarq.get(k)));
                    getActivity().startActivity(intent);

                }
            });


            tableARQ.addView(tv);

        }

    }
    public void buildtableco()
    {
        int count = os.getObservacao().size();
         List<ObservacaoOS> listobs = os.getObservacao();
        for(int i=0;i<count;i++) {

            final TableRow row = new TableRow(getActivity());
            LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
            row.setLayoutParams(llp);
            row.setBackgroundResource(R.drawable.linecircular3);
            LayoutParams ltv = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ltv.setMargins(40, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(ltv);
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv.setPadding(5, 5, 5, 5);
            switch (AppHelper.getSizeScreen())
            {
                case "small":
                    tv.setTextSize(14.0f);
                    break;
                case "normal":

                    break;
                case "large":
                    tv.setTextSize(24.0f);
                    break;
                case "xlarge":
                    tv.setTextSize(28.0f);
                    break;
            }

            String empresa="";
            if(listobs.get(i).getUsuario().getEmpresa()!=null)
            {
                empresa=(!listobs.get(i).getUsuario().getEmpresa().toString().startsWith("any")? " - "+listobs.get(i).getUsuario().getEmpresa():"");
            }
            tv.setText("  " + DateHelper.toString(listobs.get(i).getDatacad()).substring(0, 10) + " às "
                    + listobs.get(i).getHoracad().substring(0, 5) + "\r\n  " + listobs.get(i).getUsuario().getNomeresponsavel()
                    + empresa);
            row.addView(tv);

            final TableRow row2 = new TableRow(getActivity());
            LayoutParams llp2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llp2.setMargins(0, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
            row2.setLayoutParams(llp2);
            row2.setBackgroundResource(R.drawable.linecircular2);
            TextView tv2 = new TextView(getActivity());
            switch (AppHelper.getSizeScreen())
            {
                case "small":
                    tv2.setTextSize(14.0f);
                    break;
                case "normal":

                    break;
                case "large":
                    tv2.setTextSize(24.0f);
                    break;
                case "xlarge":
                    tv2.setTextSize(28.0f);
                    break;
            }
            tv2.setLayoutParams(ltv);
            tv2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv2.setPadding(5, 5, 5, 5);
            tv2.setText("  "+listobs.get(i).getObservacoes());
            row2.addView(tv2);

            tableCO.addView(row);
            tableCO.addView(row2);
        }


    }
    public void buildtableAprovadores()
    {
        int count = os.getAprovadores().size();
        List<AprovadoresOS> lapr = os.getAprovadores();
        boolean isaprovador=false;
        for(int i=0;i<lapr.size();i++)
        {
            if(lapr.get(i).getAcao()==1)
            {
                isaprovador=true;
            }
        }
        if(count>0)
        {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for(int k=0;k<2;k++)
            {

                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setPadding(2, 2, 2, 2);
                switch (AppHelper.getSizeScreen())
                {
                    case "small":
                        tv.setTextSize(11.0f);
                        break;
                    case "normal":

                        break;
                    case "large":
                        tv.setTextSize(20.0f);
                        break;
                    case "xlarge":
                        tv.setTextSize(24.0f);
                        break;
                }
                //tv.setGravity(Gravity.CENTER);
                if(k==0)
                {
                    tv.setText("Responsável");
                }
                else if(k==1)
                {
                    tv.setText("Aprovação");
                }

                row.setBackgroundResource(R.drawable.linecircular2);
                row.addView(tv);
            }
            tableAPR.addView(row);
        }
        //primeira linha
        for(int i=0;i<count;i++)
        {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            row.setBackgroundResource(R.drawable.linecircular2);
            /*if(i % 2==0)
            {
                row.setBackgroundColor(getResources().getColor(R.color.LIGHT_GRAY));
            }*/

            for (int j = 0; j < 2; j++) {


                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setPadding(2, 2, 2, 2);
                //tv.setGravity(Gravity.CENTER);
                switch (AppHelper.getSizeScreen())
                {
                    case "small":
                        tv.setTextSize(11.0f);
                        break;
                    case "normal":

                        break;
                    case "large":
                        tv.setTextSize(20.0f);
                        break;
                    case "xlarge":
                        tv.setTextSize(24.0f);
                        break;
                }
                if(lapr.get(i).getAlcadas()!=1) {
                    if (j == 0) {
                        tv.setText(lapr.get(i).getSuplente()==0?lapr.get(i).getNome():lapr.get(i).getNome()+" (*)");
                    } else if (j == 1) {
                        if (lapr.get(i).getAcao() < 3) {
                            tv.setText(getAcao(lapr.get(i).getAcao()));
                        } else {
                            createRadioButton(row);
                        }

                    }
                    if (j == 0 || lapr.get(i).getAcao() < 3)
                        row.addView(tv);
                }
                else {
                    if (isaprovador){
                        if (lapr.get(i).getAcao() == 1) {
                            if (j == 0) {
                                tv.setText(lapr.get(i).getSuplente()==0?lapr.get(i).getNome():lapr.get(i).getNome()+" (*)");
                            } else if (j == 1) {
                                if (lapr.get(i).getAcao() < 3) {
                                    tv.setText(getAcao(lapr.get(i).getAcao()));
                                } else {
                                    createRadioButton(row);
                                }

                            }
                            if (j == 0 || lapr.get(i).getAcao() < 3)
                                row.addView(tv);
                        }
                }
                    else
                    {

                            if (j == 0)
                            {

                                tv.setText(lapr.get(i).getSuplente()==0?lapr.get(i).getNome():lapr.get(i).getNome()+" (*)");
                            } else if (j == 1)
                            {
                                if (lapr.get(i).getAcao() < 3) {
                                    tv.setText(getAcao(lapr.get(i).getAcao()));
                                } else {
                                    createRadioButton(row);
                                }

                            }
                            if (j == 0 || lapr.get(i).getAcao() < 3)
                                row.addView(tv);

                    }
                }
            }
            if(row!=null&&row.getChildCount()>0)
            tableAPR.addView(row);
        }


    }
    public String getAcao(int acao)
    {
        String retorna="";
        switch (acao)
        {
            case 0:
                retorna="Aguardando Aprovação";
                break;
            case 1:
                retorna="Aprovado";
                break;
            case 2:
                retorna="Reprovado";
                break;

        }
        return retorna;
    }
    public void abrirArquivo(String url) {

        try
        {

            final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
            getActivity().startActivity(intent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        init();
    }
    public void events()
    {
       btnenviaobs.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {

               if (editobs.getText().toString().equals("")) {
                   Toast.makeText(getActivity(), "Insira o comentário antes.", Toast.LENGTH_LONG).show();
               } else {
                   switch (checked)
                   {
                       case 0:
                           onCreateDialog().show();
                           break;
                       case 1:
                           onCreateDialogSIM().show();
                           break;
                       case 2:
                           onCreateDialogNAO().show();
                           break;
                   }



                   //enviaObs(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(os.getId_os()), editobs.getText().toString());

               }
           }
       });
    }
    public void enviaObs(String idshop,String iduser,String idos,String obs)
    {
        new CadastraObsOsAsync(getActivity(),rootView,new CadastraObsOsAsync.Action() {

            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(ObservacaoOS result) {

                if(result!=null)
                {
                    if(result.getIdcomentario()>0)
                    {
                        result.setIdshop(AppHelper.getIdShop());
                        result.setIdusermobile(AppHelper.getUsuario().getIduser());
                        ObservacaoOSDAO.getNewInstance().save(result);
                        OrdemServico os1;

                        os1=OrdemServicoDAO.getNewInstance().getOrdemServico(result.getIdos(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());

                        os1.setStatus(Integer.parseInt(result.getUsuario().getTelefone1()));
                        os1.setIdshop(AppHelper.getIdShop());
                        os1.setIdusermobile(AppHelper.getUsuario().getIduser());

                        OrdemServicoDAO.getNewInstance().save(os1);
                        //AprovadoresOS apr = AprovadoresOSDAO.getNewInstance().getAprovadoresOS(AppHelper.getUsuario().getIduser(),result.getIdos());
                        //apr.setAcao(checked);
                        //AprovadoresOSDAO.getNewInstance().save(apr);
                        try {
                            Home home = HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                            if(Integer.parseInt(result.getUsuario().getTelefone1())==2)
                            {
                                home.setAutorizacao(home.getAutorizacao()+1);
                                home.setAguardandoaprovacao(home.getAguardandoaprovacao()-1);
                                HomeDAO.getNewInstance().save(home);
                            }
                            else if(Integer.parseInt(result.getUsuario().getTelefone1())==3)
                            {
                                home.setNaoautorizado(home.getNaoautorizado()+1);
                                home.setAguardandoaprovacao(home.getAguardandoaprovacao()-1);
                                HomeDAO.getNewInstance().save(home);
                            }

                            //ListaOrdemServicoAdapter.mDataSet.add(0,os1);


                            ListaOrdemServicoAdapter losa = new ListaOrdemServicoAdapter();
                            //losa.mDataSet.remove(os);
                            List<OrdemServico> servicoList = new ArrayList<>();
                            servicoList.addAll(FragmentListaOrdemServico.mDataset);
                            for (OrdemServico os2:FragmentListaOrdemServico.mDataset)
                            {
                                if(os2.getId_os()==os.getId_os())
                                {
                                    servicoList.remove(os2);
                                }
                            }
                            servicoList.add(0,os1);
                            FragmentListaOrdemServico.mDataset.clear();
                            FragmentListaOrdemServico.mDataset.addAll(servicoList);
                            losa.setFilter(servicoList);

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }



                        os.getObservacao().add(result);





                        //FragmentListaOrdemServico fos = new FragmentListaOrdemServico();
                        //fos.resetAdapter(OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser()));

                        //FragmentListaOrdemServico.resetAdapterStatic(OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser()));
                        editobs.setText("");
                        editobs.clearFocus();
                        Toast.makeText(getActivity(), "Registro efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Houve problema na requisição do cadastro.",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }).execute(idshop, iduser, idos, obs, String.valueOf(checked));
    }

    public Dialog onCreateDialog() {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Comentário")
                .setMessage("Comentar ordem de serviço?")
                .setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                enviaObs(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(os.getId_os()), editobs.getText().toString());
                            }
                        }
                )
                .setNegativeButton("NÃO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                editobs.setFocusable(true);
                            }
                        }
                )
                .create();
    }
    public Dialog onCreateDialogSIM() {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Aprovação")
                .setMessage("Aprovar ordem de serviço?")
                .setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                enviaObs(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(os.getId_os()), editobs.getText().toString());
                            }
                        }
                )
                .setNegativeButton("NÃO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                editobs.setFocusable(true);
                            }
                        }
                )
                .create();
    }
    public Dialog onCreateDialogNAO() {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Reprovação")
                .setMessage("Reprovar ordem de serviço?")
                .setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                enviaObs(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(os.getId_os()), editobs.getText().toString());
                            }
                        }
                )
                .setNegativeButton("NÃO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                editobs.setFocusable(true);
                            }
                        }
                )
                .create();
    }
    private void createRadioButton(TableRow row) {
        final RadioButton[] rb = new RadioButton[2];
        RadioGroup rg = new RadioGroup(getActivity()); //create the RadioGroup
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        for(int i=0; i<2; i++){
            rb[i]  = new RadioButton(getActivity());
            rb[i].setId(i);
            rg.addView(rb[i]);
             if(i==0)
             {
                 rb[i].setText("Sim");//the RadioButtons are added to the radioGroup instead of the layout
             }
            else
             {
                 rb[i].setText("Não");
             }

        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
             switch (checkedId)
             {
                 case 0:
                     checked=1;
                     break;
                 case 1:
                     checked=2;
                     break;
             }
            }
        });
        row.addView(rg);//you add the whole RadioGroup to the layout

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tableCO.removeAllViews();
        tablePA.removeAllViews();
        tableAPR.removeAllViews();
        tableARQ.removeAllViews();
    }
    @Override
    public void onStop() {
        super.onStop();
        tableCO.removeAllViews();
        tablePA.removeAllViews();
        tableAPR.removeAllViews();
        tableARQ.removeAllViews();
    }


}
