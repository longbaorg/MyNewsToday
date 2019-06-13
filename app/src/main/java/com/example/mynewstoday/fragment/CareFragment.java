package com.example.mynewstoday.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.mynewstoday.R;


public class CareFragment extends Fragment {

    WebView mWebView;
    public CareFragment() {
        // Required empty public constructor
    }


    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_care, container, false);
        mWebView = view.findViewById(R.id.webivew);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.loadUrl("https://quan.qq.com/userCenter/9_qeh_16399433?addressbar=hide&from=005&ch=&ich=");

        return view;
    }

}
