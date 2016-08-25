package com.esperienza.intranetmall.mobile.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.DetalheOSActivity;
import com.esperienza.intranetmall.mobile.activity.DetalhePAOSActivity;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoPAAdapter;
import com.esperienza.intranetmall.mobile.async.BuscarOrdemServicoPesquisaAvancadaAsync;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.ArquivoOSDAO;
import com.esperienza.intranetmall.mobile.dao.ObservacaoOSDAO;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.EntidadeBuscaAvancadaOs;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.fonts.SimpleDividerItemDecoration;
import com.esperienza.intranetmall.mobile.support.ItemClickSupport;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/07/2016.
 */
public class FragmentPesquisaAvancada extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    public FragmentPesquisaAvancada(){}
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView recyclerViewOSPA;
    protected ListaOrdemServicoPAAdapter mAdapter;
    protected  RecyclerView.LayoutManager mLayoutManager;
    public static List<OrdemServico> mDataset=new ArrayList<>();
    public OrdemServico itemOS = new OrdemServico();
    public EntidadeBuscaAvancadaOs ebos;
    public static boolean flag=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getParcelable("ebos")!=null)
            ebos= Parcels.unwrap(getArguments().getParcelable("ebos"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_pesquisa_avancada, container, false);

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        recyclerViewOSPA = (RecyclerView) rootview.findViewById(R.id.recyclerViewPesquisaOS);
        recyclerViewOSPA.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerViewOSPA.setClipToPadding(false);
        //Util.setInsets(getActivity(), recyclerViewOS);
        recyclerViewOSPA.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOSPA.setHasFixedSize(true);





        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);


        setListeners();
        getBuscaAvancadaOS();
        setHasOptionsMenu(true);




        return rootview;
    }
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
    public  void resetAdapter(List<OrdemServico> data) {
        mAdapter = new ListaOrdemServicoPAAdapter(getActivity().getApplicationContext(), data);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(data!=null&&mAdapter!=null)
        {
            recyclerViewOSPA.setAdapter(mAdapter);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerViewOSPA.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerViewOSPA.getLayoutManager())
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

        recyclerViewOSPA.setLayoutManager(mLayoutManager);
        recyclerViewOSPA.scrollToPosition(scrollPosition);



    }
    public void getBuscaAvancadaOS()
    {
        new BuscarOrdemServicoPesquisaAvancadaAsync(getActivity(),ebos, new BuscarOrdemServicoPesquisaAvancadaAsync.Action() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(List<OrdemServico> result) {

                if(result!=null&&result.size()>0)
                {
                    try
                    {
                        mDataset.clear();
                        mDataset.addAll(result);
                        mAdapter = new ListaOrdemServicoPAAdapter(getContext(), mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        if(mAdapter!=null)
                        {
                            recyclerViewOSPA.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {

                    getActivity().finish();
                    Toast.makeText(getActivity(),"Não há informações para o período e critérios selecionados.",Toast.LENGTH_LONG).show();
                }

            }
        }).execute();
    }
    public void setListeners() {

        ItemClickSupport.addTo(recyclerViewOSPA).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                itemOS = mAdapter.getItem(position);

                return false;
            }
        });
        ItemClickSupport.addTo(recyclerViewOSPA).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemOS = mAdapter.getItem(position);
                //itemOS.setArquivos(new ArrayList<ArquivoOS>(ArquivoOSDAO.getNewInstance().listArquivoOSPorOS(itemOS.getId_os(), AppHelper.getUsuario().getIduser(), AppHelper.getIdShop())));
                //itemOS.setPessoas(new ArrayList<PessoasAutorizadasOS>(PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSPorOS(itemOS.getId_os(), AppHelper.getUsuario().getIduser(), AppHelper.getIdShop())));
                //itemOS.setAprovadores(new ArrayList<AprovadoresOS>(AprovadoresOSDAO.getNewInstance().listAprovadoresOS(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop(), itemOS.getId_os())));
                //ArrayList<ObservacaoOS> listobs = new ArrayList<>(ObservacaoOSDAO.getNewInstance().listObservacaoOSPorOS(itemOS.getId_os(), AppHelper.getUsuario().getIduser(), AppHelper.getIdShop()));
                //int count = 0;
                //count = listobs.size();
                //for (int i = 0; i < count; i++) {
                //    listobs.get(i).setUsuario(UsuarioDAO.getNewInstance().getUsuario(listobs.get(i).getIduser(), AppHelper.getIdShop()));
                //}
                //itemOS.setObservacao(listobs);

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
                Intent intent = new Intent(getContext(), DetalhePAOSActivity.class);
                intent.putExtra("os", Parcels.wrap(itemOS));
                startActivity(intent);


            }
        });
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
        if(flag)
        {
            flag=false;
            getBuscaAvancadaOS();
        }

    }
}
