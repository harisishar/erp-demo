package com.app.crmapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.app.crmapp.ApiCalls.ApiInterface;

public class WebViewActivity  extends AppCompatActivity {
    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        if ((getIntent()!=null)) {
            /*******Get Ticket Id and Pdf file name ******/
            String ticketId = getIntent().getStringExtra("ticket_id");
            String pdfImage = getIntent().getStringExtra("file_name");

            url=ApiInterface.TICKETPROFILE+ticketId+"/"+pdfImage;
            Log.d("URL>>","url"+url);
            WebView myWebView = (WebView) findViewById(R.id.webview);

            myWebView.setWebViewClient(new WebViewClient());
            myWebView.getSettings().setSupportZoom(true);
            myWebView.getSettings().setJavaScriptEnabled(true);

            /*******Load Url into Webview*******/
            myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        }
    }
}
