package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.data.AppDataBaseHelper;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.fragment.AboutDialog;
import com.esperienza.intranetmall.mobile.fragment.FragmentCircularLeitura;
import com.esperienza.intranetmall.mobile.fragment.FragmentImagemFuncionario;
import com.esperienza.intranetmall.mobile.fragment.FragmentListaFuncionarioAdm;
import com.esperienza.intranetmall.mobile.fragment.FragmentListaOrdemServico;
import com.esperienza.intranetmall.mobile.fragment.FragmentQuadrosFuncionais;
import com.esperienza.intranetmall.mobile.fragment.Fragment_Quadrados;
import com.esperienza.intranetmall.mobile.fragment.ListaFuncionariosFragment;


import com.esperienza.intranetmall.mobile.fragment.RecyclerViewFragment;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Prefs;

import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by ThinkPad on 19/11/2015.
 */
public class MenuActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private TextView tvUsuarioNome;
    private TextView tvUsuarioEmail;
    private ImageView imageViewLogoShop;
    private FloatingActionButton fab;
    private static int notification;
    private Prefs manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        manager = new Prefs();
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }




        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);


        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.iconmobilemenu);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();






        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        /*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {

            navigationView.setBackground(getResources().getDrawable(R.drawable.background_menu_principal,getApplicationContext().getTheme()));

        }
        else
        {
            navigationView.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_menu_principal));

        }*/
        MenuItem itemapl= navigationView.getMenu().findItem(R.id.apl);
        String htmlValue = "<span style='font-family: open-sans-light; font-size: 18px; font-color:#1193f5'>Aplicativo&nbsp;</span>";
        itemapl.setTitle(Html.fromHtml("<font size='50' color='#1193f5' face='Open-Sans'><b>Aplicativo</b></font>"));

        //itemhome.setTitle(spanString);

        //itemhome.setTitle(Html.fromHtml("<font font-family='open-sans-light' color='#FFFFFF'>Home</font>"));
        navigationView.setNavigationItemSelectedListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);

        navigationView.setItemIconTintList(null);

        tvUsuarioEmail = (TextView) header.findViewById(R.id.textViewemailuser);
        tvUsuarioNome  = (TextView) header.findViewById(R.id.textViewnomeuser);
        imageViewLogoShop = (ImageView) header.findViewById(R.id.imageViewLogoShop);
        if(AppHelper.getUsuario()!=null)
        {
            tvUsuarioNome.setText(AppHelper.getUsuario().getNomeresponsavel());
            tvUsuarioEmail.setText(AppHelper.getUsuario().getEmail());
            imageViewLogoShop.setImageBitmap(AppHelper.decodeBase64(AppHelper.getUsuario().getImglogoshop()));
        }





       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setLogo(R.drawable.mobilemenu2);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);


        // Prepare list of samples in this dashboard.

        //Eventos dos quadros das funcionalidades
        /* LinearLayout app_layeros = (LinearLayout) findViewById (R.id.layout_ordemservico);
        app_layeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Ordem de Serviço", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout app_layercircular = (LinearLayout) findViewById (R.id.layout_circular);
        app_layercircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Circular", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout app_layerfnc = (LinearLayout) findViewById (R.id.layout_cadastrofuncionario);
        app_layerfnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "Cadastro Funcionário", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(MenuActivity.this, ListaFuncionariosFragment.class));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RecyclerViewFragment fragment = new RecyclerViewFragment();
                transaction.replace(R.id.container, fragment);
                transaction.commit();



            }
        });*/





       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        notification = getIntent().getIntExtra("notification",0);
        if (notification ==1)
        {
            FragmentCircularLeitura.atualizar=1;
            FragmentCircularLeitura fragment = new FragmentCircularLeitura();
            transaction.replace(R.id.container, fragment);
            transaction.commit();

        }
        else if(notification ==2)
        {
            FragmentListaOrdemServico.atualizar=1;
            FragmentListaOrdemServico frag = new FragmentListaOrdemServico();
            Bundle bundle = new Bundle();
            bundle.putInt("status", 0);
            frag.setArguments(bundle);
            transaction.replace(R.id.container, frag);
            transaction.commit();

        }
        /*else if(notification ==3)
        {
            FragmentListaOrdemServico.atualizar=0;
            FragmentListaOrdemServico frag = new FragmentListaOrdemServico();
            Bundle bundle = new Bundle();
            bundle.putInt("status",0);
            frag.setArguments(bundle);
            transaction.replace(R.id.container, frag);
            transaction.commit();
        }*/
        else
        {
            FragmentCircularLeitura.atualizar=0;
            FragmentQuadrosFuncionais fragment = new FragmentQuadrosFuncionais();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
        /*Intent receive_i=getIntent();
        Bundle extras =  receive_i.getExtras();
        if(extras!=null)
        {
            if(extras.getInt("back")==1)
            {
                finish();
                FragmentTransaction fragTransaction= getSupportFragmentManager().beginTransaction();
                FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
                fragTransaction.replace(R.id.container, fragment);
                fragTransaction.addToBackStack(null);
                fragTransaction.commit();
            }
        }*/




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_circular) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_os) {
            FragmentListaOrdemServico.atualizar=0;
            FragmentListaOrdemServico fragment = new FragmentListaOrdemServico();
            Bundle bundle = new Bundle();
            bundle.putInt("status",0);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        } else if (id == R.id.nav_circular) {
            FragmentCircularLeitura.atualizar=0;
            FragmentCircularLeitura fragment = new FragmentCircularLeitura();
            transaction.replace(R.id.container, fragment);
            transaction.commit();

        } else if (id == R.id.nav_cadfnc) {

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

        } else if (id == R.id.nav_about) {

            AboutDialog.showAbout(getSupportFragmentManager());
            //Fragment_Quadrados frag = new Fragment_Quadrados();
            //transaction.replace(R.id.container, frag);
            //transaction.commit();

        } else if (id == R.id.nav_logout) {
            try
            {
                AppDataBaseHelper.getInstance().close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Intent setIntent = new Intent(this, LoginActivity.class);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            manager.setString(MenuActivity.this,"login","");
            manager.setString(MenuActivity.this,"senha","");
            manager.setString(MenuActivity.this,"idshop","");
            manager.setInteger(MenuActivity.this,"positionspinner",0);
            startActivity(setIntent);
            finish();
            //System.exit(0);
        }else if (id == R.id.nav_home) {

            FragmentQuadrosFuncionais fragmentQuadrosFuncionais = new FragmentQuadrosFuncionais();
            transaction.replace(R.id.container, fragmentQuadrosFuncionais);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentImagemFuncionario myFragment = new FragmentImagemFuncionario();
        myFragment.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try {

            if (notification == 0) {
                long now = new Date().getTime();
                if (now - DateHelper.lastdatesession > 900000) {
                        Intent i = new Intent(this, SplashScreen.class);
                    startActivity(i);
                    finish();
                    System.exit(0);
                }
            }else
            {
                notification=0;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void scaleContents(View rootView, View container)
    {
        // Compute the scaling ratio
        float xScale = (float)container.getWidth() / rootView.getWidth();
        float yScale = (float)container.getHeight() / rootView.getHeight();
        float scale = Math.min(xScale, yScale);

        // Scale our contents
        AppHelper.scaleViewAndChildren(rootView, scale);
    }


}
