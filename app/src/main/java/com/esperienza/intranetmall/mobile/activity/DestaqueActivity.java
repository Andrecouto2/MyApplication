package com.esperienza.intranetmall.mobile.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Destaque;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

/**
 * Created by ThinkPad on 23/05/2016.
 */
public class DestaqueActivity extends AppCompatActivity
{
    private WebView webViewDestaque;
    private Destaque destaque;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destaque_home);

        webViewDestaque = (WebView) findViewById(R.id.webviewdestaque);
        progress = (ProgressBar) findViewById(R.id.progress_destaque);

        Bundle bundle = getIntent().getExtras();
        destaque = Parcels.unwrap(bundle.getParcelable("destaque"));
        Util.setInsets(this, webViewDestaque);

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
    public void init()
    {
        if (AppHelper.isInternetOnline())
        {
            webViewDestaque.getSettings().setJavaScriptEnabled(true);
            webViewDestaque.getSettings().setBuiltInZoomControls(true);
            webViewDestaque.getSettings().setDisplayZoomControls(false);
            webViewDestaque.getSettings().setSaveFormData(false);
            //webViewarqos.setInitialScale(1);
            webViewDestaque.getSettings().setLoadWithOverviewMode(true);
            webViewDestaque.getSettings().setUseWideViewPort(true);
            webViewDestaque.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            webViewDestaque.setWebViewClient(new WebViewClient() {
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
            if(destaque.getLink().endsWith("pdf"))
            {
                webViewDestaque.loadUrl("http://docs.google.com/gview?embedded=true&url="+destaque.getLink());
            }
            else
            {
                webViewDestaque.loadUrl(destaque.getLink());
            }

        }
        else
        {
            Toast.makeText(this, "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
        }
    }

}
