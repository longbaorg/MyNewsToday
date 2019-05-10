package com.example.mynewstoday.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
        webSettings.setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new Object() {
//            public void clickOnAndroid() {
//                mHandler.post(new Runnable() {
//                    public void run() {
//                        mWebView.loadUrl("javascript:wave()");
//                    }
//                });
//            }
//        }, "demo");
        mWebView.loadUrl("https://quan.qq.com/post/q_13176885452_1505892060781523/71351701_459991552961556834?&postType=FUNLIST&addressbar=hide&extdata=busi%3D22%26ch%3D003802%26contentType%3D3%26ctrid%3D1%26dataSrc%3D229%26data_type%3D1%26docId%3D831149395896011495%26pid%3D1%26sGrayPlatFormModelId%3D104234%23102697%23103362%23102566%23103854%23104155%23100495%23104417%23103791%23104854%23104677%23103876%23104186%23103786%23101214%23103113%23103870%23103077%23103486%23103398%23104126%23104591%23104035%23104610%23104624%23104634%26sModelId%3D104234%23102697%23103362%23102566%23103854%23104155%23100495%23104417%23103791%23104854%23104677%23103876%23104186%23103786%23101214%23103113%23103870%23103077%23103486%23103398%23104126%23104591%23104035%23104610%23104624%23104634%26sStrategyId%3D26%26subjectId%3D14206%26zimeitiId%3Dqeh_16399433&comment=float&hotcmt=1&readMore=1&_from=005&redrt=native");
        return view;
    }

}
