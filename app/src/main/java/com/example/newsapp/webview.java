package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.airbnb.lottie.LottieAnimationView;


public class webview extends AppCompatActivity {
    WebView webView;
//    CircularProgressBar circularProgressBar;
    LottieAnimationView lottie;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Intent intent =getIntent();

        webView= findViewById(R.id.webview);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setUseWideViewPort(true);
// Zoom out if the content width is greater than the width of the viewport
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.setBackgroundColor(Color.TRANSPARENT);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_INSET);
                webView.setWebViewClient(new MyWebViewClient());

                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                lottie=findViewById(R.id.circularProgressBar);
                lottie.playAnimation();

                webView.loadUrl(intent.getExtras().getString("url").toString());
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        //show the web page in webview but not in web browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl (url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
           if(lottie!=null)
            {
                lottie.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            if(lottie!=null)
            {
                lottie.pauseAnimation();
                lottie.cancelAnimation();
                lottie.setVisibility(View.GONE);
            }

        }
    }
}