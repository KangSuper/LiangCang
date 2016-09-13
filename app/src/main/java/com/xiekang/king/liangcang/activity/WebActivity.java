package com.xiekang.king.liangcang.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiekang.king.liangcang.R;
import com.xiekang.king.liangcang.customView.ProgressWebView;

public class WebActivity extends Activity {

    private ProgressWebView webView;
    private ImageView imageView;
    private TextView titleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // ~~~ 获取参数
        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");

        // ~~~ 绑定控件
        webView = (ProgressWebView) findViewById(R.id.web_progress_view);
        imageView = (ImageView) findViewById(R.id.web_back_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleTxt = (TextView) findViewById(R.id.web_title_txt);
        // ~~~ 设置数据
        titleTxt.setText(name);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl(url);
    }
}
