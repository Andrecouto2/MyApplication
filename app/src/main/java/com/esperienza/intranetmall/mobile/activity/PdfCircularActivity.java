package com.esperienza.intranetmall.mobile.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.PermissionUtils;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.AbstractCollection;

/**
 * Created by ThinkPad on 28/03/2016.
 */
public class PdfCircularActivity extends AppCompatActivity {

    private WebView webViewpdf;
    private FloatingActionButton actionButton;
    private Circular circular;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_circular);

        webViewpdf = (WebView) findViewById(R.id.webviewpdfcircular);
        progress = (ProgressBar) findViewById(R.id.progress);

        //actionButton = (FloatingActionButton) findViewById(R.id.fabdownloadpdf);
        /*actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webViewpdf.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    createWebPrintJob(webViewpdf);
                }
                else
                {
                    Toast.makeText(PdfCircularActivity.this,"Necessário API TARGET 19 ou superior.",Toast.LENGTH_SHORT).show();
                }*/

            /*
                URL url = null;
                URI uri = null;
                File extStore = Environment.getExternalStorageDirectory();
                String mPath = extStore.getAbsolutePath() + "/Download/bgs";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File file = new File(getApplication().getFilesDir(),"AGNGSAMIS1.pdf");
                Uri uri1 = Uri.fromFile(new File("/storage/emulated/0/Download/Adobe Acrobat/", "AGNGSAMIS1.pdf"));
                //AKI
                if(file.exists())
                {
                    int i=0;
                }

                //url = new URL("http://www.google.com"); //Some instantiated URL object
                //uri = url.toURI();
                //sendIntent.setDataAndType(Uri.parse(circular.getNomearquivo()), "application/pdf");
                //Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+circular.getNomearquivo());
                URI resolved=null;
                try {
                    url = new URL(circular.getNomearquivo());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                // Create a URI object from the String object returned by the URL object
                try {
                    uri = new URI(url.toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    resolved = new URI(circular.getNomearquivo());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                //uri=file.toURI();
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri1);
                sendIntent.setType("application/*");
                startActivityForResult(sendIntent,0);
            }
        });*/
        Bundle bundle = getIntent().getExtras();
        circular =  Parcels.unwrap( bundle.getParcelable("circular"));
        Util.setInsets(this,webViewpdf);
        //request escrita externa
        String[] permissoes = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                Toast.makeText(this,"É importante aceitar acesso a memória externa do dispositivo.",Toast.LENGTH_LONG).show();
            } else {
                // Solicita a permissão
                PermissionUtils.validate(this, 0, permissoes);
            }
        } else {
            // Tudo OK, podemos prosseguir.
            downloadpdf(circular.getNomearquivo());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setTitle("O.S: " + os.getId_os());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }



    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
    public void init()
    {
        if(AppHelper.isInternetOnline()) {
            //getWindow().requestFeature(Window.FEATURE_PROGRESS);

            webViewpdf.getSettings().setJavaScriptEnabled(true);
            webViewpdf.getSettings().setBuiltInZoomControls(true);
            webViewpdf.getSettings().setDisplayZoomControls(false);
            webViewpdf.getSettings().setSaveFormData(false);
            //webViewpdf.getSettings().setPluginState(WebSettings.PluginState.ON);
            webViewpdf.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webViewpdf.getSettings().setLoadWithOverviewMode(true);
            webViewpdf.getSettings().setUseWideViewPort(true);



            webViewpdf.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, circular.getNomearquivo());
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    init();
                }
            });

            String url = "http://docs.google.com/gview?embedded=true&url=" + circular.getNomearquivo();
            String googleDocs = "https://docs.google.com/viewer?url=";
            webViewpdf.loadUrl(url);
            if (Build.VERSION.SDK_INT >= 19) {
                webViewpdf.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            }
            webViewpdf.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                    super.onPageStarted(webview, url, favicon);
                    // Liga o progress
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView webview, String url) {
                    // Desliga o progress
                    progress.setVisibility(View.INVISIBLE);
                    // Termina a animação do Swipe to Refresh


                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d("intramall", "webview url: " + url);
                    if (url != null && url.endsWith("sobre.htm")) {
                        //AboutDialog.showAbout(getFragmentManager());
                        // Retorna true para informar que interceptamos o evento
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

            });


        }
        else
        {
            Toast.makeText(this,"Sem conexão com a internet.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf_circular, menu);
        MenuItem shareMenuItem = menu.findItem(R.id.menu_item_share);
        if (shareMenuItem != null) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            File extStore = Environment.getExternalStorageDirectory();
            String mPath = extStore.getAbsolutePath()+"/pdf_circular_"+AppHelper.getIdShop()+"/";
            String[] split= circular.getNomearquivo().split("/");
            Uri uri = Uri.fromFile(new File(mPath, split[5]));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareActionProvider.setShareIntent(shareIntent);
        }

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        init();
    }
    public void downloadpdf(String url)
    {
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath()+"/pdf_circular_"+AppHelper.getIdShop()+"/";
        File folder = new File(mPath);
        if(!folder.exists())
        {
            folder.mkdir();
        }
        String[] split= url.split("/");
        File file = new File(mPath+split[5]);
        if(!file.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDestinationInExternalPublicDir("/pdf_circular_"+AppHelper.getIdShop(), split[5]);
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
        }

    }


}
