package com.esperienza.intranetmall.mobile.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.AberturaOSActivity;
import com.esperienza.intranetmall.mobile.activity.BuscaAvancadaOSActivity;
import com.esperienza.intranetmall.mobile.activity.DetalheOSActivity;
import com.esperienza.intranetmall.mobile.adapter.ListaCircularLeituraAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.async.BuscarListaOrdemServicoAsync;
import com.esperienza.intranetmall.mobile.async.BuscarListaOrdemServicoSemDialogAsync;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.ArquivoOSDAO;
import com.esperienza.intranetmall.mobile.dao.CalendarioDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.ObservacaoOSDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoSetorDAO;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.dao.RegraOrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.TipoServicoDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.EntidadeRetornoOS;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.fonts.SimpleDividerItemDecoration;
import com.esperienza.intranetmall.mobile.support.ItemClickSupport;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 11/01/2016.
 */
public class FragmentListaOrdemServico extends Fragment{

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    public static int atualizar=0;
    public FragmentListaOrdemServico(){}
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    private  RecyclerView recyclerViewOS;
    private SwipeRefreshLayout swipeRefreshLayoutOS;
    protected  ListaOrdemServicoAdapter mAdapter;
    protected  RecyclerView.LayoutManager mLayoutManager;
    public static List<OrdemServico> mDataset=null;
    public OrdemServico itemOS = null;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(atualizar!=0)
        {
            if (AppHelper.isInternetOnline()) {
                getListaOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
            } else {
                Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
            }
        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_lista_ordemservico, container, false);

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        recyclerViewOS = (RecyclerView) rootview.findViewById(R.id.recyclerViewOS);

