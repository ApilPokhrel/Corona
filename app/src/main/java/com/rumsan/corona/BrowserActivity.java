package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class BrowserActivity extends AppCompatActivity {


    WebView webView;
    ProgressDialog progressDialog;
    Button tryAgain;
    LinearLayout errorLayout;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        webView =  findViewById(R.id.webView);
        tryAgain = findViewById(R.id.try_again);
        errorLayout = findViewById(R.id.error);

        if (progressDialog == null) {
            // in standard case YourActivity.this
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        loadPage();
    }


    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try{
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
            if(progressDialog != null)progressDialog.dismiss();
            progressDialog = null;
            webView.stopLoading();
            webView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(BrowserActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    loadPage();
                    webView.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

public void loadPage(){
    errorLayout.setVisibility(View.GONE);
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    webView.setWebViewClient(new WebViewClient());
    webView.loadUrl(url);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.stopLoading();
        webView.destroy();
    }
}
