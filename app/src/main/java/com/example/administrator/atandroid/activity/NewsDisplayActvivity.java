package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.atandroid.R;

public class NewsDisplayActvivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView infoxiangqing_back_to_info;
    private String newsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_information_listviewitem);
        newsUrl = getIntent().getStringExtra("news_url");
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsUrl);
        init();

    }

    private void init() {
        infoxiangqing_back_to_info = ((ImageView) findViewById(R.id.infoxiangqing_back_to_info));
        infoxiangqing_back_to_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this,Charity_information_Activity.class);
        startActivity(intent);
        this.finish();
    }
}
