package com.esperienza.intranetmall.mobile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaPessoasAutorizadasAdapter;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.fonts.SimpleDividerItemDecoration;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/02/2016.
 */
public class FragmentPessoasAutorizadas extends Fragment {


    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    public FragmentPessoasAutorizadas(){}
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView recyclerViewPA;
    private SwipeRefreshLayout swipeRefreshLayoutPA;
    protected ListaPessoasAutorizadasAdapter mAdapter;
    protected  RecyclerView.LayoutManager mLayoutManager;
    protected List<PessoasAutorizadasOS> mDataset=null;
    public PessoasAutorizadasOS itemOS = null;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_pessoas_autorizadas, container, false);

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        recyclerViewPA = (RecyclerView) rootview.findViewById(R.id.recyclerViewPA);

        //swipeRefreshLayoutPA = (SwipeRefreshLayout) rootview.findViewById(R.id.pa_swipe_refresh_layout);
        //swipeRefreshLayoutPA.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            TypedValue typed_value = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
            //swipeRefreshLayoutPA.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        }

        recyclerViewPA.setClipToPadding(false);
        Util.setInsets(getActivity(), recyclerViewPA);
        recyclerViewPA.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPA.setHasFixedSize(true);
        fab = (FloatingActionButton) rootview.findViewById(R.id.fabpa);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        initDataset();
        setListeners();
        setHasOptionsMenu(true);

        return rootview;

    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerViewPA.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerViewPA.getLayoutManager())
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
        recyclerViewPA.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerViewPA.setLayoutManager(mLayoutManager);
        recyclerViewPA.scrollToPosition(scrollPosition);


    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    private void initDataset()
    {
        if(PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSLojaRetorno(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()).size()>0)
        {
            mDataset=PessoasAutorizadasOSDAO.getNewInstance().listPessoasAutorizadasOSLojaRetorno(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop());
            resetAdapter(mDataset);
        }

    }
    public void setListeners()
    {
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FragmentAddPessoaAutorizada frag = new  FragmentAddPessoaAutorizada();

                // Adiciona o fragment no layout
                getFragmentManager().beginTransaction().replace(R.id.pessoasautorizadas, frag).commit();

            }
        });
    }
    public  void resetAdapter(List<PessoasAutorizadasOS> data) {
        mAdapter = new ListaPessoasAutorizadasAdapter(getActivity(), data);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(data!=null&&mAdapter!=null)
        {
            recyclerViewPA.setAdapter(mAdapter);
        }

    }

}
