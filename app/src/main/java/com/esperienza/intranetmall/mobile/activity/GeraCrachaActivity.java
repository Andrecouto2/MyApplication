package com.esperienza.intranetmall.mobile.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;


import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.dao.ClienteDAO;
import com.esperienza.intranetmall.mobile.entidade.Cliente;
import com.esperienza.intranetmall.mobile.fragment.RecyclerItemClickListener;
import com.esperienza.intranetmall.mobile.support.ItemClickSupport;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Prefs;
import com.esperienza.intranetmall.mobile.util.ValidaCPF;
import com.google.zxing.WriterException;
import com.google.zxing.common.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by ThinkPad on 16/05/2016.
 */
public class GeraCrachaActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTextCpf;
    private AutoCompleteTextView editTextShop;
    private Button btnGeraQrCode;
    private ImageView imgviewQrCode;
    private ImageView imgviewLogo;
    private ImageView imgviewretorno;
    private TextView txtviewremove;
    private TextView txtviewprincipal;
    private TextView txtviewfrase;
    private LinearLayout linearLayoutCpf;
    private LinearLayout linearLayoutBotao;
    private LinearLayout linearLayoutQrCode;
    private LinearLayout linearLayoutPrincipal;
    private Prefs manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gera_cracha);

        manager = new Prefs();

        editTextCpf = (EditText) findViewById(R.id.edt_cpf_qr_code);
        editTextShop = (AutoCompleteTextView) findViewById(R.id.edit_shop_qr_code);
        btnGeraQrCode = (Button) findViewById(R.id.btn_gera_qrcode);
        imgviewretorno = (ImageView) findViewById(R.id.img_retorno);
        linearLayoutCpf = (LinearLayout) findViewById(R.id.linearcpf);
        linearLayoutBotao = (LinearLayout) findViewById(R.id.linearbotao);
        linearLayoutQrCode = (LinearLayout) findViewById(R.id.linearqrcode);
        linearLayoutPrincipal = (LinearLayout) findViewById(R.id.linearprincipal);
        imgviewQrCode = (ImageView) findViewById(R.id.imageviewqrcodecracha);
        imgviewLogo = (ImageView) findViewById(R.id.imgview_logo_shop);
        txtviewremove = (TextView) findViewById(R.id.textviewremovercracha);
        txtviewprincipal = (TextView) findViewById(R.id.txtviewprincipal);
        txtviewfrase = (TextView) findViewById(R.id.txtviewfrase);

        insereCompleteText();

        events();
        session();

    }
    public void events() {
        btnGeraQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextCpf.getText().toString().isEmpty())
                {
                    try {
                        AppHelper.generateQRCode_general(Encrypt(editTextCpf.getText().toString(),"qrc0d3"),imgviewQrCode);

                        Picasso.with(GeraCrachaActivity.this).load(ClienteDAO.getNewInstance().getClientePorNome(editTextShop.getText().toString()).getImg_cliente()).error(R.drawable.status_error_arquivo).fit().into(imgviewLogo,
                                new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        //progressBar.setVisibility(View.GONE); // download ok
                                    }

                                    @Override
                                    public void onError() {
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                });
                        manager.setString(GeraCrachaActivity.this,"urllogo",ClienteDAO.getNewInstance().getClientePorNome(editTextShop.getText().toString()).getImg_cliente());
                        manager.setString(GeraCrachaActivity.this,"cpfqrcode",editTextCpf.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    linearLayoutPrincipal.setVisibility(View.GONE);
                    linearLayoutQrCode.setVisibility(View.VISIBLE);
                    txtviewremove.setVisibility(View.VISIBLE);
                    txtviewprincipal.setVisibility(View.INVISIBLE);
                    txtviewfrase.setVisibility(View.INVISIBLE);
                    if (getCurrentFocus() != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                else
                {
                    Toast.makeText(GeraCrachaActivity.this,"Inserir CPF.",Toast.LENGTH_LONG).show();
                }

            }
        });

        imgviewretorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtviewremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.setString(GeraCrachaActivity.this,"urllogo","");
                manager.setString(GeraCrachaActivity.this,"cpfqrcode","");
                linearLayoutQrCode.setVisibility(View.GONE);
                linearLayoutPrincipal.setVisibility(View.VISIBLE);
                txtviewremove.setVisibility(View.GONE);
                txtviewprincipal.setVisibility(View.VISIBLE);
                txtviewfrase.setVisibility(View.VISIBLE);
            }
        });

        editTextCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if(ValidaCPF.ValidaCPF(editTextCpf.getText().toString()))
                 {
                     linearLayoutBotao.setVisibility(View.VISIBLE);
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




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
            editTextShop.setAdapter(adapter);
        }

        //edt_shop.setTextIsSelectable(false);
        //edt_shop.performCompletion();
        //edt_shop.performValidation();

        editTextShop.dismissDropDown();
        editTextShop.setOnItemSelectedListener(this);
        editTextShop.setOnItemClickListener(this);

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

        return Base64.encodeToString(results, 0);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ClienteDAO.getNewInstance().getClientePorNome(editTextShop.getText().toString()).getAtivo_cracha() == 1) {
            linearLayoutCpf.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(GeraCrachaActivity.this, "Verifique na administração do shopping\nsobre o crachá digital. ", Toast.LENGTH_LONG).show();
            linearLayoutCpf.setVisibility(View.GONE);
            linearLayoutBotao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void session()
    {
        if(manager.getString(GeraCrachaActivity.this, "cpfqrcode").length()>1)
        {
            try {
                AppHelper.generateQRCode_general(Encrypt(manager.getString(GeraCrachaActivity.this, "cpfqrcode"),"qrc0d3"),imgviewQrCode);

                Picasso.with(GeraCrachaActivity.this).load(manager.getString(GeraCrachaActivity.this, "urllogo")).error(R.drawable.status_error_arquivo).fit().into(imgviewLogo,
                        new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                //progressBar.setVisibility(View.GONE); // download ok
                            }

                            @Override
                            public void onError() {
                                //progressBar.setVisibility(View.GONE);
                            }
                        });
                linearLayoutPrincipal.setVisibility(View.GONE);
                linearLayoutCpf.setVisibility(View.GONE);
                linearLayoutBotao.setVisibility(View.GONE);
                linearLayoutQrCode.setVisibility(View.VISIBLE);
                txtviewremove.setVisibility(View.VISIBLE);
                txtviewprincipal.setVisibility(View.INVISIBLE);
                txtviewfrase.setVisibility(View.INVISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
