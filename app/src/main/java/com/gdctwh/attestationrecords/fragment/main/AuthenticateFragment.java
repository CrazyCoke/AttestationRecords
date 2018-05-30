package com.gdctwh.attestationrecords.fragment.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gdctwh.attestationrecords.R;

public class AuthenticateFragment extends Fragment {

    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_authenticate,container,false);

        mWebView = mView.findViewById(R.id.web_view);
        initWebView();
        return mView;
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);

        //webview 中的链接在webview内部跳转
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("http://120.77.214.194:8086/");
    }
}

