package com.esperienza.intranetmall.mobile.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.async.BuscarListaShopAsync;
import com.esperienza.intranetmall.mobile.async.CadastraDispositivoAsync;
import com.esperienza.intranetmall.mobile.dao.ClienteDAO;
import com.esperienza.intranetmall.mobile.dao.DeviceDAO;
import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.gcm.Constants;
import com.esperienza.intranetmall.mobile.gcm.GCM;
import com.esperienza.intranetmall.mobile.gcm.GcmBroadcastReceiver;

import com.esperienza.intranetmall.mobile.gcm.GcmIntentService;
import com.esperienza.intranetmall.mobile.services.MyService;
import com.esperienza.intranetmall.mobile.util.AppExceptionHandler;
import com.esperienza.intranetmall.mobile.util.AppHelper;

import com.esperienza.intranetmall.mobile.util.GPS;
import com.esperienza.intranetmall.mobile.util.PermissionUtils;
import com.esperienza.intranetmall.mobile.util.Prefs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


import java.sql.SQLException;
import java.util.List;


/**
 * Created by BONSUCESSO on 16/11/2015.
 */
public class SplashScreen extends Activity{

    private static int SPLASH_TIME_OUT = 10000;
    public static boolean isinFG = false;
    private Prefs manager;
    public static final Class<? extends Service> CLS = GcmIntentService.class;

    // private Validacao validacao;
    //private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        manager = new Prefs();


        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/





        //Thread.setDefaultUncaughtExceptionHandler(new AppExceptionHandler(this)); Depois André
        AppHelper.setApplicationContext(getApplicationContext());
        setContentView(R.layout.activity_splash_screen);
        AppHelper.onInitApplication(getApplicationContext());



        //String teste2 = AppHelper.getLocalIpAddress();



        /*if(AppHelper.isInternetOnline()&&checkPlayServices())
        {
            getListaShopping();
        }
        else if(!checkPlayServices()&&AppHelper.isInternetOnline())
        {
            initApp();
        }
        else if(!AppHelper.isInternetOnline())
        {
            initAppoffline();
        }*/

        /*if(PermissionUtils.validate(this, 1, PERMISSIONS_STORAGE))
        {
            getListaShopping();
        }
        else
        {

        }*/
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))||((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ||((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ))||((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)))) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                Toast.makeText(this,"É importante aceitar o uso das especificações.",Toast.LENGTH_LONG).show();
                PermissionUtils.validate(this, 1, PERMISSIONS_STORAGE);
                initApp();
                //initAppFinish();
                //Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //intent.setData(Uri.parse("package:" + getPackageName()));
                //startActivity(intent);

            } else {
                // Solicita a permissão
                Toast.makeText(this,"É importante aceitar o uso das especificações.",Toast.LENGTH_LONG).show();
                PermissionUtils.validate(this, 1, PERMISSIONS_STORAGE);
                initApp();
            }

        }
        else {

        getListaShopping();

    }



    }


    private void initAppFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //TODO  validar Usuario

                finish();


            }
        }, SPLASH_TIME_OUT);
    }
    private void initApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //TODO  validar Usuario
                //if(verificapermissoes())
                //{
                    getListaShopping();
               // }
                //else
                //{
                    //finish();
               // }




            }
        }, SPLASH_TIME_OUT);
    }
    public boolean verificapermissoes()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void initAppoffline() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //TODO  validar Usuario


                Intent i;


                i = new Intent(SplashScreen.this, LoginActivity.class);
                ClienteDAO cldao = new ClienteDAO();
                if (cldao.listCliente() == null || cldao.listCliente().size() == 0) {
                    Toast.makeText(getApplicationContext(), "Primeiro acesso: necessário conexão com a internet.", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finish();
                        }
                    }, 5000);
                } else {
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }
    public void getListaShopping(){

        new BuscarListaShopAsync(getApplicationContext(), new BuscarListaShopAsync.Action() {
            @Override
            public void preExecute() {

            }

            /*@Override
            public void getResult(List<Cliente> result) {



            }*/
            @Override
            public void postExecute(List<Cliente> result) {

                ClienteDAO cldao = new ClienteDAO();

                for(int i=0;i<result.size();i++)
                {
                    cldao.saveFromServer(result.get(i));
                }
                Intent i=null;
                String login="";
                String senha="";
                String idshop="";
                login=manager.getString(SplashScreen.this, "login");
                senha=manager.getString(SplashScreen.this,"senha");
                idshop=manager.getString(SplashScreen.this,"idshop");
                i = new Intent(SplashScreen.this, LoginActivity.class);
                if (login.length()>1){
                    i.putExtra("login",login);
                    i.putExtra("senha",senha);
                    i.putExtra("idshop",idshop);
                    i.putExtra("tipo","00");
                    startActivity(i);
                }else{
                    startActivity(i);
                }


                startActivity(i);
                finish();
            }


        }).execute();

    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(SplashScreen.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
                GooglePlayServicesUtil.getErrorDialog(resultCode, SplashScreen.this, PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else {
                Toast.makeText(SplashScreen.this,"Este aparelho não suporta o Google Play Services.",Toast.LENGTH_SHORT).show();
                //finish();
            }
            return false;
        }
        return true;
    }
    public void onPause() {
        super.onPause();
        isinFG = false;
    }

    public void onResume() {
        super.onResume();
        isinFG = true;
    }

    public void onDestroy() {
        super.onDestroy();
        //GCMRegistrar.onDestroy(getApplicationContext());
        isinFG = false;
    }
    /*private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }*/





}