        swipeRefreshLayoutOS = (SwipeRefreshLayout) rootview.findViewById(R.id.os_swipe_refresh_layout);
        swipeRefreshLayoutOS.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            TypedValue typed_value = new TypedValue();
getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
            swipeRefreshLayoutOS.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        }



        recyclerViewOS.setClipToPadding(false);
        recyclerViewOS.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        //Util.setInsets(getActivity(), recyclerViewOS);
        recyclerViewOS.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOS.setHasFixedSize(true);
        fab = (FloatingActionButton) rootview.findViewById(R.id.fab);

        //session
        DateHelper.setLastdatesession();


        //animação reciclerview
        //Animation animation;
        //animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        //animation.setDuration(800);
        // recyclerViewOS.startAnimation(animation);
        //fim animação

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);


        setListeners();
        setHasOptionsMenu(true);

        Home home = HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
        mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());

        List<OrdemServico> filteredModelList=new ArrayList<>();
        switch (this.getArguments().getInt("status",0))
        {

            case 1:
                if (mDataset!=null&&mDataset.size()>0)
                {
                    filteredModelList = filterAprovacao(mDataset);
                    if(filteredModelList.size()<home.getAguardandoaprovacao())
                    {
                        if(AppHelper.isInternetOnline())
                        {
                            swipeRefreshLayoutOS.setRefreshing(true);
                            getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                            filteredModelList.clear();
                            filteredModelList = filterAprovacao(mDataset);
                        }

                    }

                    resetAdapter(filteredModelList);
                }
                else
                {
                    mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    resetAdapter(mDataset);
                }
                break;
            case 2:
                if (mDataset!=null&&mDataset.size()>0)
                {
                    filteredModelList = filterAprovacao(mDataset, 2);
                    if(filteredModelList.size()<home.getAutorizacao())
                    {
                        if(AppHelper.isInternetOnline())
                        {
                            swipeRefreshLayoutOS.setRefreshing(true);
                            getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                            filteredModelList.clear();
                            filteredModelList = filterAprovacao(mDataset, 2);
                        }
                    }

                    resetAdapter(filteredModelList);
                }
                else
                {
                    mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    resetAdapter(mDataset);
                }
                break;
            case 3:
                if (mDataset!=null&&mDataset.size()>0)
                {
                    filteredModelList = filterAprovacao(mDataset, 3);
                    if(filteredModelList.size()<home.getNaoautorizado())
                    {
                        if(AppHelper.isInternetOnline())
                        {
                            swipeRefreshLayoutOS.setRefreshing(true);
                            getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                            filteredModelList.clear();
                            filteredModelList = filterAprovacao(mDataset, 3);
                        }
                    }
                    resetAdapter(filteredModelList);
                }
                else
                {
                    mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    resetAdapter(mDataset);
                }
                break;
            case 4:
                if (mDataset!=null&&mDataset.size()>0)
                {
                    filteredModelList = filterAprovacao(mDataset, 4);
                    if(filteredModelList.size()<home.getEmexecucao())
                    {
                        swipeRefreshLayoutOS.setRefreshing(true);
                        getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                        filteredModelList.clear();
                        filteredModelList = filterAprovacao(mDataset, 4);
                    }

                    resetAdapter(filteredModelList);
                }
                else
                {
                    mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    resetAdapter(mDataset);
                }
                break;
            default:
                initDataset();
                break;
        }

        return rootview;
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerViewOS.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerViewOS.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerViewOS.setLayoutManager(mLayoutManager);
        recyclerViewOS.scrollToPosition(scrollPosition);



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void setListeners()
    {

        ItemClickSupport.addTo(recyclerViewOS).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                itemOS = mAdapter.getItem(position);

                return false;
            }
        });
        ItemClickSupport.addTo(recyclerViewOS).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemOS = mAdapter.getItem(position);
                itemOS.setArquivos(new ArrayList<ArquivoOS>(ArquivoOSDAO.getNewInstance().listArquivoOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())));
                itemOS.setPessoas(new ArrayList<PessoasAutorizadasOS>(PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())));
                itemOS.setAprovadores(new ArrayList<AprovadoresOS>(AprovadoresOSDAO.getNewInstance().listAprovadoresOS(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop(),itemOS.getId_os())));
                ArrayList<ObservacaoOS> listobs = new ArrayList<>(ObservacaoOSDAO.getNewInstance().listObservacaoOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()));
                int count = 0;
                count = listobs.size();
                for (int i = 0; i < count; i++) {
                    listobs.get(i).setUsuario(UsuarioDAO.getNewInstance().getUsuario(listobs.get(i).getIduser(),AppHelper.getIdShop()));
                }
                itemOS.setObservacao(listobs);

                //Usuario userdestino = UsuarioDAO.getNewInstance().getUsuario(itemOS.getIddestino());
                //itemOS.setUserdestino(userdestino);

                //itemOS.setAutorizadores(new ArrayList<Usuario>(UsuarioDAO.getNewInstance().));
                /*FragmentDetalheOrdemServico frag = new FragmentDetalheOrdemServico();
                Bundle bundle= new Bundle();
                bundle.putParcelable("os",Parcels.wrap(itemOS));
                frag.setArguments(bundle);
                // Adiciona o fragment no layout
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, frag);
                ft.commit();*/
                Intent intent = new Intent(getContext(), DetalheOSActivity.class);
                intent.putExtra("os", Parcels.wrap(itemOS));
                startActivity(intent);


            }
        });
        swipeRefreshLayoutOS.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppHelper.isInternetOnline()) {
                    swipeRefreshLayoutOS.setRefreshing(true);
                    getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                } else {
                    Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AberturaOSActivity.class);
                startActivity(intent);

            }
        });
    }
    private void initDataset()
    {
        Home home = HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
       if(AppHelper.getUsuario()!=null) {
           if (OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()).size() > 0) {
               mDataset = OrdemServicoDAO.getNewInstance().listOrdemServico(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
               resetAdapter(mDataset);
               /*if(filterAprovacao(mDataset).size()<home.getAguardandoaprovacao()||filterAprovacao(mDataset, 2).size()<home.getAutorizacao()
                       ||filterAprovacao(mDataset, 3).size()<home.getNaoautorizado()||filterAprovacao(mDataset, 4).size()<home.getEmexecucao())
               {
                   swipeRefreshLayoutOS.setRefreshing(true);
                   getListaSemDialogOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
                   return;
               }*/


           } else {
               if (AppHelper.isInternetOnline()) {
                   getListaOS(String.valueOf(AppHelper.getIdShop()), String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getUsuario().getTipo()));
               } else {
                   Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
               }

           }

       }

    }
    public  void resetAdapter(List<OrdemServico> data) {
        mAdapter = new ListaOrdemServicoAdapter(getActivity().getApplicationContext(), data);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(data!=null&&mAdapter!=null)
        {
            recyclerViewOS.setAdapter(mAdapter);
        }

    }
    /*public static void resetAdapterStatic(List<OrdemServico> data) {
        mAdapter = new ListaOrdemServicoAdapter( data);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(data!=null&&mAdapter!=null)
        {
            recyclerViewOS.setAdapter(mAdapter);
        }

    }*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ordemservico, menu);
        MenuItem itemconsulta = menu.findItem(R.id.action_consultar_os);
        if(AppHelper.getUsuario().getTipo()==1)
        {
            itemconsulta.setVisible(true);
        }
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        searchView.setQueryHint("Entre com dados da O.S");

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mAdapter.setFilter(mDataset);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });


    }
    private List<OrdemServico> filter(List<OrdemServico> models, String query) {
        query = query.toLowerCase();

        final List<OrdemServico> filteredModelList = new ArrayList<>();
        for (OrdemServico model : models) {
            final String solicitante = model.getNomesolicita().toLowerCase();
            final String os = String.valueOf(model.getId_os());
            final String email = model.getEmail();
            final String data = DateHelper.toString(model.getDatafim());
            if (solicitante.contains(query)||os.contains(query)||email.contains(query)||data.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    private List<OrdemServico> filterAprovacao(List<OrdemServico> models) {


        final List<OrdemServico> filteredModelList = new ArrayList<>();
        for (OrdemServico model : models) {
            final int status = model.getStatus();
            final int obsos = model.getObservacoes();

            if (status==1&&obsos==1) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    private List<OrdemServico> filterAprovacao(List<OrdemServico> models,int st) {


        final List<OrdemServico> filteredModelList = new ArrayList<>();
        for (OrdemServico model : models) {
            final int status = model.getStatus();
            //model.setArquivos(new ArrayList<ArquivoOS>(ArquivoOSDAO.getNewInstance().listArquivoOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())));
            //model.setPessoas(new ArrayList<PessoasAutorizadasOS>(PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())));
            try
            {
                model.setAprovadores(new ArrayList<AprovadoresOS>(AprovadoresOSDAO.getNewInstance().listAprovadoresOS(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop(), model.getId_os())));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //ArrayList<ObservacaoOS> listobs = new ArrayList<>(ObservacaoOSDAO.getNewInstance().listObservacaoOSPorOS(itemOS.getId_os(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()));
            //int count = 0;
            //count = listobs.size();
            //for (int i = 0; i < count; i++) {
            //    listobs.get(i).setUsuario(UsuarioDAO.getNewInstance().getUsuario(listobs.get(i).getIduser(),AppHelper.getIdShop()));
            //}
            //model.setObservacao(listobs);

            boolean checkapr=false;
            if(model.getAprovadores()!=null&&model.getAprovadores().size()>0) {
                for (int i = 0; i < model.getAprovadores().size(); i++) {
                    if(model.getAprovadores().get(i)!=null) {
                        if (model.getAprovadores().get(i).getIduser() == AppHelper.getUsuario().getIduser()) {
                            checkapr = true;
                        }
                    }

                }
            }


            if (status==st&&checkapr) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    private List<OrdemServico> filterIDOS(List<OrdemServico> models,String query) {


        final List<OrdemServico> filteredModelList = new ArrayList<>();
        for (OrdemServico model : models) {
            final int idos = model.getId_os();


            if (String.valueOf(idos).equals(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                final List<OrdemServico> filteredModelList = filterIDOS(mDataset, query);
                if(filteredModelList.size()>0)
                {
                    resetAdapter(filteredModelList);
                }
                else
                {
                    Toast.makeText(getActivity(),"Ordem de serviço não encontrado.",Toast.LENGTH_LONG).show();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Mudou o texto digitado
                final List<OrdemServico> filteredModelList = filter(mDataset, newText);
                if(filteredModelList.size()>0)
                {
                    resetAdapter(filteredModelList);
                }

                return true;
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_consultar_os) {
            //toast("Clicou no Search!");
            startActivity(new Intent(getActivity(), BuscaAvancadaOSActivity.class));
            return true;
        } else if (id == R.id.action_okos) {
           List<OrdemServico> filteredModelList=filterAprovacao(mDataset);
            if(filteredModelList.size()>0)
            {
                resetAdapter(filteredModelList);
            }
            else
            {
                Toast.makeText(getActivity(),"Não há ordem de serviço para aprovação.",Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getListaOS(String idshop, String usuario, String tipo) {

        new BuscarListaOrdemServicoAsync(getActivity(), new BuscarListaOrdemServicoAsync.Action() {


            @Override
            public void preExecute()
            {

            }

            @Override
            public void postExecute(EntidadeRetornoOS result)
            {
                List<OrdemServico> listos=result.getListaordemdeservico();
                //List<RegraOrdemServico> listros=result.getRegras();
                //List<OrdemServicoSetor> listoss=result.getSetores();
                //List<Calendario> listca=result.getCalendarios();
                List<Usuario> listadestino= result.getListausuariodestino();
               if(listos!=null)
               {
                   if(listos.size()>0) {
                       try {
                           int count = listos.size();
                           OrdemServico os;
                           List<ObservacaoOS> obsos;
                           List<PessoasAutorizadasOS> paos;
                           List<Usuario> user;
                           List<ArquivoOS> arqos;
                           List<AprovadoresOS> apros;
                           int countobs = 0;
                           int countpa = 0;
                           int countarq = 0;
                           int countuser = 0;
                           int countdestino=0;
                           for (int i = 0; i < count; i++) {

                               OrdemServicoDAO.getNewInstance().save(listos.get(i));
                               UsuarioDAO.getNewInstance().saveOS(listos.get(i).getUserdestino());
                               obsos = listos.get(i).getObservacao();
                               paos = listos.get(i).getPessoas();
                               arqos = listos.get(i).getArquivos();
                               //user = result.get(i).getAutorizadores();
                               apros = listos.get(i).getAprovadores();

                               countobs = obsos.size();
                               countpa = paos.size();
                               countarq = arqos.size();
                               countuser = apros.size();

                               for (int j = 0; j < countobs; j++) {
                                   ObservacaoOSDAO.getNewInstance().save(obsos.get(j));
                                   UsuarioDAO.getNewInstance().saveOS(obsos.get(j).getUsuario());
                               }
                               for (int k = 0; k < countpa; k++) {
                                   PessoasAutorizadasOSDAO.getNewInstance().save(paos.get(k));
                               }
                               for (int z = 0; z < countarq; z++) {
                                   ArquivoOSDAO.getNewInstance().save(arqos.get(z));
                               }
                               /*for (int y = 0; y < countuser; y++) {
                                   UsuarioDAO.getNewInstance().saveOS(user.get(y));
                               }*/
                               for(int y=0;y<countuser;y++)
                               {
                                   apros.get(y).setIdshop(AppHelper.getIdShop());
                                   apros.get(y).setIdusermobile(AppHelper.getUsuario().getIduser());
                                   AprovadoresOSDAO.getNewInstance().save(apros.get(y));
                               }



                           }
                           //regra
                           /*if(listros!=null)
                           {
                               int counter=listros.size();
                               for(int i=0;i<counter;i++)
                               {
                                   RegraOrdemServicoDAO.getNewInstance().save(listros.get(i));
                               }
                           }
                           //calendario
                           if(listca!=null)
                           {
                               int counter=listca.size();
                               for (int i=0;i<counter;i++)
                               {
                                   CalendarioDAO.getNewInstance().save(listca.get(i));
                               }
                           }
                           //setores
                           if(listoss!=null)
                           {
                               int counter=listoss.size();
                               for (int i=0;i<counter;i++)
                               {
                                   OrdemServicoSetorDAO.getNewInstance().save(listoss.get(i));
                                   int count1=listoss.get(i).getTipoServicos().size();
                                   for(int j=0;j<count1;j++)
                                   {
                                       TipoServicoDAO.getNewInstance().save(listoss.get(i).getTipoServicos().get(j));
                                   }
                               }
                           }*/
                           //destino
                           /*if(listadestino!=null)
                           {
                               int counter=listadestino.size();
                               for(int i=0;i<counter;i++)
                               {
                                   UsuarioDAO.getNewInstance().saveOS(listadestino.get(i));
                               }
                           }*/
                           mDataset.clear();
                           mDataset.addAll(listos);
                           mAdapter = new ListaOrdemServicoAdapter(getContext(), listos);
                           // Set CustomAdapter as the adapter for RecyclerView.
                           if(mAdapter!=null)
                           {
                               recyclerViewOS.setAdapter(mAdapter);
                               mAdapter.notifyDataSetChanged();
                           }




                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                   else
                   {
                       Toast.makeText(getActivity(),"Não há ordem de serviço cadastrada.",Toast.LENGTH_SHORT).show();
                   }
               }
            }



        }).execute(idshop, usuario, tipo);

    }
    public void getListaSemDialogOS(String idshop, String usuario, String tipo) {

        new BuscarListaOrdemServicoSemDialogAsync(getActivity(), new BuscarListaOrdemServicoSemDialogAsync.Action() {


            @Override
            public void preExecute()
            {

            }

            @Override
            public void postExecute(EntidadeRetornoOS result)
            {
                List<OrdemServico> listos=result.getListaordemdeservico();
                List<RegraOrdemServico> listros=result.getRegras();
                List<OrdemServicoSetor> listoss=result.getSetores();
                List<Calendario> listca=result.getCalendarios();
                List<Usuario> listadestino= result.getListausuariodestino();

                if(listos!=null)
                {
                    if(listos.size()>0) {
                        try {
                            int count = listos.size();
                            OrdemServico os;
                            List<ObservacaoOS> obsos;
                            List<PessoasAutorizadasOS> paos;
                            List<Usuario> user;
                            List<ArquivoOS> arqos;
                            List<AprovadoresOS> apros;
                            int countobs = 0;
                            int countpa = 0;
                            int countarq = 0;
                            int countuser = 0;
                            int countapr = 0;
                            for (int i = 0; i < count; i++) {

                                OrdemServicoDAO.getNewInstance().save(listos.get(i));
                                UsuarioDAO.getNewInstance().saveOS(listos.get(i).getUserdestino());

                                obsos = listos.get(i).getObservacao();
                                paos = listos.get(i).getPessoas();
                                arqos = listos.get(i).getArquivos();
                                //user = result.get(i).getAutorizadores();
                                apros = listos.get(i).getAprovadores();

                                countobs = obsos.size();
                                countpa = paos.size();
                                countarq = arqos.size();
                                countapr = apros.size();
                                //countuser = user.size();

                                for (int j = 0; j < countobs; j++) {
                                    ObservacaoOSDAO.getNewInstance().save(obsos.get(j));
                                    UsuarioDAO.getNewInstance().saveOS(obsos.get(j).getUsuario());
                                }
                                for (int k = 0; k < countpa; k++) {
                                    PessoasAutorizadasOSDAO.getNewInstance().save(paos.get(k));
                                }
                                for (int z = 0; z < countarq; z++) {
                                    ArquivoOSDAO.getNewInstance().save(arqos.get(z));
                                }
                                for(int y=0;y<countapr;y++)
                                {
                                    AprovadoresOSDAO.getNewInstance().save(apros.get(y));
                                }
                                /*for (int y = 0; y < countuser; y++) {
                                    UsuarioDAO.getNewInstance().saveOS(user.get(y));
                                }*/



                            }
                            //regra
                            /*if(listros!=null)
                            {
                                int counter=listros.size();
                                for(int i=0;i<counter;i++)
                                {
                                    RegraOrdemServicoDAO.getNewInstance().save(listros.get(i));
                                }
                            }
                            //calendario
                            if(listca!=null)
                            {
                                int counter=listca.size();
                                for (int i=0;i<counter;i++)
                                {
                                    CalendarioDAO.getNewInstance().save(listca.get(i));
                                }
                            }*/
                            //setores
                            /*if(listoss!=null)
                            {
                                int counter=listoss.size();
                                for (int i=0;i<counter;i++)
                                {
                                    OrdemServicoSetorDAO.getNewInstance().save(listoss.get(i));
                                    int count1=listoss.get(i).getTipoServicos().size();
                                    for(int j=0;j<count1;j++)
                                    {
                                        TipoServicoDAO.getNewInstance().save(listoss.get(i).getTipoServicos().get(j));
                                    }
                                }
                            }*/
                            //destino
                            /*if(listadestino!=null)
                            {
                                int counter=listadestino.size();
                                for(int i=0;i<counter;i++)
                                {
                                    UsuarioDAO.getNewInstance().saveOS(listadestino.get(i));
                                }
                            }*/
                            mDataset.clear();
                            mDataset.addAll(listos);
                            mAdapter = new ListaOrdemServicoAdapter(getContext(), listos);
                            // Set CustomAdapter as the adapter for RecyclerView.
                            if(mAdapter!=null)
                            {
                                recyclerViewOS.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            }

                            swipeRefreshLayoutOS.setRefreshing(false);

                        } catch (Exception e) {
                            e.printStackTrace();
                            swipeRefreshLayoutOS.setRefreshing(false);

                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Não há ordem de serviço cadastrada.",Toast.LENGTH_LONG).show();
                        swipeRefreshLayoutOS.setRefreshing(false);
                    }
                }

            }



        }).execute(idshop, usuario, tipo);

    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (mDataset!=null&&mAdapter!=null)
        {
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
            //mDataset.notifyAll();
            mAdapter.notifyDataSetChanged();
        }
        /*else
        {
            initDataset();
        }*/

    }


}
