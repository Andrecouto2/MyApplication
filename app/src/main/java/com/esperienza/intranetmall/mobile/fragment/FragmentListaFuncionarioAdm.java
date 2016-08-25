
package com.esperienza.intranetmall.mobile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.ListaFuncionarioAdapter;
import com.esperienza.intranetmall.mobile.async.BuscarListaFuncinarioSemDialogAsync;
import com.esperienza.intranetmall.mobile.async.BuscarListaFuncionarioAsync;
import com.esperienza.intranetmall.mobile.async.EnvioFuncionarioAsync;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.fonts.SimpleDividerItemDecoration;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.support.ItemClickSupport;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Prefs;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 22/07/2016.
 */
public class FragmentListaFuncionarioAdm extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private int idFuncionario=0;
    private LinearLayout linearLayoutInformativo;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;

    protected RecyclerView mRecyclerView;
    protected ListaFuncionarioAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Funcionario> mDataset;
    public   Funcionario itemfnc=null;
    public PopupWindow popupWindow;
    protected SwipeRefreshLayout swipeRefreshLayoutFnc;
    protected FloatingActionButton fabfnc;
    protected Spinner spinnerLojas;
    private List<Integer> lojasid;
    private List<Integer> shopid;
    private int idspinner;
    private Prefs manager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_funcionario_adm, container, false);
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        spinnerLojas = (Spinner) rootView.findViewById(R.id.spinner_lojas);
        int dpValue = 16; // margin in dips
        float d = getActivity().getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        spinnerLojas.setPadding(margin,0,0,0);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        fabfnc = (FloatingActionButton) rootView.findViewById(R.id.fabfnc);
        swipeRefreshLayoutFnc = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_fnc);
        swipeRefreshLayoutFnc.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            TypedValue typed_value = new TypedValue();
            getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
            swipeRefreshLayoutFnc.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
        }
        manager = new Prefs();
        linearLayoutInformativo = (LinearLayout) rootView.findViewById(R.id.linearinformativo);

        /*if(!AppHelper.getSizeScreen().equals("small"))
        {
            linearLayoutInformativo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopup();
                }
            });
        }*/


        //session
        DateHelper.setLastdatesession();

        //spinnerStatusFnc = (Spinner) rootView.findViewById(R.id.spinnerStatusFnc);
        //ScrollView scrollViewRecycler = (ScrollView) rootView.findViewById(R.id.scrollViewRecycler);
        //spinnerStatusFnc.setClipToPadding(false);
        //setInsets(getActivity(), scrollViewRecycler);
        mRecyclerView.setClipToPadding(false);
        //Util.setInsets(getActivity(), mRecyclerView);


        registerForContextMenu(mRecyclerView);
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);

        animation.setDuration(800);

        mRecyclerView.startAnimation(animation);

        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                itemfnc = (Funcionario) mAdapter.getItem(position);
                idFuncionario = itemfnc.getIdfnc();

                return false;
            }
        });
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                itemfnc = mAdapter.getItem(position);
                idFuncionario = itemfnc.getIdfnc();
                Bundle bundle = new Bundle();
                bundle.putParcelable("fnc", Parcels.wrap(itemfnc));
                bundle.putInt("idFuncionario", idFuncionario);
                FragmentDadosFuncionario.funcionario=itemfnc;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                FragmentDadosFuncionario fragmentDadosFuncionario = new FragmentDadosFuncionario();
                fragmentDadosFuncionario.setArguments(bundle);
                ft.replace(R.id.container, fragmentDadosFuncionario);
                ft.commit();


            }
        });


        /*spinnerStatusFnc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An spinnerItem was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                */
                /*switch (pos)
                {
                    case 0:
                        initDataset(0);
                        //mRecyclerView.getLayoutManager().onRestoreInstanceState(mAdapter);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);

                        break;
                    case 1:
                        //initDataset(1);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);

                        break;
                    case 2:
                        initDataset(2);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 3:
                        initDataset(3);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 4:
                        initDataset(4);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 5:
                        initDataset(5);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 6:
                        initDataset(6);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 7:
                        initDataset(7);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 8:
                        initDataset(8);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 9:
                        initDataset(9);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        // Set CustomAdapter as the adapter for RecyclerView.
                        mRecyclerView.setAdapter(mAdapter);
                        break;

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        });*/


        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        //mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        //mRecyclerView.setAdapter(mAdapter);
        try
        {
            Home home = HomeDAO.getNewInstance().getHome(AppHelper.getUsuario().getIduser(), AppHelper.getIdShop());
            final Integer qntfnc = Integer.valueOf(home.getQtdfuncionario());
            final Integer qntdataset = mDataset.size();
            if (qntdataset < qntfnc) {
                getListaFncSemDialog(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        /*mLinearLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.linear_layout_rb);
        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });*/

        /*mGridLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.grid_layout_rb);
        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });*/
        setSpinnerLojas();
        events();
        setHasOptionsMenu(true);




        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
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

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private List<Funcionario> initdata(List<Funcionario> data,int status)
    {
        int tam=data.size();
        List<Funcionario> retorno = new ArrayList<>();
        for (int i=0;i<tam;i++)
        {
            if(data.get(i).getId_cracha_tipo()==status)
            {
                retorno.add(data.get(i));
            }
        }
        return retorno;
    }
    private void initDataset(int flag,int iduser) {
        mDataset = new ArrayList<Funcionario>();
        FuncionarioDAO fncdao = new FuncionarioDAO();
        switch (flag)
        {
            case 0:

                mDataset = fncdao.listFuncionario(iduser,AppHelper.getIdShop());


                if(mDataset==null||mDataset.size()==0)
                {
                    getListaFnc(String.valueOf(iduser),String.valueOf(AppHelper.getIdShop()));
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mRecyclerView.setAdapter(mAdapter);
                }

                break;
            case 1:
                mDataset = fncdao.listFuncionarioStatus(2,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 2:
                mDataset = fncdao.listFuncionarioStatus(7,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 3:
                mDataset = fncdao.listFuncionarioStatus(8,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 4:
                mDataset = fncdao.listFuncionarioStatus(9,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 5:
                mDataset = fncdao.listFuncionarioStatus(4,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 6:
                mDataset = fncdao.listFuncionarioStatus(3,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 7:
                mDataset = fncdao.listFuncionarioStatus(5,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 8:
                mDataset = fncdao.listFuncionarioStatus(6,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 9:
                mDataset = fncdao.listFuncionarioStatus(1,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                // Set CustomAdapter as the adapter for RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
        /*int size=mDataset.size();
        ArrayList<String> arraylist= new ArrayList<>();
        Boolean flag1=false,flag2=false,flag3=false,flag4=false,flag5=false,flag6=false,flag7=false,flag8=false,flag9=false;
        for(int i=0;i<size;i++)
        {
            switch (mDataset.get(i).getId_cracha_tipo())
            {
                case 1:
                    if(!flag1)
                    {
                        arraylist.add("Novo Cadastro");
                        flag1=true;
                    }
                    break;
                case 2:
                    if(!flag2)
                    {
                        arraylist.add("Aguardando aprovação do Depto. de Segurança");
                        flag2=true;
                    }

                    break;
                case 3:
                    if(!flag3)
                    {
                        arraylist.add("Foto fora do padrão. Reenviar");
                        flag3=true;
                    }

                    break;
                case 4:
                    if(!flag4)
                    {
                        arraylist.add("Crachá na ADM. Aguardando retirada");
                        flag4=true;
                    }

                    break;
                case 5:
                    if(!flag5)
                    {
                        arraylist.add("Funcionário Ativo");
                        flag5=true;
                    }

                    break;
                case 6:
                    if(!flag6)
                    {
                        arraylist.add("Funcionário Inativo");
                        flag6=true;
                    }

                    break;
                case 7:
                    if(!flag7)
                    {
                        arraylist.add("Aprovado. Aguardando Emissão do Crachá");
                        flag7=true;
                    }

                    break;
                case 8:
                    if(!flag8)
                    {
                        arraylist.add("Cadastro reprovado pelo Depto. de Segurança");
                        flag8=true;
                    }

                    break;
                case 9:
                    if(!flag9)
                    {
                        arraylist.add("Crachá em emissão");
                        flag9=true;
                    }

                    break;
            }
        }
        String[] ls = new String[9];
        for(int i=0;i<arraylist.size();i++)
        {
            ls[i]=arraylist.get(i).toString();

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, ls);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusFnc.setAdapter(adapter);*/


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_cadfnc, menu);
        MenuItem item = menu.findItem(R.id.action_search_fnc);
        SearchView searchView = null;
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        resetAdapter();
                        //mAdapter.setFilter(mDataset);
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
                List<Funcionario> listafnc=new ArrayList<>();
                listafnc=filter(mDataset,newText);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    Toast.makeText(getActivity(),"Cadastro não encontrado.",Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        };
    }
    private List<Funcionario> filter(List<Funcionario> data,String query)
    {
        List<Funcionario> retorno = new ArrayList<>();
        int tam=data.size();
        for(int i=0;i<tam;i++)
        {
            String nome=data.get(i).getNome_lojista();
            String datanasc="";
            if(data.get(i).getDatanasc()!=null)
            {
                datanasc= DateHelper.toString(data.get(i).getDatanasc());
            }
            String cpf=data.get(i).getCpf();
            String cadastro=DateHelper.toString(data.get(i).getDatacadastro());

            if(nome.toLowerCase().contains(query.toLowerCase())||datanasc.toLowerCase().contains(query.toLowerCase())||cadastro.toLowerCase().contains(query.toLowerCase())||cpf.toLowerCase().contains(query.toLowerCase()))
            {
                retorno.add(data.get(i));
            }

        }

        return retorno;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Funcionario> listafnc=new ArrayList<>();
        switch(item.getItemId()) {


           /* case R.id.action_novofnc:

                Bundle bundle = new Bundle();
                bundle.putInt("idFuncionario", 0);


                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentDadosFuncionario fragment = new FragmentDadosFuncionario();
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();



                return true;
            case R.id.action_sync:
                if(AppHelper.isInternetOnline()) {
                    getListaFnc(String.valueOf(AppHelper.getUsuario().getIduser()), String.valueOf(AppHelper.getIdShop()));
                }else{
                    Toast.makeText(getActivity(),"Checar conexão com a internet.",Toast.LENGTH_SHORT).show();
                }
                return true;*/

            case R.id.action_todos:
                mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.action_1:
                listafnc=initdata(mDataset,1);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.action_2:
                listafnc=initdata(mDataset,2);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_3:
                listafnc=initdata(mDataset,3);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_4:
                listafnc=initdata(mDataset,4);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_5:
                listafnc=initdata(mDataset,5);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_6:
                listafnc=initdata(mDataset,6);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_7:
                listafnc=initdata(mDataset,7);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_8:
                listafnc=initdata(mDataset,8);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_9:
                listafnc=initdata(mDataset,9);
                if(listafnc.size()>0)
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),listafnc);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                    mRecyclerView.setAdapter(mAdapter);
                    Toast.makeText(getActivity(),"Não há cadastro com status escolhido.",Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void getListaFnc(String idshop,String usuario){

        new BuscarListaFuncionarioAsync(getActivity(), new BuscarListaFuncionarioAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(List<Funcionario> result) {
                if(result!=null&&result.get(0)!=null)
                {
                    FuncionarioDAO fncdao = new FuncionarioDAO();
                    for(int i=0;i<result.size();i++)
                    {
                        fncdao.save(result.get(i),result.get(i).getIduser(),AppHelper.getIdShop());

                    }
                    //mDataset=fncdao.listFuncionario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                    mDataset.addAll(result);
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),result);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mRecyclerView.setAdapter(mAdapter);


                }
                else
                {
                    mRecyclerView.setAdapter(null);
                    Toast.makeText(getActivity(),"Não há funcionários cadastrados.",Toast.LENGTH_SHORT).show();
                }

            }




        }).execute(idshop, usuario);

    }
    public void getListaFncSemDialog(String idshop,String usuario){

        new BuscarListaFuncinarioSemDialogAsync(getActivity(), new BuscarListaFuncinarioSemDialogAsync.Action() {


            @Override
            public void preExecute() {
                swipeRefreshLayoutFnc.setRefreshing(true);
            }

            @Override
            public void postExecute(List<Funcionario> result) {
                if(result!=null&&result.get(0)!=null)
                {
                    FuncionarioDAO fncdao = new FuncionarioDAO();
                    for(int i=0;i<result.size();i++)
                    {
                        fncdao.save(result.get(i),result.get(i).getIduser(),AppHelper.getIdShop());

                    }
                    /*if(AppHelper.getUsuario().getTipo()==1)
                    {
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),FuncionarioDAO.getNewInstance().listFuncionariosShop(AppHelper.getIdShop()));
                    }
                    else
                    {
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),FuncionarioDAO.getNewInstance().listFuncionario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop()));
                    }*/
                    mAdapter = new ListaFuncionarioAdapter(getActivity(),FuncionarioDAO.getNewInstance().listFuncionario(idspinner,AppHelper.getIdShop()));
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mDataset.addAll(result);
                    mRecyclerView.setAdapter(mAdapter);


                }
                else
                {
                    Toast.makeText(getActivity(),"Não há funcionários cadastrados.",Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayoutFnc.setRefreshing(false);
            }




        }).execute(idshop, usuario);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.menu_contexto_item_funcionario, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putInt("idFuncionario",idFuncionario);
        bundle.putParcelable("fnc", Parcels.wrap(itemfnc));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.menu_editar:
                FragmentDadosFuncionario.funcionario=itemfnc;
                FragmentDadosFuncionario fragmentDadosFuncionario = new FragmentDadosFuncionario();
                fragmentDadosFuncionario.setArguments(bundle);
                ft.replace(R.id.container, fragmentDadosFuncionario);
                ft.commit();

                break;
            case R.id.menu_enviar:

                if(AppHelper.isInternetOnline())
                {
                    try
                    {
                        if(itemfnc.getStatusEnvio()==1||itemfnc.getStatusEnvio()==2)
                        {
                            setEnvioFnc(String.valueOf(AppHelper.getIdShop()), itemfnc);

                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Cadastro já atualizado.",Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(),"Erro no envio dos dados do funcionário.",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),"Favor, verificar conexão com a internet.",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.menu_remover:
                try
                {
                    if(FuncionarioDAO.getNewInstance().remove(itemfnc,AppHelper.getUsuario().getIduser(),AppHelper.getIdShop())==1)
                    {
                        initDataset(0,idspinner);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        mRecyclerView.setAdapter(mAdapter);
                        Toast.makeText(getActivity(),"Cadastro removido com sucesso!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Não é permitido remover o cadastro!",Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Erro na remoção do cadastro!",Toast.LENGTH_SHORT).show();
                }


                break;
        }
        return super.onContextItemSelected(item);
    }
    public void setEnvioFnc(String idshop,Funcionario fnc){

        new EnvioFuncionarioAsync(this.getActivity(), new EnvioFuncionarioAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(String result)
            {

                if(result!=null||result.equals("0"))
                {
                    if(result.equals("-1"))
                    {
                        Toast.makeText(getActivity(),"Não é possível cadastrar funcionário com o mesmo CPF.",Toast.LENGTH_SHORT).show();                        return;
                    }
                    else
                    {

                        FuncionarioDAO fncdao = new FuncionarioDAO();
                        Funcionario fnc;
                        fnc= itemfnc;
                        int idold=itemfnc.getIdfnc();
                        fnc.setIdfnc(Integer.valueOf(result));
                        fnc.setStatusEnvio(3);
                        if(AppHelper.getUsuario().getTipo()==1)
                        {
                            fncdao.save(fnc, itemfnc.getIduser(), idold, AppHelper.getIdShop());
                        }
                        else
                        {
                            fncdao.save(fnc, AppHelper.getUsuario().getIduser(), idold, AppHelper.getIdShop());
                        }

                       initDataset(0,idspinner);
                        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
                        mRecyclerView.setAdapter(mAdapter);
                        Toast.makeText(getActivity(),"Dados do funcionário enviado com sucesso!",Toast.LENGTH_SHORT).show();


                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Erro ao enviar dados do funcionário.",Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        }).executeStart(idshop, String.valueOf(fnc.getIduser()),String.valueOf(fnc.getIdfnc()));

    }
    public int ConversaoStringToInt(String value)
    {
        int myNum=0;
        try {
            myNum = Integer.parseInt(value);
        } catch(NumberFormatException nfe) {

            Log.d("Recycler","Erro String to int"+nfe);
        }
        return myNum;
    }
    public void resetAdapter()
    {
        FuncionarioDAO fncdao = new FuncionarioDAO();
        mDataset=fncdao.listFuncionario(AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
        mAdapter = new ListaFuncionarioAdapter(getActivity(),mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
    }
    public void events()
    {
        fabfnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idFuncionario", 0);


                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentDadosFuncionario fragment = new FragmentDadosFuncionario();
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        swipeRefreshLayoutFnc.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh()
                    {
                        if(AppHelper.isInternetOnline()) {
                            getListaFncSemDialog(String.valueOf(idspinner), String.valueOf(AppHelper.getIdShop()));
                        }else{
                            Toast.makeText(getActivity(),"Checar conexão com a internet.",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );
        spinnerLojas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idspinner=lojasid.get(position);
                initDataset(0,idspinner);
                manager.setInteger(getActivity(),"positionspinner", position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void setPopup()
    {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupwindowfuncionario, null);
        popupWindow = new PopupWindow(
                popupView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                550,
                true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 650);

        TextView textView = (TextView) popupView.findViewById(R.id.txtinformativofnc);;

        SpannableString ss = new SpannableString(textView.getText().toString());


        Drawable img = getResources().getDrawable(R.drawable.legenda_atualizar_infos);
        img.setBounds(0, 0, img.getIntrinsicWidth()-20, img.getIntrinsicHeight()-20);

        textView.setText(ss);


    }
    public void setSpinnerLojas()
    {
        int pos=0;
        List<String> lojas =new ArrayList<>();
        lojasid =new ArrayList<>();
        List<Usuario> llojas=UsuarioDAO.getNewInstance().getListTipoUsuario(2,AppHelper.getIdShop());
        int counter2=llojas.size();
        for(int j=0;j<counter2;j++)
        {
            lojas.add(j,llojas.get(j).getEmpresa()+(llojas.get(j).getLogin()!=null&&!llojas.get(j).getLogin().equals("null")?" - "+llojas.get(j).getLogin():""));
            lojasid.add(j,llojas.get(j).getIduser());
            /*if(llojas.get(j).getIduser()==AppHelper.getUsuario().getIduser())
            {
                pos=j;
            }*/
        }
        //
        List<String> shop =new ArrayList<>();
        shopid = new ArrayList<>();


        List<Usuario> lshop = UsuarioDAO.getNewInstance().getListTipoUsuario(1,AppHelper.getIdShop());

        int counter=lshop.size();
        for(int i=0;i<counter;i++)
        {
            shop.add(i,lshop.get(i).getNomeresponsavel()+" - "+ lshop.get(i).getLogin());
            lojasid.add(counter2+i,lshop.get(i).getIduser());
            /*if(lshop.get(i).getIduser()==AppHelper.getUsuario().getIduser())
            {
                pos=counter2+lshop.get(i).getIduser()-1;
            }*/
        }
        lojas.removeAll(shop);
        lojas.addAll(shop);



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, lojas);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spinnerLojas.setAdapter(dataAdapter);

        ////-------------------------------------------------------------------------


        //ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, lojas);

        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
       // spinnerLojas.setAdapter(dataAdapter2);
        spinnerLojas.setSelection(manager.getInteger(getActivity(),"positionspinner"));
    }
}
