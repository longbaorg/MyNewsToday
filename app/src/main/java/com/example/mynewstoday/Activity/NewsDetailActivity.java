package com.example.mynewstoday.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.example.mynewstoday.R;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

public class NewsDetailActivity extends AppCompatActivity {

    private WebView wv;
    private String picurl ;
    private String title ;
    private String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        picurl = intent.getStringExtra("picurl");
        title = intent.getStringExtra("title");
        wv = (WebView)findViewById(R.id.wv);
        wv.getSettings().setDefaultTextEncodingName("UTF-8");
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });

        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                share();
            }
        });
    }


    /*
        QQ分享到空间及好友  无需登录权限
        腾讯开放平台
        http://wiki.connect.qq.com/%E5%88%86%E4%BA%AB%E6%B6%88%E6%81%AF%E5%88%B0qq%EF%BC%88%E5%AE%9A%E5%90%91%E5%88%86%E4%BA%AB%EF%BC%89_android_sdk
 */
    public void share()
    {

        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE,title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"优质咨询精选，尽在Todays的APP");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,picurl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        MainActivity.mTencent.shareToQQ(NewsDetailActivity.this, params, MainActivity.mIUiListener);
    }


    /*
          创建menu
   */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
           右上角的setting的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            share();
            Toast.makeText(NewsDetailActivity.this,"暂未实现！",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
