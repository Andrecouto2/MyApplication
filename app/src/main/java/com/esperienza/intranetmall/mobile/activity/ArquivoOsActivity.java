package com.esperienza.intranetmall.mobile.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Arquivos;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

import java.io.File;

/**
 * Created by ThinkPad on 26/04/2016.
 */
public class ArquivoOsActivity extends AppCompatActivity {

    private WebView webViewarqos;
    private ArquivoOS arquivos;
    private ProgressBar progress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquivos_ordem_servico);

        webViewarqos = (WebView) findViewById(R.id.webviewarqos);
        progress = (ProgressBar) findViewById(R.id.progress_arq_os);

        Bundle bundle = getIntent().getExtras();
        arquivos =  Parcels.unwrap(bundle.getParcelable("arquivos"));
        Util.setInsets(this, webViewarqos);
        url=arquivos.getUrlarquivo()+arquivos.getIdarquivo()+"."+arquivos.getExtensao();
        downloadarq(url);
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
        init();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf_circular, menu);
        MenuItem shareMenuItem = menu.findItem(R.id.menu_item_share);
        if (shareMenuItem != null) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/*");
            File extStore = Environment.getExternalStorageDirectory();
            String mPath = extStore.getAbsolutePath()+"/arq_os_"+AppHelper.getIdShop()+"/";

            Uri uri = Uri.fromFile(new File(mPath, arquivos.getIdarquivo()+"."+arquivos.getExtensao()));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareActionProvider.setShareIntent(shareIntent);
        }

        return super.onCreateOptionsMenu(menu);
    }
    public void init() {

        if (AppHelper.isInternetOnline())
        {
            webViewarqos.getSettings().setJavaScriptEnabled(true);
            webViewarqos.getSettings().setBuiltInZoomControls(true);
            webViewarqos.getSettings().setDisplayZoomControls(false);
            webViewarqos.getSettings().setSaveFormData(false);
            //webViewarqos.setInitialScale(1);
            webViewarqos.getSettings().setLoadWithOverviewMode(true);
            webViewarqos.getSettings().setUseWideViewPort(true);
            webViewarqos.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            webViewarqos.setWebViewClient(new WebViewClient() {
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
            });
            if(arquivos.getExtensao().equals("pdf"))
            {
                webViewarqos.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
            }
            else
            {
                webViewarqos.loadUrl(url);
            }

        }
        else
        {
            Toast.makeText(this, "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
        }
    }
    public void downloadarq(String url)
    {
        File extStore = Environment.getExternalStorageDirectory();
        String mPath = extStore.getAbsolutePath()+"/arq_os_"+AppHelper.getIdShop()+"/";
        File folder = new File(mPath);
        if(!folder.exists())
        {
            folder.mkdir();
        }
        //String[] split= url.split("/");
        File file = new File(mPath+arquivos.getIdarquivo()+"."+arquivos.getExtensao());
        if(!file.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDestinationInExternalPublicDir("/arq_os_"+AppHelper.getIdShop(), arquivos.getIdarquivo()+"."+arquivos.getExtensao());
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
        }

    }
}
