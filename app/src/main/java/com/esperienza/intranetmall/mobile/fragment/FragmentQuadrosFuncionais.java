package com.esperienza.intranetmall.mobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.ArquivoOsActivity;
import com.esperienza.intranetmall.mobile.activity.DestaqueActivity;
import com.esperienza.intranetmall.mobile.async.CadastraDispositivoAsync;
import com.esperienza.intranetmall.mobile.dao.CircularDAO;
import com.esperienza.intranetmall.mobile.dao.DeviceDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.entidade.Destaque;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.gcm.Constants;
import com.esperienza.intranetmall.mobile.gcm.GCM;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by ThinkPad on 08/12/2015.
 */
public class FragmentQuadrosFuncionais extends Fragment{


    public FragmentQuadrosFuncionais(){};
    private ImageSwitcher banner;
    private ImageView alertCircular;
    private ImageView imgviewprevious;
    private ImageView imgviewnext;
    private TextView textViewEmAprovacao;
    private TextView textViewAutorizacao;
    private TextView textViewNaoAutorizado;
    private TextView textViewEmExecucao;
    private TextView textViewQtdCircularNaoLidas;
    private TextView textViewQtdFuncionarios;
    private ProgressBar progressBar;
    private TextView textViewAguApr;
    private TextView textViewAut;
    private TextView textViewNaut;
    private TextView textViewExe;
    private int[] imagens = {R.drawable.icone_pdf, R.drawable.arquivos,
            R.drawable.ic_acao_pause};
    private int idx = 0;
    private ImageView img;
    private int position=0;
    private List<Destaque> destaques;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            //setText(regId);
            // Quando iniciar, lê a msg da notification
            //String msg = getIntent().getStringExtra("msg");
            //setText(msg);
        destaques=AppHelper.getDestaque();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.content_main, container, false);
        banner = (ImageSwitcher) rootview.findViewById(R.id.imageViewBanner);
        //alertCircular = (ImageView) rootview.findViewById(R.id.imageViewalertCircular);
        textViewEmAprovacao = (TextView) rootview.findViewById(R.id.textViewAguardandoAprovacao);
        textViewAutorizacao = (TextView) rootview.findViewById(R.id.textViewAutorizacao);
        textViewNaoAutorizado = (TextView) rootview.findViewById(R.id.textViewNaoAutorizado);
        textViewEmExecucao = (TextView) rootview.findViewById(R.id.textViewEmExecucao);
        textViewQtdCircularNaoLidas = (TextView) rootview.findViewById(R.id.textViewQtdCircularesnaolidas);
        textViewQtdFuncionarios = (TextView) rootview.findViewById(R.id.textViewQtdfnc);
        imgviewnext = (ImageView) rootview.findViewById(R.id.imgviewnext);
        imgviewprevious = (ImageView) rootview.findViewById(R.id.imgviewprevious);
        progressBar = (ProgressBar) rootview.findViewById(R.id.progressImg);
        img= (ImageView) rootview.findViewById(R.id.img);
        textViewAguApr = (TextView) rootview.findViewById(R.id.textViewagaut);
        textViewAut = (TextView) rootview.findViewById(R.id.textViewaut);
        textViewNaut = (TextView) rootview.findViewById(R.id.textViewnaut);
        textViewExe = (TextView) rootview.findViewById(R.id.textViewexe);
        final int i = 0;
        final Animation myFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

        try
        {
            if(destaques!=null&&destaques.size()>1) {
                banner.postDelayed(new Runnable() {

                    public void run() {
                        Picasso.with(getActivity()).load(destaques.get(getPosition(false)).getUrl()).error(R.drawable.img_erro_carregamento_intranetmall).fit().into(img,
                                new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                        progressBar.setVisibility(View.GONE); // download ok
                                        img.startAnimation(myFadeInAnimation);
                                    }


                                    @Override
                                    public void onError() {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                        banner.postDelayed(this, 5000);
                    }

                }, 2000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        textViewAguApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textViewEmAprovacao.getText().toString().equals("0"))
                {
                    FragmentListaOrdemServico.atualizar=0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status",1);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"Não há ordem de serviço com este status.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewAut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textViewAutorizacao.getText().toString().equals("0"))
                {
                    FragmentListaOrdemServico.atualizar=0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status",2);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"Não há ordem de serviço com este status.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewNaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textViewNaoAutorizado.getText().toString().equals("0"))
                {
                    FragmentListaOrdemServico.atualizar=0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status",3);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"Não há ordem de serviço com este status.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textViewEmExecucao.getText().toString().equals("0"))
                {
                    FragmentListaOrdemServico.atualizar=0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                    Bundle bundle = new Bundle();
                    bundle.putInt("status",4);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"Não há ordem de serviço com este status.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*banner.setFactory(new ImageSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                img = new ImageView(getActivity());
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                img.setAlpha(0.95f);
                img.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return img;
            }
        });*/
        banner.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        banner.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    /*if(!destaques.get(position).getLink().equals("")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(destaques.get(position).getLink()));
                        startActivity(intent);
                    }*/
                    if(destaques.get(position).getTipo()==1||destaques.get(position).getTipo()==3) {
                        Intent intent = new Intent(getActivity(), DestaqueActivity.class);
                        intent.putExtra("destaque", Parcels.wrap(destaques.get(position)));
                        getActivity().startActivity(intent);
                    }
                    else
                    if(destaques.get(position).getTipo()==2)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(destaques.get(position).getLink()));
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Destaque sem link.",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        imgviewnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                progressBar.setVisibility(View.VISIBLE);
                // Faz o download da foto e mostra o ProgressBar
                Picasso.with(getActivity()).load(destaques.get(getPosition(false)).getUrl()).error(R.drawable.img_erro_carregamento_intranetmall).fit().into(img,
                        new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE); // download ok
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });

            }
        });
        imgviewprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                progressBar.setVisibility(View.VISIBLE);
                // Faz o download da foto e mostra o ProgressBar
                Picasso.with(getActivity()).load(destaques.get(getPosition(true)).getUrl()).error(R.drawable.img_erro_carregamento_intranetmall).fit().into(img,
                        new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE); // download ok
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });

            }
        });

        /*CircularDAO circularDAO = new CircularDAO();
        if(AppHelper.getUsuario()!=null) {
            if ((circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()) != null && circularDAO.listCircularLeitura(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop(),0).size() > 0 )|| (HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())!=null&&HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()).getCircularnaolida()>0)) {
                alertCircular.setBackgroundResource(R.drawable.list_alertcircular);
                Animatable anim = (AnimationDrawable) alertCircular.getBackground();
                anim.start();
            }
        }*/




        initData();
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }



        ScrollView scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewContentMain);
        scrollView.setClipToPadding(false);
        setInsets(getActivity(),scrollView);

        LinearLayout app_layeros = (LinearLayout) rootview.findViewById(R.id.layout_ordemservico);
        app_layeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentListaOrdemServico.atualizar=0;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                Bundle bundle = new Bundle();
                bundle.putInt("status",0);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });
        LinearLayout app_layercircular = (LinearLayout) rootview.findViewById(R.id.layout_circular);
        app_layercircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCircularLeitura.atualizar=0;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentCircularLeitura fragment = new FragmentCircularLeitura();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });
        LinearLayout app_layerfnc = (LinearLayout) rootview.findViewById(R.id.layout_cadastrofuncionario);
        app_layerfnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Cadastro Funcionário", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(MenuActivity.this, ListaFuncionariosFragment.class));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                RecyclerViewFragment fragment = new RecyclerViewFragment();
                FragmentListaFuncionarioAdm fragadm = new FragmentListaFuncionarioAdm();
                if(AppHelper.getUsuario().getTipo()==1)
                {
                    transaction.replace(R.id.container, fragadm);
                }
                else
                {
                    transaction.replace(R.id.container, fragment);
                }
                transaction.commit();


            }
        });

        return rootview;
    }
    public static void setInsets(Activity context, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    }




    public void initData()
    {
        if (AppHelper.getUsuario() != null) {
            try {
                HomeDAO homeDAO = new HomeDAO();

                if(destaques!=null&&destaques.size()>0) {



                          /*  progressBar.setVisibility(View.VISIBLE);
                            Picasso.with(getActivity()).load(AppHelper.getDestaque().get(getPosition(false)).getUrl()).error(R.drawable.img_erro_carregamento_intranetmall).fit().into(img,
                                    new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.GONE); // download ok
                                        }

                                        @Override
                                        public void onError() {
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });*/


                    progressBar.setVisibility(View.VISIBLE);
                    // Faz o download da foto e mostra o ProgressBar
                    Picasso.with(getActivity()).load(AppHelper.getDestaque().get(0).getUrl()).error(R.drawable.img_erro_carregamento_intranetmall).fit().into(img,
                            new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE); // download ok
                                }

                                @Override
                                public void onError() {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
                if(destaques.size()<=1)
                {
                    imgviewnext.setVisibility(View.INVISIBLE);
                    imgviewprevious.setVisibility(View.INVISIBLE);
                }

                Home home = homeDAO.getHome(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop());
                if (home != null) {
                    //Bitmap bitmap = AppHelper.decodeBase64(home.getImagembanner());
                    //BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                    //banner.setBackground(ob);

                    textViewEmAprovacao.setText(String.valueOf(home.getAguardandoaprovacao()));
                    textViewAutorizacao.setText(String.valueOf(home.getAutorizacao()));
                    textViewNaoAutorizado.setText(String.valueOf(home.getNaoautorizado()));
                    textViewEmExecucao.setText(String.valueOf(home.getEmexecucao()));
                    textViewQtdCircularNaoLidas.setText(String.valueOf(home.getCircularnaolida()));
                    textViewQtdFuncionarios.setText(String.valueOf(home.getQtdfuncionario()));
                }
            }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    public int getPosition(Boolean leftright)
    {
        //left true
        //right false
        if(leftright)
        {
            if(position==0)
            {
                position = destaques.size()-1;
            }
            else
            {
                position--;
            }

        }
        else
        {
            if(position==destaques.size()-1)
            {
                position=0;
            }
            else
            {
                position++;
            }
        }
        return position;
    }






}
