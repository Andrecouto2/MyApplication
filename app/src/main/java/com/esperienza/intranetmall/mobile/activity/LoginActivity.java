package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.CamposObrigatoriosFncDAO;
import com.esperienza.intranetmall.mobile.async.BuscaHomeAsync;
import com.esperienza.intranetmall.mobile.async.CadastraDispositivoAsync;
import com.esperienza.intranetmall.mobile.async.LoginAsync;
import com.esperienza.intranetmall.mobile.async.RegistrationDeviceAsync;
import com.esperienza.intranetmall.mobile.dao.AprovadoresOSDAO;
import com.esperienza.intranetmall.mobile.dao.ArquivoOSDAO;
import com.esperienza.intranetmall.mobile.dao.CalendarioDAO;
import com.esperienza.intranetmall.mobile.dao.CircularDAO;
import com.esperienza.intranetmall.mobile.dao.ClienteDAO;
import com.esperienza.intranetmall.mobile.dao.ConfigHorarioOSDAO;
import com.esperienza.intranetmall.mobile.dao.DeviceDAO;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.dao.HomeDAO;
import com.esperienza.intranetmall.mobile.dao.LeitoresCircularDAO;
import com.esperienza.intranetmall.mobile.dao.ObservacaoOSDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoSetorDAO;
import com.esperienza.intranetmall.mobile.dao.PessoasAutorizadasOSDAO;
import com.esperienza.intranetmall.mobile.dao.RegraOrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.TipoServicoDAO;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.CamposObrigatoriosFnc;
import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.entidade.EntidadeLogin;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.gcm.Constants;
import com.esperienza.intranetmall.mobile.gcm.GCM;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.GPS;
import com.esperienza.intranetmall.mobile.util.Prefs;
import com.esperienza.intranetmall.mobile.util.SessionManager;
import com.esperienza.intranetmall.mobile.validate.Validate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.location.LocationServices;


import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;




/**
 * Created by BONSUCESSO on 10/11/2015.
 */
