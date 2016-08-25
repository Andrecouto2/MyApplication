package com.esperienza.intranetmall.mobile.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.PdfCircularActivity;
import com.esperienza.intranetmall.mobile.adapter.ListaCircularLeituraAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaFuncionarioAdapter;
import com.esperienza.intranetmall.mobile.async.BuscarListaCircularAsync;
import com.esperienza.intranetmall.mobile.async.BuscarListaCircularSemDialogAsync;
import com.esperienza.intranetmall.mobile.async.BuscarListaFuncionarioAsync;
import com.esperienza.intranetmall.mobile.async.FlagLeituraCircularAsync;
import com.esperienza.intranetmall.mobile.async.LeitoresCircularAsync;
import com.esperienza.intranetmall.mobile.dao.CircularDAO;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.LeitoresCircularDAO;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.EntidadeCircular;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.LeitoresCircular;
import com.esperienza.intranetmall.mobile.fonts.SimpleDividerItemDecoration;
import com.esperienza.intranetmall.mobile.support.ItemClickSupport;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Util;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

/**
 * Created by ThinkPad on 16/12/2015.
 */
public class FragmentCircularLeitura extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private int idFuncionario = 0;
    public static int atualizar=0;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    //private Spinner spinnerMes;
    //private Spinner spinnerAno;
    private RecyclerView recyclerViewCircularLeitura;
    protected ListaCircularLeituraAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Circular> mDataset=null;
    public Circular itemCircular = null;
    public static String ano = null;//Spinner filtro
    public static String mes = null;//Spinner filtro
    public PopupWindow popupWindow;
    public ArrayList<Map<String, Object>> data;
    public SwipeRefreshLayout circular_swipe_refresh_layout;


    public FragmentCircularLeitura() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(atualizar!=0)
        {
            if (AppHelper.isInternetOnline()) {
                getListaCircularLeitura(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), "0");
            } else {
                Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_circular_leitura, container, false);

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        //session
        DateHelper.setLastdatesession();


        //spinnerMes = (Spinner) rootview.findViewById(R.id.spinnerMes);
        //spinnerAno = (Spinner) rootview.findViewById(R.id.spinnerAno);
        recyclerViewCircularLeitura = (RecyclerView) rootview.findViewById(R.id.recyclerViewCircularLeitura);
        circular_swipe_refresh_layout = (SwipeRefreshLayout) rootview.findViewById(R.id.circular_swipe_refresh_layout);
        circular_swipe_refresh_layout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.paletalaranja), ContextCompat.getColor(getActivity(),R.color.paletagreen), ContextCompat.getColor(getActivity(),R.color.paletaazul), Color.CYAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            TypedValue typed_value = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
            circular_swipe_refresh_layout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        }

        //spinnerMes.setClipToPadding(false);
        //spinnerAno.setClipToPadding(false);
        recyclerViewCircularLeitura.setClipToPadding(false);
        //setInsets(getActivity(), recyclerViewCircularLeitura);
        registerForContextMenu(recyclerViewCircularLeitura);

        //animação reciclerview
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        animation.setDuration(800);
        recyclerViewCircularLeitura.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerViewCircularLeitura.startAnimation(animation);
        recyclerViewCircularLeitura.setHasFixedSize(true);
        //Util.setInsets(getActivity(), recyclerViewCircularLeitura);
        //fim animação


        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new ListaCircularLeituraAdapter(getActivity(), mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerViewCircularLeitura.setAdapter(mAdapter);

        //compara server -->mobile
        Home home =HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
        int qntnlida=CircularDAO.getNewInstance().listCircularLeitura(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop(),0).size();
        if(home.getCircularnaolida()>qntnlida)
        {
            if (AppHelper.isInternetOnline())
            {
                getListaCircularLeitura(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), "0");
            }
            else
            {
                Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
            }
        }

        //------------------------

        setListeners();
        initDataset();

        setHasOptionsMenu(true);
        return rootview;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerViewCircularLeitura.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerViewCircularLeitura.getLayoutManager())
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

        recyclerViewCircularLeitura.setLayoutManager(mLayoutManager);
        recyclerViewCircularLeitura.scrollToPosition(scrollPosition);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_circular_leitura, menu);
        MenuItem item = menu.findItem(R.id.action_search_circular);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        //searchView.setQueryHint("Entre com dados da O.S");

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        resetAdapter();
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

    }
    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Mudou o texto digitado
               final List<Circular> listacircular= filter(mDataset,newText);
                if(listacircular.size()>0)
                {
                    resetAdapter(listacircular);
                }

                return true;
            }
        };
    }
    private List<Circular> filter(List<Circular> models, String query) {
        query = query.toLowerCase();

        final List<Circular> filteredModelList = new ArrayList<>();
        for (Circular model : models) {
            final String data = DateHelper.toString(model.getData_cadastro());
            final String nome = model.getTitulo().toLowerCase();

            if (nome.contains(query)||data.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_circular:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
       if(AppHelper.getUsuario().getTipo()==1)
        getActivity().getMenuInflater().inflate(R.menu.menu_contexto_item_circular_leitura, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_leitura:
                if (AppHelper.isInternetOnline()) {
                    if (itemCircular.getFlagleitura() == 0) {
                        setCircularLeitura(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), String.valueOf(itemCircular.getIdcircular()));
                    } else {
                        LerCircular();
                    }

                } else {
                    Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_leitores:

                setPopupWindow();

                break;
        }
        return super.onContextItemSelected(item);
    }

    public void getListaCircularLeitura(String idshop, String usuario, String load) {

        new BuscarListaCircularAsync(getActivity(), new BuscarListaCircularAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(EntidadeCircular result) {
                List<Circular> lc=result.getCirculares();
                List<LeitoresCircular> llc= result.getLeitoresCircular();
                if (result != null) {
                    try {
                        if(lc!=null) {
                            CircularDAO circdao = new CircularDAO();
                            for (int i = 0; i < lc.size(); i++) {
                                circdao.save(lc.get(i), AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                            }
                        }
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na lista de circulares.", Toast.LENGTH_SHORT).show();
                    }

                    //getLeitoresCircular();
                    try {
                        if(llc!=null) {
                            LeitoresCircularDAO ldao = new LeitoresCircularDAO();
                            for (int i = 0; i < llc.size(); i++) {
                                ldao.save(llc.get(i),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na leitura da circular.", Toast.LENGTH_SHORT).show();
                    }


                    mDataset = CircularDAO.getNewInstance().listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    mAdapter = new ListaCircularLeituraAdapter(getActivity(), mDataset);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    recyclerViewCircularLeitura.setAdapter(mAdapter);


                } else {
                    Toast.makeText(getActivity(), "Não há listagem de circulares.", Toast.LENGTH_SHORT).show();
                }

            }


        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, usuario, idshop);

    }
    public void getListaCircularLeituraSemDialog(String idshop, String usuario, String load) {

        new BuscarListaCircularSemDialogAsync(getActivity(), new BuscarListaCircularSemDialogAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(EntidadeCircular result) {
                List<Circular> lc=result.getCirculares();
                List<LeitoresCircular> llc= result.getLeitoresCircular();
                if (result != null) {
                    try {
                        if(lc!=null) {
                            CircularDAO circdao = new CircularDAO();
                            for (int i = 0; i < lc.size(); i++) {
                                circdao.save(lc.get(i), AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                            }
                        }
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na lista de circulares.", Toast.LENGTH_SHORT).show();
                    }

                    //getLeitoresCircular();
                    try {
                        if(llc!=null) {
                            LeitoresCircularDAO ldao = new LeitoresCircularDAO();
                            for (int i = 0; i < llc.size(); i++) {
                                ldao.save(llc.get(i),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na leitura da circular.", Toast.LENGTH_SHORT).show();
                    }


                    mDataset = CircularDAO.getNewInstance().listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    mAdapter = new ListaCircularLeituraAdapter(getActivity(), mDataset);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    //recyclerViewCircularLeitura.setAdapter(mAdapter);
                    resetAdapter();
                    circular_swipe_refresh_layout.setRefreshing(false);

                } else if(lc!=null&&lc.size()==0) {
                    Toast.makeText(getActivity(), "Não há listagem de circulares.", Toast.LENGTH_SHORT).show();
                }

            }


        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, usuario, idshop);

    }

    public void setListeners() {
        /*spinnerAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                ano = spinnerAno.getSelectedItem().toString();
                CircularDAO circularDAO = new CircularDAO();
                if (spinnerAno.getSelectedItemPosition() == 0) {
                    ano = null;
                }
                if (ano != null && mes != null) {

                    mDataset = circularDAO.listCircularAnoMes(ano + mes,AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }

                } else if (ano != null && mes == null) {
                    mDataset = circularDAO.listCircularAno(ano + "%",AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }

                } else if (ano == null && mes != null) {
                    mDataset = circularDAO.listCircularMes("____" + mes,AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }

                } else {
                    mDataset = circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }

                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                CircularDAO circularDAO = new CircularDAO();
                switch (spinnerMes.getSelectedItemPosition()) {
                    case 0:
                        mes = null;
                        break;
                    case 1:
                        mes = "00";
                        break;
                    case 2:
                        mes = "01";
                        break;
                    case 3:
                        mes = "02";
                        break;
                    case 4:
                        mes = "03";
                        break;
                    case 5:
                        mes = "04";
                        break;
                    case 6:
                        mes = "05";
                        break;
                    case 7:
                        mes = "06";
                        break;
                    case 8:
                        mes = "07";
                        break;
                    case 9:
                        mes = "08";
                        break;
                    case 10:
                        mes = "09";
                        break;
                    case 11:
                        mes = "10";
                        break;
                    case 12:
                        mes = "11";
                        break;
                    default:
                        mes = null;

                }
                if (ano != null && mes != null) {
                    mDataset = circularDAO.listCircularAnoMes(ano + mes,AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }
                } else if (ano == null && mes != null) {
                    mDataset = circularDAO.listCircularMes("____" + mes,AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }
                } else if (ano != null && mes == null) {
                    mDataset = circularDAO.listCircularAno(ano + "%",AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }
                } else {
                    mDataset = circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser());
                    if(mDataset!=null)
                    {
                        if (mDataset.size()>0)
                        {
                            resetAdapter();
                        }

                    }
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/
        ItemClickSupport.addTo(recyclerViewCircularLeitura).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                itemCircular = mAdapter.getItem(position);

                return false;
            }
        });
        ItemClickSupport.addTo(recyclerViewCircularLeitura).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemCircular = mAdapter.getItem(position);
                if (AppHelper.isInternetOnline()) {
                    if (itemCircular.getFlagleitura() == 0) {
                        setCircularLeitura(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), String.valueOf(itemCircular.getIdcircular()));
                    } else {
                        LerCircular();
                    }

                } else {
                    Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        circular_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppHelper.isInternetOnline()) {
                    circular_swipe_refresh_layout.setRefreshing(true);
                    getListaCircularLeituraSemDialog(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), "0");
                } else {
                    Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void resetAdapter() {
        mAdapter = new ListaCircularLeituraAdapter(getActivity(), mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(mDataset!=null&&mAdapter!=null)
        {
            recyclerViewCircularLeitura.setAdapter(mAdapter);
        }

    }
    public void resetAdapter(List<Circular> mDataset) {
        mAdapter = new ListaCircularLeituraAdapter(getActivity(), mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        if(mDataset!=null&&mAdapter!=null)
        {
            recyclerViewCircularLeitura.setAdapter(mAdapter);
        }

    }

    public static void setInsets(Activity context, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true) / 10, config.getPixelInsetRight(), config.getPixelInsetBottom());
    }

    public void LerCircular() {

        try
        {

            //final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(itemCircular.getNomearquivo()));
            Intent intent = new Intent(getActivity(), PdfCircularActivity.class);
            intent.putExtra("circular", Parcels.wrap(itemCircular));
            getActivity().startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void initDataset() {
        mDataset = new ArrayList<>();
        CircularDAO circularDAO = new CircularDAO();
        if(circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())!=null)
        {
            if(circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()).size()>0)
            {
                mDataset = circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                resetAdapter();
            }
            else
            {
                if (AppHelper.isInternetOnline())
                {
                    getListaCircularLeitura(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()), "0");
                }
                else
                {
                    Toast.makeText(getActivity(), "Favor, verifique conexão com a internet.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void setCircularLeitura(String idshop, String usuario, String idcircular) {

        new FlagLeituraCircularAsync(getActivity(), new FlagLeituraCircularAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(String result) {
                if (result != null && result.equals("1")) {
                    try {
                        LerCircular();
                        CircularDAO circularDAO = new CircularDAO();
                        Circular circular = circularDAO.getCircular(itemCircular.getIdcircular(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                        circular.setFlagleitura(1);
                        circularDAO.save(circular, AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                        mDataset = circularDAO.listCircularPorUsuario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                        resetAdapter();
                        HomeDAO homeDAO = new HomeDAO();
                        Home home = homeDAO.getHome(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                        if(home.getCircularnaolida()>0)
                        {
                            home.setCircularnaolida(home.getCircularnaolida()-1);
                        }
                        homeDAO.save(home);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na leitura da circular.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Erro no retorno da circular.", Toast.LENGTH_SHORT).show();
                }

            }

        }).execute(usuario, idshop, idcircular);

    }

    public void getLeitoresCircular() {
        new LeitoresCircularAsync(getActivity(), new LeitoresCircularAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(List<LeitoresCircular> result) {
                if (result != null) {
                    try {
                        LeitoresCircularDAO ldao = new LeitoresCircularDAO();
                        for (int i = 0; i < result.size(); i++) {
                            ldao.save(result.get(i),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Erro na leitura da circular.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Erro no retorno da circular.", Toast.LENGTH_SHORT).show();
                }
            }


        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public void setPopupWindow() {
        LeitoresCircularDAO ldao = new LeitoresCircularDAO();
        List<LeitoresCircular> leitores = new ArrayList<>();
        leitores=ldao.listLeitoresPorCircular(itemCircular.getIdcircular(),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
        if(leitores.size()!=0) {
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.popwindowcircular, null);
            popupWindow = new PopupWindow(
                    popupView,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    750,
                    true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            // Removes default background.
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


            //popupWindow.setFocusable(true);
            //popupWindow.setTouchable(true);


           //Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            //popupWindow.setBackgroundDrawable( new BitmapDrawable());
            //popupWindow.setOutsideTouchable(true);

            //popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            //popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

            ListView listView = (ListView) popupView.findViewById(R.id.list);
            // create the grid item mapping
            String[] from = new String[]{"col_1", "col_2"};
            int[] to = new int[]{R.id.textViewNomeEmpresa, R.id.textViewCircularDataLeitura};

            // prepare the list of all records
            List<HashMap<String, String>> fillMaps = new ArrayList<>();

            for (int i = 0; i < leitores.size(); i++) {
                HashMap<String, String> map = new HashMap<>();

                map.put("col_1", leitores.get(i).getEmpresa());
                map.put("col_2", DateHelper.toString(leitores.get(i).getData_acessou()));

                fillMaps.add(map);
            }

            // fill in the grid_item layout
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), fillMaps, R.layout.itempopupcircular, from, to);
            listView.setAdapter(adapter);


            /*((Button) popupView.findViewById(R.id.btndismiss))
                    .setOnClickListener(new View.OnClickListener() {

                        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                        public void onClick(View arg0) {


                            popupWindow.dismiss();

                        }


                    });*/
        }
        else
        {
            Toast.makeText(getActivity(),"Não há leitores.",Toast.LENGTH_SHORT).show();
        }

    }

}