public class LoginActivity extends Activity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // UI references.
    private EditText edt_usuario;
    private EditText edt_Password;
    private View mProgressView;
    private Button btn_entrar;
    private ImageButton btn_qrcode;
    private AutoCompleteTextView edt_shop;
    private Validate valid;
    private int pushType=0;
    private ProgressBar progressBar;
    private TextView txtviewCracha;
    private static final byte[] salt = { (byte) 0xA4, (byte) 0x0B, (byte) 0xC8,
            (byte) 0x34, (byte) 0xD6, (byte) 0x95, (byte) 0xF3, (byte) 0x13 };
    /*SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String login = "loginKey";
    public static final String senha = "senhaKey";
    public static final String shop = "shopKey";*/
    private Prefs manager;
    private String lat="0",log="0",alt="0",precisao="0",velocidade="0",gmt="0";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novologin);
        manager = new Prefs();
        //onInitApplication
        AppHelper.onInitApplication(getApplicationContext());
        // Set up the login form.
        edt_usuario = (EditText) findViewById(R.id.edt_usuario);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        btn_entrar = (Button) findViewById(R.id.btn_logar);
        //mProgressView = findViewById(R.id.login_progress);
        btn_qrcode = (ImageButton) findViewById(R.id.imgbtn_qrcode);
        edt_shop = (AutoCompleteTextView) findViewById(R.id.edit_shop);
        progressBar = (ProgressBar) findViewById(R.id.progresslogin);
        txtviewCracha = (TextView) findViewById(R.id.textviewGerarCracha);


        GPS gps = new GPS(getApplicationContext());
        Location location=gps.getLocation();
        if(location!=null)
        {
            lat=""+location.getLatitude();
            log=""+location.getLongitude();
            alt=""+location.getAltitude();
            precisao=""+location.getAccuracy();
            velocidade=""+location.getSpeed();
        }


        /*// Configura o objeto GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Location l = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);*/



        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //gerar cracha
        txtviewCracha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,GeraCrachaActivity.class);
                startActivity(i);
            }
        });
        //
        edt_Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        //Login
                        if(validacampos()) {
                            Logar();
                        }
                        return true;
                    }
                return false;
            }


        });
        btn_qrcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                if (AppHelper.isAvailable(getApplicationContext(), intent)) {
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getApplicationContext(),"Instale o app Barcode Scanner", Toast.LENGTH_SHORT).show();

                    if (AppHelper.isAvailable(getApplicationContext(), intent)) {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.google.zxing.client.android"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Google Play não disponivel", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btn_entrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacampos())
                {
                        //Login
                    Logar();

                }

            }
        });
        insereCompleteText();
        valid = new Validate(this);
        //((TextView) findViewById(R.id.edt_usuario)).setText(AppHelper.getUsuario().getLogin());
        valid.addField(R.id.edt_password, "Informe a senha.", null, false);
        setTitle("Login do usuário");
        startFromNotification();
    }
    public void insereCompleteText(){
        ArrayList<String> listashop = new ArrayList<String>();
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.listCliente()!=null)
        {
            int count = clienteDAO.listCliente().size();
            List<Cliente> listacliente=clienteDAO.listCliente();
            for(int i=0;i<count;i++)
            {
                listashop.add(listacliente.get(i).getNome());
            }

        }

       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listashop);
        if(adapter!=null)
        {
            edt_shop.setAdapter(adapter);
        }

        //edt_shop.setTextIsSelectable(false);
        //edt_shop.performCompletion();
        //edt_shop.performValidation();

        edt_shop.dismissDropDown();

    }
    public Boolean validacampos()
    {
        Boolean valido=true;
        String textoerro="";
        if(TextUtils.isEmpty(edt_shop.getText().toString()))
        {
            textoerro="Favor, insira:\n Nome do shopping \n";
            valido=false;

        }
        if(TextUtils.isEmpty(edt_usuario.getText().toString()))
        {
            if(valido)
            {
                textoerro=" Favor, insira:\n Login do usuário \n";
            }
            else
            {
                textoerro=textoerro+" Login do usuário \n";
            }
            valido=false;

        }
        if(TextUtils.isEmpty(edt_Password.getText().toString()))
        {
            if(valido)
            {
                textoerro="Favor, insira:\n senha do usuário \n";
            }
            else
            {
                textoerro=textoerro+" Senha do usuário";
            }
            valido=false;
        }
        if(!valido)
        {
            //Toast toast = Toast.makeText(getBaseContext(), textoerro,Toast.LENGTH_LONG);
            //toast.show();
            createdialogmessage("Login",textoerro);
        }
        if(valido)
        {
            ClienteDAO cdao = new ClienteDAO();
            Cliente cliente;
            cliente=cdao.getClientePorNome(edt_shop.getText().toString());
            if(cliente==null)
            {
                //Toast toast = Toast.makeText(getBaseContext(),"Escolha a sugestão do aplicativo para o nome do shopping.",Toast.LENGTH_LONG);
                //toast.show();
                createdialogmessage("Login", "Escolha a sugestão do aplicativo para o nome do shopping.");
                valido=false;
            }
        }


        return valido;
    }
    public void Logar()
    {

        if(AppHelper.isInternetOnline())
        {
            /*SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(login, edt_usuario.getText().toString());
            editor.putString(senha, edt_Password.getText().toString());
            editor.putString(shop, edt_shop.getText().toString());
            editor.commit();*/
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente=clienteDAO.getClientePorNome(edt_shop.getText().toString());
            String regId = GCM.getRegistrationId(LoginActivity.this);
            if(regId==null)
            {
                registrationDevice();
            }
            else
            {
                getLogon(edt_usuario.getText().toString(), edt_Password.getText().toString(),String.valueOf(cliente.getIdcliente()),regId,AppHelper.getIMEI(getBaseContext()),AppHelper.getIpAddress(),alt,log,lat,AppHelper.getDetalhesDispositivo(this),AppHelper.timeZone(),DateHelper.currentDate2(),velocidade,precisao);
            }


        }
        else
        {
            //Toast.makeText(getApplicationContext(),"Sem conexão com a internet.",Toast.LENGTH_SHORT).show();
            createdialogmessage("","Sem conexão com a internet.");
        }


    }
    public void getLogon(final String usuario, final String senha, final String idshop, final String iddevice, final String imei, final String ip,final String alt, final String log, final String lat, final String descricao, final String gmt, final String data,final String vel, final String precisao){

       new LoginAsync(LoginActivity.this, new LoginAsync.Action() {


           @Override
           public void onPreExecute() {

               progressBar.setVisibility(View.VISIBLE);
           }

           @Override
           public void onPostExecute(EntidadeLogin result) {

               Usuario u = result.getUsuario();
               Dispositivo d = result.getDispositivo();
               Home h = result.getHome();
               List<RegraOrdemServico> listros=result.getRegraOrdemServicoList();
               List<OrdemServicoSetor> listoss=result.getOrdemServicoSetorList();
               List<Calendario> listca=result.getCalendarioList();
               List<CamposObrigatoriosFnc> listcof = result.getCamposObrigatoriosFncList();
               List<Usuario> listadestino = result.getUsuarioList();

               try
               {
                   Cliente c=ClienteDAO.getNewInstance().getCliente(AppHelper.getIdShop());
                   if(c!=null) {
                       if (c.getAtivo() == 0) {
                           //AprovadoresOSDAO.getNewInstance().removeAll();
                           ArquivoOSDAO.getNewInstance().removeAll();
                           //CalendarioDAO.getNewInstance().removeAll();
                           CircularDAO.getNewInstance().removeAll();
                           //ClienteDAO.getNewInstance().removeAll();
                           //ConfigHorarioOSDAO.getNewInstance().removeAll();
                           DeviceDAO.getNewInstance().removeAll();
                           FuncionarioDAO.getNewInstance().removeAll();
                           HomeDAO.getNewInstance().removeAll();
                           LeitoresCircularDAO.getNewInstance().removeAll();
                           ObservacaoOSDAO.getNewInstance().removeAll();
                           //OrdemServicoSetorDAO.getNewInstance().removeAll();
                           OrdemServicoDAO.getNewInstance().removeAll();
                           PessoasAutorizadasOSDAO.getNewInstance().removeAll();
                           //RegraOrdemServicoDAO.getNewInstance().removeAll();
                           //UsuarioDAO.getNewInstance().removeAll();

                           ClienteDAO.getNewInstance().updateStatusShop();
                           c.setAtivo(1);
                           ClienteDAO.getNewInstance().save(c);
                       }
                   }
               }
               catch (Exception e)
               {
                   e.printStackTrace();
                   progressBar.setVisibility(View.GONE);
               }
               if(u!=null&&u.getIduser()>0)
               {

                   try
                   {
                   //regra
                   if(listros!=null)
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
                           listca.get(i).setIdshop(AppHelper.getIdShop());
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
                   }
                   //campos obrigatórios fnc
                   if(listcof!=null)
                   {
                       int counter = listcof.size();
                       for(int i=0;i<counter;i++)
                       {
                           listcof.get(i).setIduser(AppHelper.getUsuario().getIduser());
                           listcof.get(i).setIdshop(AppHelper.getIdShop());
                           CamposObrigatoriosFncDAO.getNewInstance().save(listcof.get(i),AppHelper.getUsuario().getIduser(),AppHelper.getIdShop());
                       }
                   }
                   //destino
                   if(listadestino!=null)
                   {
                       int counter=listadestino.size();
                       for(int i=0;i<counter;i++)
                       {
                           UsuarioDAO.getNewInstance().saveOS(listadestino.get(i));
                       }
                   }

                       if(h!=null)
                           HomeDAO.getNewInstance().save(h);
                       if(d!=null)
                           DeviceDAO.getNewInstance().save(d);

                       DateHelper.setLastdatesession();
                       Intent i;
                       i = new Intent(LoginActivity.this,MenuActivity.class);
                       i.putExtra("notification", pushType);
                       manager.setString(LoginActivity.this,"login", usuario);
                       manager.setString(LoginActivity.this,"senha", senha);
                       manager.setString(LoginActivity.this,"idshop",idshop);

                       startActivity(i);
                       finish();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                       progressBar.setVisibility(View.GONE);
                       createdialogmessage("","Erro ao efetuar Login.");
                   }




               }
               else
               {
                   //Toast.makeText(getApplicationContext(),result.getNomeresponsavel(),Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
                   if(u!=null)
                   createdialogmessage("",u.getNomeresponsavel());
               }

               progressBar.setVisibility(View.GONE);
           }


        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, usuario, senha, idshop, iddevice,ip,imei,alt,log,lat,descricao,gmt,data,vel,precisao);

    }
    public void getHome(String idshop,String login,String senha)
    {
        new BuscaHomeAsync(LoginActivity.this, new BuscaHomeAsync.Action()
        {

            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Home result) {
                try
                {
                    if(result!=null)
                    {
                        HomeDAO homeDAO = new HomeDAO();
                        homeDAO.save(result);

                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Logar();

            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, idshop, login, senha);
    }
    /*public void getHome()
    {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente=clienteDAO.getClientePorNome(edt_shop.getText().toString());
        getHome(String.valueOf(cliente.getIdcliente()), edt_usuario.getText().toString(), edt_Password.getText().toString());
    }*/
    public void getRegistrationDevice(String idshop,String logon,String senha,String iddevice){

        new CadastraDispositivoAsync(LoginActivity.this, new CadastraDispositivoAsync.Action() {


            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(Dispositivo result) {

                if(result!=null)
                {
                    try
                    {

                        DeviceDAO devdao = new DeviceDAO();
                        devdao.save(result);
                        Intent i;
                        i = new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }




        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, idshop, logon, senha, iddevice);

    }
    public void registrationDevice()
    {
        new RegistrationDeviceAsync(this, new RegistrationDeviceAsync.Action(){
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(String result) {
                if(result.equals("0"))
                {
                    //createdialogmessage("","Necessário instalação do Google Play Services.");
                    Toast.makeText(LoginActivity.this,"Necessário instalação do Google Play Services.",Toast.LENGTH_SHORT);
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente cliente=clienteDAO.getClientePorNome(edt_shop.getText().toString());
                    getLogon(edt_usuario.getText().toString(), edt_Password.getText().toString(),String.valueOf(cliente.getIdcliente()),"0","0","0","0","0","0","0","0","0","0","0");
                }
                else
                {
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente cliente=clienteDAO.getClientePorNome(edt_shop.getText().toString());
                    //getLogon(edt_usuario.getText().toString(), edt_Password.getText().toString(),String.valueOf(cliente.getIdcliente()),result);
                }

            }
        }).execute();
    }
    private void registrar()
    {
        new Thread() {
            @Override
            public void run() {
                super.run();

                String regId = GCM.getRegistrationId(LoginActivity.this);
                if (regId == null) {
                    // Faz o registro e pega o registration id
                    regId = GCM.register(LoginActivity.this, Constants.PROJECT_NUMBER);
                    //setText("Registrado com sucesso.\n" + regId);
                } else {
                    //toast("Você já está registrado.");
                }

                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente=clienteDAO.getClientePorNome(edt_shop.getText().toString());
                if(cliente==null)
                {
                    cliente = new Cliente();
                    cliente.setIdcliente(0);
                }
                getRegistrationDevice(String.valueOf(cliente.getIdcliente()),edt_usuario.getText().toString(),edt_Password.getText().toString(),regId);


            }
        }.start();
    }
    /*public String getDevice()
    {
        //inicio
        boolean ok = checkPlayServices();
        if (ok) {
            // Já está registrado
            String regId = GCM.getRegistrationId(LoginActivity.this);
            if (regId != null && !regId.equals("")) {

               return regId;

            } else  {
                return registrar();
            }
        }
        return null;        //fim
    }*/
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(LoginActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            /*if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
                GooglePlayServicesUtil.getErrorDialog(resultCode, LoginActivity.this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(LoginActivity.this,"Este aparelho não suporta o Google Play Services.",Toast.LENGTH_SHORT).show();
                //finish();
            }*/
            return false;
        }
        return true;
    }
    public void createdialogmessage(String titulo,String msg)
    {
        final Dialog alert = new Dialog(this);
        alert.setContentView(R.layout.dialogbox_login);
        alert.setTitle(titulo);
        Button button2 = (Button) alert.findViewById(R.id.button2);
        TextView textView = (TextView) alert.findViewById(R.id.textView1);
        textView.setText(msg);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }
    public void startFromNotification()
    {
        String tipo="";
        if(getIntent().getStringExtra("tipo")!=null) {
            tipo = getIntent().getStringExtra("tipo");
            if (!tipo.isEmpty()) {
                if (tipo.equals("circular")) {
                    edt_usuario.setText(getIntent().getStringExtra("login"));
                    edt_Password.setText(getIntent().getStringExtra("senha"));
                    edt_shop.setText(ClienteDAO.getNewInstance().getCliente(Integer.parseInt(getIntent().getStringExtra("idshop"))).getNome());
                    pushType=1;//1=circular
                    Logar();
                }
                else if(tipo.equals("00"))
                {
                    edt_usuario.setText(getIntent().getStringExtra("login"));
                    edt_Password.setText(getIntent().getStringExtra("senha"));
                    edt_shop.setText(ClienteDAO.getNewInstance().getCliente(Integer.parseInt(getIntent().getStringExtra("idshop"))).getNome());
                    pushType=0;
                    Logar();
                }
                else {
                    edt_usuario.setText(getIntent().getStringExtra("login"));
                    edt_Password.setText(getIntent().getStringExtra("senha"));
                    edt_shop.setText(ClienteDAO.getNewInstance().getCliente(Integer.parseInt(getIntent().getStringExtra("idshop"))).getNome());
                    pushType=2;//2=ordem serviço
                    Logar();
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra("SCAN_RESULT");
                // Este é o texto do código de barras
                try {
                    String re=Decrypt(s,"qrc0d3");
                    String[] split = null;
                    split=re.split("\\|");
                    if(split!=null&&split.length==3)
                    {
                        edt_usuario.setText(split[1]);
                        edt_Password.setText(split[2]);
                        edt_shop.setText(ClienteDAO.getNewInstance().getCliente(Integer.parseInt(split[0])).getNome());
                    }
                    else
                    {
                        Toast.makeText(this,"QR CODE não identificado",Toast.LENGTH_SHORT).show();
                    }

                    Logar();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Código não identificado.",Toast.LENGTH_LONG).show();
                }

            }

        }
    }
    public String Decrypt(String text, String key) throws Exception{
        byte[] results=null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length) len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);


          results = cipher.doFinal(Base64.decode(text, 0));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new String(results,"UTF-8");
    }
    private  String Encrypt(String text, String key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes= new byte[16];
        byte[] b= key.getBytes("UTF-8");
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        Log.v("GET Result from  final:",results.toString());

        return Base64.encodeToString(results, 1);

    }
    private byte[] generateKey(String key)
    {
        SecretKeySpec sks=null;
        try
        {
              sks = new SecretKeySpec(key.getBytes(),"AES");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sks.getEncoded();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

